package com.finnvek.rowtool.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterMutationResult
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.HistoryChangeReason
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
class CounterRepositoryTest {
    private lateinit var database: RowToolDatabase
    private lateinit var repository: CounterRepository
    private val now = AtomicLong(1_000)

    @Before
    fun setUp() {
        database =
            Room
                .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    RowToolDatabase::class.java,
                ).allowMainThreadQueries()
                .build()
        repository = CounterRepository(database, clock = { now.incrementAndGet() })
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun incrementFromZeroPersistsHistory() =
        runTest {
            val project = createProject()

            val result = repository.mutate(project.id, CounterMutation.Increment)

            assertChanged(result, 0, 1, HistoryChangeReason.INCREMENT)
            assertEquals(1L, repository.getProject(project.id)?.count)
            assertEquals(1, database.counterHistoryDao().countForProject(project.id))
            assertTrue(repository.observeCanUndo(project.id).first())
        }

    @Test
    fun decrementFromOnePersistsHistory() =
        runTest {
            val project = createProject(startValue = 1)

            val result = repository.mutate(project.id, CounterMutation.Decrement)

            assertChanged(result, 1, 0, HistoryChangeReason.DECREMENT)
            assertEquals(0L, repository.getProject(project.id)?.count)
        }

    @Test
    fun decrementAtZeroIsNoOpWithoutHistory() =
        runTest {
            val project = createProject()

            val result = repository.mutate(project.id, CounterMutation.Decrement)

            assertEquals(CounterMutationResult.NoOp(0), result)
            assertEquals(0, database.counterHistoryDao().countForProject(project.id))
        }

    @Test
    fun incrementAtMaximumIsNoOpWithoutHistory() =
        runTest {
            val project = createProject()
            repository.mutate(project.id, CounterMutation.ManualSet(CounterConstants.MAX_COUNT))
            database.counterHistoryDao().deleteAll()

            val result = repository.mutate(project.id, CounterMutation.Increment)

            assertEquals(CounterMutationResult.NoOp(CounterConstants.MAX_COUNT), result)
            assertEquals(0, database.counterHistoryDao().countForProject(project.id))
        }

    @Test
    fun manualSetIsPersistedAndUndoable() =
        runTest {
            val project = createProject()

            assertChanged(
                repository.mutate(project.id, CounterMutation.ManualSet(42)),
                0,
                42,
                HistoryChangeReason.MANUAL_SET,
            )
            assertChanged(repository.undo(project.id), 42, 0, HistoryChangeReason.MANUAL_SET)
            assertEquals(0L, repository.getProject(project.id)?.count)
        }

    @Test
    fun resetToConfiguredZeroIsUndoable() =
        runTest {
            val project = createProject(startValue = 0)
            repository.mutate(project.id, CounterMutation.ManualSet(8))

            assertChanged(repository.mutate(project.id, CounterMutation.Reset), 8, 0, HistoryChangeReason.RESET)
            assertChanged(repository.undo(project.id), 0, 8, HistoryChangeReason.RESET)
        }

    @Test
    fun resetToConfiguredOneIsUndoable() =
        runTest {
            val project = createProject(startValue = 1)
            repository.mutate(project.id, CounterMutation.ManualSet(8))

            assertChanged(repository.mutate(project.id, CounterMutation.Reset), 8, 1, HistoryChangeReason.RESET)
            assertChanged(repository.undo(project.id), 1, 8, HistoryChangeReason.RESET)
        }

    @Test
    fun undoRestoresIncrementAndDecrement() =
        runTest {
            val project = createProject()
            repository.mutate(project.id, CounterMutation.Increment)
            repository.mutate(project.id, CounterMutation.Increment)
            repository.mutate(project.id, CounterMutation.Decrement)

            assertChanged(repository.undo(project.id), 1, 2, HistoryChangeReason.DECREMENT)
            assertChanged(repository.undo(project.id), 2, 1, HistoryChangeReason.INCREMENT)
            assertChanged(repository.undo(project.id), 1, 0, HistoryChangeReason.INCREMENT)
            assertEquals(CounterMutationResult.NoOp(0), repository.undo(project.id))
            assertFalse(repository.observeCanUndo(project.id).first())
        }

