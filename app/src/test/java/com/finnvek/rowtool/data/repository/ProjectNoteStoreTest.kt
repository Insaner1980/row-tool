package com.finnvek.rowtool.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.ReminderValues
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ProjectNoteStoreTest {
    private lateinit var db: RowToolDatabase
    private lateinit var counters: CounterRepository
    private var now = 100L

    @Before fun setUp() {
        db =
            Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), RowToolDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        counters = CounterRepository(db, clock = { now++ })
    }

    @After fun tearDown() = db.close()

    @Test fun savesCurrentCountAndPreservesNoOpAndCounterState() =
        runTest {
            val id = counters.createProject("A", CounterUnit.ROWS, 0, null, null).id
            val store = counters.notes
            counters.mutate(id, CounterMutation.ManualSet(74))
            val before = counters.getProject(id)
            val initial = (store.save(id, null, "  Work\r\n\r\n    🧶\rNext", true) as NoteWriteResult.Success).note!!
            assertEquals("  Work\n\n    🧶\nNext", initial.text)
            assertEquals(74L, initial.savedCount)
            assertEquals(before, counters.getProject(id))
            assertEquals(1, db.counterHistoryDao().countAll())
            counters.mutate(id, CounterMutation.ManualSet(80))
            assertEquals(initial, (store.save(id, initial.version, initial.text, true) as NoteWriteResult.Success).note)
            val changed = (store.save(id, initial.version, "Changed", true) as NoteWriteResult.Success).note!!
            assertEquals(80L, changed.savedCount)
            assertTrue(changed.savedAt > initial.savedAt)
            val detached = (store.save(id, changed.version, "Changed", false) as NoteWriteResult.Success).note!!
            assertNull(detached.savedCount)
            counters.mutate(id, CounterMutation.Reset)
            val attached = (store.save(id, detached.version, "Changed", true) as NoteWriteResult.Success).note!!
            assertEquals(0L, attached.savedCount)
            counters.undo(id)
            assertEquals(attached, store.load(id).note)
        }

    @Test fun noteWritesPreserveRepeatAdditionalCountersRemindersAndUndoHistory() =
        runTest {
            val id = counters.createProject("Work", CounterUnit.ROWS, 1, 100, 6).id
            counters.repeatSettings.save(id, 6, 11)
            counters.additionalCounters.save(id, null, "Following", true)
            counters.additionalCounters.save(id, null, "Manual", false)
            counters.mutate(id, CounterMutation.ManualSet(32))
            val reminder = counters.reminders.save(id, null, null, ReminderValues("Check", 32, 6, true))!!
            counters.reminders.acknowledge(id, reminder.id, reminder.revision, 32)
            val project = db.projectDao().getById(id)
            val additional = db.additionalCounterDao().getAll()
            val reminders = db.reminderDao().getAll()
            val history = db.counterHistoryDao().getAll()
            val effects = db.counterHistoryEffectDao().getAll()
            val note = (counters.notes.save(id, null, "First", true) as NoteWriteResult.Success).note!!
            val edited = (counters.notes.save(id, note.version, "Second", false) as NoteWriteResult.Success).note!!
            counters.notes.delete(id, edited.version)
            assertEquals(project, db.projectDao().getById(id))
            assertEquals(additional, db.additionalCounterDao().getAll())
            assertEquals(reminders, db.reminderDao().getAll())
            assertEquals(history, db.counterHistoryDao().getAll())
            assertEquals(effects, db.counterHistoryEffectDao().getAll())
            val stored = (counters.notes.save(id, null, "Keep", true) as NoteWriteResult.Success).note!!
            counters.repeatSettings.save(id, 8, 25)
            counters.mutate(id, CounterMutation.Increment, additional.last().id)
            counters.undo(id)
            counters.mutate(id, CounterMutation.Reset)
            assertEquals(stored, counters.notes.load(id).note)
        }

    @Test fun emptyValidationOwnershipVersionsArchiveAndCascade() =
        runTest {
            val (a, b) = createTwoProjects(counters)
            val store = counters.notes
            assertEquals(NoteWriteResult.Success(null), store.save(a, null, " \n\t", true))
            assertNull(store.load(a).note)
            assertEquals(NoteWriteResult.Invalid, store.save(a, null, "🧶".repeat(5001), true))
            val note = (store.save(a, null, "🧶".repeat(5000), true) as NoteWriteResult.Success).note!!
            assertEquals(NoteWriteResult.DeletionRequired, store.save(a, note.version, "\n ", false))
            assertEquals(note, store.load(a).note)
            assertEquals(NoteWriteResult.Conflict, store.save(b, note.version, "Wrong owner", true))
            assertEquals(NoteWriteResult.Conflict, store.delete(b, note.version))
            assertEquals(NoteWriteResult.Conflict, store.save(a, null, "Duplicate", true))
            val changed = (store.save(a, note.version, "New", true) as NoteWriteResult.Success).note!!
            assertEquals(NoteWriteResult.Conflict, store.delete(a, note.version))
            assertEquals(NoteWriteResult.Conflict, store.save(a, note.version, "Old", true))
            counters.setArchived(a, true)
            assertEquals(changed, store.load(a).note)
            assertEquals(NoteWriteResult.Unavailable, store.delete(a, changed.version))
            assertEquals(NoteWriteResult.Unavailable, store.save(a, changed.version, "Archived", true))
            counters.setArchived(a, false)
            assertEquals(NoteWriteResult.Success(null), store.delete(a, changed.version))
            val recreated = (store.save(a, null, "Recreated", true) as NoteWriteResult.Success).note!!
            assertNotEquals(changed.version, recreated.version)
            assertEquals(NoteWriteResult.Conflict, store.save(a, changed.version, "Stale", true))
            counters.deleteProject(a)
            assertNull(store.load(a).note)
        }
}
