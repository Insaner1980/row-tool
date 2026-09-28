package com.finnvek.rowtool.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.ReminderRules
import com.finnvek.rowtool.domain.model.ReminderValues
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ReminderStoreTest {
    private lateinit var db: RowToolDatabase
    private lateinit var counters: CounterRepository

    @Before fun setUp() {
        db =
            Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), RowToolDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        counters = CounterRepository(db)
    }

    @After fun tearDown() = db.close()

    @Test fun acknowledgementUsesExactOwnerVersionAndOccurrenceWithoutChangingHistory() =
        runTest {
            val (a, b) = createTwoProjects(counters)
            val store = counters.reminders
            val saved = store.save(a, null, null, ReminderValues(" Check ", 32, 6, true))!!
            assertEquals("Check", saved.message)
            assertNull(store.save(b, saved.id, saved.revision, ReminderValues("Wrong", 32, 6, true)))
            counters.mutate(a, CounterMutation.ManualSet(32))
            val historyCount = db.counterHistoryDao().countAll()
            counters.mutate(a, CounterMutation.ManualSet(38))
            assertTrue(store.acknowledge(a, saved.id, saved.revision, 32))
            assertEquals(
                32L,
                store
                    .observe(a)
                    .first()
                    .single()
                    .acknowledgedThrough,
            )
            assertTrue(store.acknowledge(a, saved.id, saved.revision, 32))
            assertFalse(store.acknowledge(b, saved.id, saved.revision, 38))
            assertFalse(store.acknowledge(a, saved.id, saved.revision, 31))
            assertEquals(38L, ReminderRules.status(store.observe(a).first().single(), 38).dueCount)
            assertTrue(store.acknowledge(a, saved.id, saved.revision, 38))
            assertEquals(historyCount + 1, db.counterHistoryDao().countAll())
            counters.undo(a)
            assertEquals(
                38L,
                store
                    .observe(a)
                    .first()
                    .single()
                    .acknowledgedThrough,
            )
        }

    @Test fun editResetArchiveAndDeleteRespectStoredState() =
        runTest {
            val a = counters.createProject("A", CounterUnit.ROWS, 0, null, null).id
            val store = counters.reminders
            val initial = store.save(a, null, null, ReminderValues("First", 1, null, true))!!
            counters.mutate(a, CounterMutation.Increment)
            assertTrue(store.acknowledge(a, initial.id, initial.revision, 1))
            val renamed = store.save(a, initial.id, initial.revision, ReminderValues("Changed", 1, null, true))!!
            assertEquals(1L, renamed.acknowledgedThrough)
            assertFalse(store.acknowledge(a, initial.id, initial.revision, 1))
            assertTrue(store.resetAcknowledgements(a, renamed.id, renamed.revision))
            assertNull(
                store
                    .observe(a)
                    .first()
                    .single()
                    .acknowledgedThrough,
            )
            assertEquals(1L, ReminderRules.status(store.observe(a).first().single(), 1).dueCount)
            assertTrue(store.acknowledge(a, renamed.id, renamed.revision + 1, 1))
            val moved = store.save(a, initial.id, renamed.revision + 1, ReminderValues("Changed", 2, null, false))!!
            assertNull(moved.acknowledgedThrough)
            assertFalse(moved.enabled)
            assertTrue(store.resetAcknowledgements(a, moved.id, moved.revision))
            counters.setArchived(a, true)
            assertNull(store.save(a, null, null, ReminderValues("Other", 2, null, true)))
            assertFalse(store.delete(a, moved.id, moved.revision))
            counters.setArchived(a, false)
            assertTrue(store.delete(a, moved.id, moved.revision + 1))
            assertTrue(store.observe(a).first().isEmpty())
        }

    @Test
    fun duplicateCreateAndStaleEditCannotReplaceNewReminderVersion() =
        runTest {
            val projectId = counters.createProject("A", CounterUnit.ROWS, 0, null, null).id
            val store = counters.reminders
            val initial = store.save(projectId, null, null, ReminderValues("First", 1, null, true), "fixed")!!
            assertEquals(initial, store.save(projectId, null, null, ReminderValues("First", 1, null, true), "fixed"))
            assertNull(store.save(projectId, null, null, ReminderValues("Different", 1, null, true), "fixed"))
            assertEquals(1, store.observe(projectId).first().size)
            val edited = store.save(projectId, initial.id, initial.revision, ReminderValues("Edited", 1, null, true))!!
            assertNull(store.save(projectId, initial.id, initial.revision, ReminderValues("Stale", 2, null, true)))
            assertFalse(store.delete(projectId, initial.id, initial.revision))
            assertEquals(
                "Edited",
                store
                    .observe(projectId)
                    .first()
                    .single()
                    .message,
            )
            assertTrue(store.delete(projectId, edited.id, edited.revision))
        }

    @Test
    fun decreaseUndoResetAndDirectSetRecalculateWithoutErasingAcknowledgement() =
        runTest {
            val projectId = counters.createProject("Work", CounterUnit.ROWS, 0, null, null).id
            val reminder = counters.reminders.save(projectId, null, null, ReminderValues("Check", 5, null, true))!!

            fun due(count: Long) = ReminderRules.status(reminder, count).dueCount
            counters.mutate(projectId, CounterMutation.ManualSet(5))
            assertEquals(5L, due(counters.getProject(projectId)!!.count))
            counters.mutate(projectId, CounterMutation.Decrement)
            assertNull(due(counters.getProject(projectId)!!.count))
            counters.undo(projectId)
            assertEquals(5L, due(counters.getProject(projectId)!!.count))
            counters.mutate(projectId, CounterMutation.Reset)
            assertNull(due(counters.getProject(projectId)!!.count))
            counters.mutate(projectId, CounterMutation.ManualSet(6))
            assertEquals(5L, due(counters.getProject(projectId)!!.count))
            assertTrue(counters.reminders.acknowledge(projectId, reminder.id, reminder.revision, 5))
            counters.mutate(projectId, CounterMutation.Reset)
            counters.mutate(projectId, CounterMutation.ManualSet(5))
            val stored =
                counters.reminders
                    .observe(projectId)
                    .first()
                    .single()
            assertEquals(5L, stored.acknowledgedThrough)
            assertNull(ReminderRules.status(stored, 5).dueCount)
        }
}