    @Test
    fun sameManualValueAndAlreadyResetValueAreNoOps() =
        runTest {
            val project = createProject(startValue = 0)

            assertEquals(CounterMutationResult.NoOp(0), repository.mutate(project.id, CounterMutation.ManualSet(0)))
            assertEquals(CounterMutationResult.NoOp(0), repository.mutate(project.id, CounterMutation.Reset))
            assertEquals(0, database.counterHistoryDao().countForProject(project.id))
        }

    @Test
    fun onlyNewestOneHundredChangesRemainUndoable() =
        runTest {
            val project = createProject()
            repeat(101) { repository.mutate(project.id, CounterMutation.Increment) }

            assertEquals(100, database.counterHistoryDao().countForProject(project.id))
            repeat(100) { repository.undo(project.id) }
            assertEquals(1L, repository.getProject(project.id)?.count)
            assertEquals(CounterMutationResult.NoOp(1), repository.undo(project.id))
        }

    @Test
    fun deletingProjectCascadesItsHistory() =
        runTest {
            val project = createProject()
            repository.mutate(project.id, CounterMutation.Increment)

            repository.deleteProject(project.id)

            assertNull(repository.getProject(project.id))
            assertEquals(0, database.counterHistoryDao().countForProject(project.id))
        }

    @Test
    fun archivedProjectCannotBeMutatedOrUndone() =
        runTest {
            val project = createProject()
            repository.mutate(project.id, CounterMutation.Increment)
            repository.setArchived(project.id, true)

            assertEquals(CounterMutationResult.ProjectArchived, repository.mutate(project.id, CounterMutation.Increment))
            assertEquals(CounterMutationResult.ProjectArchived, repository.undo(project.id))
            assertEquals(1L, repository.getProject(project.id)?.count)
            assertEquals(1, database.counterHistoryDao().countForProject(project.id))
        }

    @Test
    fun missingProjectReturnsStructuredResult() =
        runTest {
            assertEquals(
                CounterMutationResult.ProjectMissing,
                repository.mutate("missing", CounterMutation.Increment),
            )
            assertEquals(CounterMutationResult.ProjectMissing, repository.undo("missing"))
        }

    @Test
    fun rapidSequentialIncrementsAreAllStored() =
        runTest {
            val project = createProject()

            repeat(250) { repository.mutate(project.id, CounterMutation.Increment) }

            assertEquals(250L, repository.getProject(project.id)?.count)
            assertEquals(100, database.counterHistoryDao().countForProject(project.id))
        }

    @Test
    fun concurrentIncrementsAreSerializedWithoutLostUpdates() =
        runTest {
            val project = createProject()

            (1..200)
                .map {
                    async { repository.mutate(project.id, CounterMutation.Increment) }
                }.awaitAll()

            assertEquals(200L, repository.getProject(project.id)?.count)
        }

    @Test
    fun editingStartValueDoesNotAlterCurrentCountOrHistory() =
        runTest {
            val project = createProject(startValue = 0)
            repository.mutate(project.id, CounterMutation.ManualSet(25))

            val updated =
                repository.updateProject(
                    id = project.id,
                    name = " Renamed ",
                    counterUnit = CounterUnit.ROUNDS,
                    startValue = 1,
                    targetCount = 20,
                    repeatLength = 4,
                )

            requireNotNull(updated)
            assertEquals("Renamed", updated.name)
            assertEquals(CounterUnit.ROUNDS, updated.counterUnit)
            assertEquals(25L, updated.count)
            assertEquals(1, updated.startValue)
            assertEquals(1, database.counterHistoryDao().countForProject(project.id))
        }

    private suspend fun createProject(startValue: Int = 0) =
        repository.createProject(
            name = "Project",
            counterUnit = CounterUnit.ROWS,
            startValue = startValue,
            targetCount = null,
            repeatLength = null,
        )

    private fun assertChanged(
        result: CounterMutationResult,
        previous: Long,
        new: Long,
        reason: HistoryChangeReason,
    ) {
        assertEquals(CounterMutationResult.Changed(previous, new, reason), result)
    }
}
