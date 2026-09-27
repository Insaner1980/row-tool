package com.finnvek.rowtool.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.domain.model.CounterConstants.MAX_COUNT
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterMutationResult
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
import java.util.concurrent.atomic.AtomicLong

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class AdditionalCounterRepositoryTest {
    private lateinit var db: RowToolDatabase
    private lateinit var repository: CounterRepository
    private val clock = AtomicLong(100)

    @Before
    fun setUp() {
        db =
            Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), RowToolDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        repository = CounterRepository(db, clock::incrementAndGet)
    }

    @After
    fun tearDown() = db.close()

    @Test
    fun createEditAndDeleteAreIsolatedAndSettingsNeverOverwriteCount() =
        runTest {
            val first = project()
            val second = project()
            val id = counter(first)
            assertEquals(0L, count(first, id))
            assertFalse(db.additionalCounterDao().getById(first, id)!!.followsMain)
            assertTrue(
                repository.additionalCounters
                    .observe(second)
                    .first()
                    .isEmpty(),
            )
            assertFalse(repository.additionalCounters.save(second, id, "Wrong owner", true))
            assertFalse(repository.additionalCounters.delete(second, id))
            assertEquals(CounterMutationResult.CounterMissing, repository.mutate(second, CounterMutation.Increment, id))
            repository.mutate(first, CounterMutation.ManualSet(19), id)
            repository.additionalCounters.save(first, id, "  Sleeve  ", true)
            assertEquals(19L, count(first, id))
            assertEquals("Sleeve", db.additionalCounterDao().getById(first, id)!!.name)
            repository.mutate(first, CounterMutation.Increment)
            repository.additionalCounters.save(first, id, "Sleeve", false)
            repository.mutate(first, CounterMutation.Increment)
            assertEquals(20L, count(first, id))
            assertTrue(repository.additionalCounters.delete(first, id))
            assertTrue(
                repository.additionalCounters
                    .observe(first)
                    .first()
                    .isEmpty(),
            )
            assertEquals(2L, repository.getProject(first)!!.count)
            assertEquals(0L, repository.getProject(second)!!.count)
        }

    @Test
    fun sharedNameAndCountValidationRejectsInvalidValues() =
        runTest {
            val project = project()
            for (name in listOf(" ", "x".repeat(61))) {
                assertTrue(
                    runCatching { repository.additionalCounters.save(project, null, name) }.exceptionOrNull() is IllegalArgumentException,
                )
            }
            assertTrue(repository.additionalCounters.save(project, null, "🧶".repeat(60)))
            val id =
                db
                    .additionalCounterDao()
                    .getActive(project)
                    .single()
                    .id
            for (value in listOf(-1L, MAX_COUNT + 1)) {
                assertTrue(repository.mutate(project, CounterMutation.ManualSet(value), id) is CounterMutationResult.Invalid)
            }
            assertEquals(0L, count(project, id))
            assertEquals(0, db.counterHistoryDao().countAll())
        }

    @Test
    fun mainIncrementDecrementSetAndResetUseActualSignedDelta() =
        runTest {
            val project = project(start = 1)
            val linked = counter(project, follows = true)
            val manual = counter(project)
            repository.mutate(project, CounterMutation.ManualSet(20), linked)
            repository.mutate(project, CounterMutation.Increment)
            assertEquals(21L, count(project, linked))
            repository.mutate(project, CounterMutation.Decrement)
            assertEquals(20L, count(project, linked))
            repository.mutate(project, CounterMutation.ManualSet(10))
            assertEquals(29L, count(project, linked))
            repository.mutate(project, CounterMutation.Reset)
            assertEquals(20L, count(project, linked))
            assertEquals(1L, repository.getProject(project)!!.count)
            assertEquals(0L, count(project, manual))
            assertEquals(6, repository.getProject(project)!!.repeatLength)
        }

    @Test
    fun clippedMultiCounterUndoRestoresExactValuesAndUsesOneSlot() =
        runTest {
            val project = project()
            val low = counter(project, true)
            val high = counter(project, true)
            repository.mutate(project, CounterMutation.ManualSet(MAX_COUNT - 2), high)
            val before = db.counterHistoryDao().countAll()
            repository.mutate(project, CounterMutation.ManualSet(10))
            assertEquals(before + 1, db.counterHistoryDao().countAll())
            assertEquals(10L, count(project, low))
            assertEquals(MAX_COUNT, count(project, high))
            repository.undo(project)
            assertEquals(0L, count(project, low))
            assertEquals(MAX_COUNT - 2, count(project, high))
            assertEquals(0L, repository.getProject(project)!!.count)
            repository.mutate(project, CounterMutation.ManualSet(10))
            repository.mutate(project, CounterMutation.ManualSet(2), low)
            repository.mutate(project, CounterMutation.Reset)
            assertEquals(0L, count(project, low))
            repository.undo(project)
            assertEquals(2L, count(project, low))
            assertEquals(MAX_COUNT, count(project, high))
        }

    @Test
    fun ownIncrementDecrementSetAndResetUndoOnlyThatCounter() =
        runTest {
            val project = project()
            val manual = counter(project)
            val other = counter(project, true)
            for ((mutation, expected) in listOf(
                CounterMutation.Increment to 1L,
                CounterMutation.ManualSet(25) to 25L,
                CounterMutation.Decrement to 24L,
                CounterMutation.Reset to 0L,
            )) {
                repository.mutate(project, mutation, manual)
                assertEquals(expected, count(project, manual))
            }
            for (expected in listOf(24L, 25L, 1L, 0L)) {
                assertTrue(repository.undo(project) is CounterMutationResult.Changed)
                assertEquals(expected, count(project, manual))
                assertEquals(0L, count(project, other))
                assertEquals(0L, repository.getProject(project)!!.count)
            }
            assertFalse(repository.observeCanUndo(project).first())
        }

    @Test
    fun oldEventDoesNotAffectCounterCreatedLater() =
        runTest {
            val project = project(start = 1)
            repository.mutate(project, CounterMutation.Decrement)
            val later = counter(project, true)
            repository.undo(project)
            assertEquals(0L, count(project, later))
            assertEquals(1L, repository.getProject(project)!!.count)
        }

    @Test
    fun deletionKeepsHistoryButUndoNeverMakesDeletedCounterVisible() =
        runTest {
            val project = project()
            val deleted = counter(project, true)
            val remaining = counter(project, true)
            repository.mutate(project, CounterMutation.Increment)
            repository.mutate(project, CounterMutation.Increment, deleted)
            repository.additionalCounters.delete(project, deleted)
            assertEquals(2, db.counterHistoryDao().countAll())
            repository.mutate(project, CounterMutation.Increment)
            assertEquals(2L, count(project, deleted))
            repository.undo(project)
            repository.undo(project)
            assertEquals(1L, count(project, deleted))
            assertEquals(
                listOf(remaining),
                repository.additionalCounters
                    .observe(project)
                    .first()
                    .map { it.id },
            )
            repository.undo(project)
            assertNull(db.additionalCounterDao().getById(project, deleted))
            assertEquals(0L, count(project, remaining))
        }

    @Test
    fun boundariesAndIdenticalSettingsAreNoOpsWithoutClockOrHistoryChanges() =
        runTest {
            val project = project()
            val id = counter(project)
            val timestamp = repository.getProject(project)!!.updatedAt
            val beforeClock = clock.get()
            repository.additionalCounters.save(project, id, "Counter", false)
            repository.mutate(project, CounterMutation.Decrement, id)
            repository.mutate(project, CounterMutation.ManualSet(0), id)
            repository.mutate(project, CounterMutation.Reset, id)
            repository.mutate(project, CounterMutation.Decrement)
            assertEquals(timestamp, repository.getProject(project)!!.updatedAt)
            assertEquals(beforeClock, clock.get())
            assertEquals(0, db.counterHistoryDao().countAll())
            repository.mutate(project, CounterMutation.ManualSet(MAX_COUNT), id)
            val maximumTime = repository.getProject(project)!!.updatedAt
            repository.mutate(project, CounterMutation.Increment, id)
            assertEquals(maximumTime, repository.getProject(project)!!.updatedAt)
            assertEquals(1, db.counterHistoryDao().countAll())
        }

    @Test
    fun concurrentRepositoryInstancesKeepAllMainAndManualChangesAndTrimTo100() =
        runTest {
            val project = project()
            val linked = counter(project, true)
            val manual = counter(project)
            val secondRepository = CounterRepository(db)
            (1..120)
                .map {
                    async(Dispatchers.Default) {
                        val repo = if (it % 2 == 0) repository else secondRepository
                        repo.mutate(project, CounterMutation.Increment)
                        repo.mutate(project, CounterMutation.Increment, manual)
                    }
                }.awaitAll()
            assertEquals(120L, repository.getProject(project)!!.count)
            assertEquals(120L, count(project, linked))
            assertEquals(120L, count(project, manual))
            assertEquals(100, db.counterHistoryDao().countAll())
            repeat(100) { repository.undo(project) }
            assertEquals(140L, repository.getProject(project)!!.count + count(project, manual))
        }

    @Test
    fun pruningHistoryRemovesUnreferencedDeletedCounters() =
        runTest {
            val project = project()
            val id = counter(project, true)
            repository.mutate(project, CounterMutation.Increment)
            repository.additionalCounters.delete(project, id)
            repeat(100) { repository.mutate(project, CounterMutation.Increment) }
            assertNull(db.additionalCounterDao().getById(project, id))
            assertEquals(100, db.counterHistoryDao().countAll())
        }

    @Test
    fun missingArchivedAndDeletedOwnersCannotBeChanged() =
        runTest {
            val project = project()
            val id = counter(project)
            repository.setArchived(project, true)
            assertEquals(CounterMutationResult.ProjectArchived, repository.mutate(project, CounterMutation.Increment, id))
            assertFalse(repository.additionalCounters.save(project, id, "Edited", true))
            assertFalse(repository.additionalCounters.delete(project, id))
            assertFalse(repository.additionalCounters.save("missing", null, "Counter"))
            repository.deleteProject(project)
            assertEquals(CounterMutationResult.ProjectMissing, repository.mutate(project, CounterMutation.Increment, id))
            assertTrue(db.additionalCounterDao().getAll().isEmpty())
        }

    @Test
    fun failedEffectWriteRollsBackMainCountersTimestampAndHistory() =
        runTest {
            val project = project()
            val id = counter(project, true)
            val before = repository.getProject(project)
            db.openHelper.writableDatabase.execSQL(
                "CREATE TRIGGER fail_effect BEFORE INSERT ON counter_history_effects BEGIN SELECT RAISE(ABORT, 'test'); END",
            )
            assertTrue(runCatching { repository.mutate(project, CounterMutation.Increment) }.isFailure)
            assertEquals(before, repository.getProject(project))
            assertEquals(0L, count(project, id))
            assertEquals(0, db.counterHistoryDao().countAll())
        }

    @Test
    fun failedUndoRollsBackAndProjectDeletionCascadesAllCounterData() =
        runTest {
            val project = project()
            val id = counter(project, true)
            repository.mutate(project, CounterMutation.Increment)
            val before = repository.getProject(project)
            db.openHelper.writableDatabase.execSQL(
                "CREATE TRIGGER fail_undo BEFORE UPDATE ON additional_counters BEGIN SELECT RAISE(ABORT, 'test'); END",
            )
            assertTrue(runCatching { repository.undo(project) }.isFailure)
            assertEquals(before, repository.getProject(project))
            assertEquals(1L, count(project, id))
            assertEquals(1, db.counterHistoryDao().countAll())
            assertEquals(1, db.counterHistoryEffectDao().getAll().size)
            db.openHelper.writableDatabase.execSQL("DROP TRIGGER fail_undo")
            repository.deleteProject(project)
            assertTrue(db.additionalCounterDao().getAll().isEmpty())
            assertTrue(db.counterHistoryEffectDao().getAll().isEmpty())
            assertEquals(0, db.counterHistoryDao().countAll())
        }

    private suspend fun project(start: Int = 0): String = repository.createProject("Project", CounterUnit.ROWS, start, 100, 6).id

    private suspend fun counter(
        projectId: String,
        follows: Boolean = false,
    ): String {
        repository.additionalCounters.save(projectId, null, "Counter", follows)
        return db
            .additionalCounterDao()
            .getActive(projectId)
            .last()
            .id
    }

    private suspend fun count(
        projectId: String,
        counterId: String,
    ): Long = db.additionalCounterDao().getById(projectId, counterId)!!.count
}
