package com.finnvek.rowtool.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class CopyCounterSetupTest {
    private lateinit var db: RowToolDatabase
    private lateinit var repository: CounterRepository
    private var now = 100L

    @Before fun setUp() {
        db =
            Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), RowToolDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        repository = CounterRepository(db, { ++now })
    }

    @After fun tearDown() = db.close()

    @Test fun basicAndArchivedSourcesCreateActiveIndependentProjects() =
        runTest {
            for (archived in listOf(false, true)) {
                val source = repository.createProject("Source", CounterUnit.ROUNDS, 1, 120, 8, 11)
                repository.mutate(source.id, CounterMutation.ManualSet(75))
                repository.setArchived(source.id, archived)
                val before = db.projectDao().getById(source.id)
                val draft = repository.copySetups.capture(source.id)
                val copy = repository.copySetups.create(draft, draft.settings.copy(name = "Copy"))
                assertNotEquals(source.id, copy.id)
                assertEquals(1L, copy.count)
                assertEquals(CounterUnit.ROUNDS, copy.counterUnit)
                assertEquals(120L, copy.targetCount)
                assertEquals(8, copy.repeatLength)
                assertEquals(11L, copy.repeatStartCount)
                assertFalse(copy.isArchived)
                assertTrue(copy.createdAt > before!!.createdAt)
                assertEquals(before, db.projectDao().getById(source.id))
                assertNull(db.counterHistoryDao().getLatest(copy.id))
            }
        }

    @Test fun reviewedCountersKeepOrderNamesModesAndStartAtZero() =
        runTest {
            val source = repository.createProject("Source", CounterUnit.ROWS, 1, 120, 8, 11)
            repository.additionalCounters.save(source.id, null, "Leg rows", true)
            repository.additionalCounters.save(source.id, null, "Decreases", false)
            repository.additionalCounters.save(source.id, null, "Decreases", true)
            repository.additionalCounters.save(source.id, null, "Hidden", false)
            val rows = db.additionalCounterDao().getActive(source.id)
            repository.mutate(source.id, CounterMutation.ManualSet(75))
            repository.mutate(source.id, CounterMutation.ManualSet(64), rows[0].id)
            repository.mutate(source.id, CounterMutation.ManualSet(9), rows[1].id)
            repository.mutate(source.id, CounterMutation.Increment, rows[3].id)
            repository.additionalCounters.delete(source.id, rows[3].id)
            val draft = repository.copySetups.capture(source.id)
            repository.additionalCounters.save(source.id, rows[0].id, "Changed", false)
            val before = db.additionalCounterDao().getAll()
            val copy = repository.copySetups.create(draft, draft.settings.copy(name = "Copy"))
            val children = db.additionalCounterDao().getActive(copy.id)
            assertEquals(listOf("Leg rows", "Decreases", "Decreases"), children.map { it.name })
            assertEquals(listOf(true, false, true), children.map { it.followsMain })
            assertEquals(listOf(0L, 0L, 0L), children.map { it.count })
            assertEquals(3, children.map { it.id }.distinct().size)
            assertTrue(children.none { child -> rows.any { it.id == child.id } })
            repository.mutate(copy.id, CounterMutation.Increment)
            assertEquals(listOf(1L, 0L, 1L), db.additionalCounterDao().getActive(copy.id).map { it.count })
            repository.undo(copy.id)
            assertEquals(children, db.additionalCounterDao().getActive(copy.id))
            assertEquals(before, db.additionalCounterDao().getAll().filter { it.projectId == source.id })
        }

    @Test fun repeatedSubmissionReturnsOneCommittedIdentity() =
        runTest {
            val source = repository.createProject("Source", CounterUnit.ROWS, 0, null, null)
            repository.additionalCounters.save(source.id, null, "Extra")
            val draft = repository.copySetups.capture(source.id)
            val results = List(2) { async { repository.copySetups.create(draft, draft.settings.copy(name = "Copy")) } }.awaitAll()
            assertEquals(results[0], results[1])
            assertEquals(2, db.projectDao().count())
            assertEquals(1, db.additionalCounterDao().getActive(results[0].id).size)
        }

    @Test fun deletedSourceAndInvalidFieldsWriteNothing() =
        runTest {
            val source = repository.createProject("Source", CounterUnit.ROWS, 0, null, null)
            val draft = repository.copySetups.capture(source.id)
            assertTrue(runCatching { repository.copySetups.create(draft, draft.settings) }.isFailure)
            assertEquals(1, db.projectDao().count())
            repository.deleteProject(source.id)
            assertTrue(
                runCatching {
                    repository.copySetups.create(draft, draft.settings.copy(name = "Copy"))
                }.exceptionOrNull() is CopySourceUnavailableException,
            )
            assertEquals(0, db.projectDao().count())
        }

    @Test fun replacementInvalidatesOldDraftAndV5PreservesIndependentCopy() =
        runTest {
            val preferences =
                com.finnvek.rowtool.data.preferences.PreferencesRepository(
                    com.finnvek.rowtool.test
                        .InMemoryPreferencesDataStore(),
                    db.projectDao(),
                )
            val backup = BackupRepository(db, preferences)
            val source = repository.createProject("Source", CounterUnit.ROWS, 1, 120, 8, 11)
            repository.additionalCounters.save(source.id, null, "Leg rows", true)
            repository.mutate(source.id, CounterMutation.ManualSet(75))
            repository.notes.save(source.id, null, "Private source note", true)
            db.reminderDao().insert(
                com.finnvek.rowtool.data.local
                    .ReminderEntity("reminder", source.id, "Turn", 3, 2, true, 5, 7),
            )
            val draft = repository.copySetups.capture(source.id)
            val sourceRow = db.projectDao().getById(source.id)
            val copy = repository.copySetups.create(draft, draft.settings.copy(name = "Copy"))
            assertEquals(sourceRow, db.projectDao().getById(source.id))
            assertTrue(db.reminderDao().getForProject(copy.id).isEmpty())
            assertTrue(db.projectNoteDao().getAll().none { it.projectId == copy.id })
            val before = db.projectDao().getAll()
            val children = db.additionalCounterDao().getAll()
            val oldDraft = repository.copySetups.capture(source.id)
            val decoded = backup.prepareImport(backup.exportJson().encodeToByteArray()) as BackupDecodeResult.Valid
            assertTrue(backup.replaceWith(decoded.backup) is BackupImportResult.Success)
            assertEquals(before, db.projectDao().getAll())
            assertEquals(children, db.additionalCounterDao().getAll())
            assertTrue(db.reminderDao().getForProject(copy.id).isEmpty())
            assertTrue(db.projectNoteDao().getAll().none { it.projectId == copy.id })
            assertNull(db.counterHistoryDao().getLatest(copy.id))
            assertTrue(
                runCatching {
                    repository.copySetups.create(oldDraft, oldDraft.settings.copy(name = "Stale"))
                }.exceptionOrNull() is CopySourceUnavailableException,
            )
            repository.deleteProject(source.id)
            repository.mutate(copy.id, CounterMutation.Increment)
            assertEquals(2L, repository.getProject(copy.id)!!.count)
        }

    @Test fun transactionChecksLimitIncludingArchivedProjects() =
        runTest {
            val source = repository.createProject("Source", CounterUnit.ROWS, 0, null, null)
            val draft = repository.copySetups.capture(source.id)
            val row = db.projectDao().getById(source.id)!!
            db.projectDao().insertAll((1 until 1000).map { row.copy(id = "limit-$it", isArchived = true) })
            assertTrue(
                runCatching {
                    repository.copySetups.create(draft, draft.settings.copy(name = "Copy"))
                }.exceptionOrNull() is ProjectLimitReachedException,
            )
            assertEquals(1000, db.projectDao().count())
        }

    @Test fun widgetBindingAndGenerationStayUnchangedAndCopyIsSelectable() =
        runTest {
            val file = java.io.File.createTempFile("copy-bindings", ".xml")
            file.delete()
            try {
                val bindings =
                    com.finnvek.rowtool.widget
                        .WidgetBindings(file)
                val source = repository.createProject("Source", CounterUnit.ROWS, 0, null, null)
                val binding = bindings.bind(7, source.id)
                val bytes = file.readBytes()
                val draft = repository.copySetups.capture(source.id)
                val copy = repository.copySetups.create(draft, draft.settings.copy(name = "Copy"))
                assertArrayEquals(bytes, file.readBytes())
                assertEquals(binding, bindings.read(7))
                bindings.withBinding(7, binding.token) { repository.mutate(it, CounterMutation.Increment) }
                assertEquals(1L, repository.getProject(source.id)!!.count)
                assertEquals(0L, repository.getProject(copy.id)!!.count)
                assertTrue(db.projectDao().getAll().any { it.id == copy.id && !it.isArchived })
                assertNotNull(bindings.bindIf(8, copy.id) { repository.getProject(copy.id)?.isArchived == false })
            } finally {
                file.delete()
            }
        }

    @Test fun failedChildInsertRollsBackProjectAndAllowsRetry() =
        runTest {
            val source = repository.createProject("Source", CounterUnit.ROWS, 0, null, null)
            repository.additionalCounters.save(source.id, null, "Extra")
            val draft = repository.copySetups.capture(source.id)
            db.openHelper.writableDatabase.execSQL(
                "CREATE TRIGGER fail_copy BEFORE INSERT ON additional_counters " +
                    "BEGIN SELECT RAISE(ABORT, 'Injected failure'); END",
            )
            assertTrue(runCatching { repository.copySetups.create(draft, draft.settings.copy(name = "Copy")) }.isFailure)
            assertEquals(1, db.projectDao().count())
            db.openHelper.writableDatabase.execSQL("DROP TRIGGER fail_copy")
            val copy = repository.copySetups.create(draft, draft.settings.copy(name = "Copy"))
            assertEquals(1, db.additionalCounterDao().getActive(copy.id).size)
        }
}
