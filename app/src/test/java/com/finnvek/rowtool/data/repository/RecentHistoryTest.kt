package com.finnvek.rowtool.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.CounterHistoryEntity
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class RecentHistoryTest {
    private lateinit var db: RowToolDatabase
    private lateinit var repository: CounterRepository
    private var time = 1L

    @Before fun setUp() {
        db =
            Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), RowToolDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        repository = CounterRepository(db, { time })
    }

    @After fun tearDown() = db.close()

    @Test fun emptyMissingAndArchivedAreDistinctAndReadsDoNotWrite() =
        runTest {
            val id = project()
            val before = db.projectDao().getById(id)
            repeat(3) { assertTrue(snapshot(id)!!.entries.isEmpty()) }
            assertEquals(before, db.projectDao().getById(id))
            assertNull(snapshot("missing"))
            repository.setArchived(id, true)
            assertTrue(snapshot(id)!!.entries.isEmpty())
        }

    @Test fun operationTypesAndActualMainValuesArePreserved() =
        runTest {
            val id = project()
            listOf(CounterMutation.Increment, CounterMutation.Decrement, CounterMutation.ManualSet(24), CounterMutation.Reset)
                .forEach { repository.mutate(id, it) }
            val entries = snapshot(id)!!.entries
            assertEquals(listOf("RESET", "MANUAL_SET", "DECREMENT", "INCREMENT"), entries.map { it.reason })
            assertEquals(
                listOf(24L to 0L, 0L to 24L, 1L to 0L, 0L to 1L),
                entries.map {
                    it.changes.single().let { c ->
                        c.before to c.after
                    }
                },
            )
        }

    @Test fun groupedEffectsUseClippedValuesAndManualCounterOmitsMain() =
        runTest {
            val id = project()
            val low = counter(id)
            val high = counter(id)
            repository.mutate(id, CounterMutation.ManualSet(CounterConstants.MAX_COUNT - 2), high)
            assertEquals(
                listOf(high),
                snapshot(id)!!
                    .entries
                    .single()
                    .changes
                    .map { it.counterId },
            )
            repository.mutate(id, CounterMutation.ManualSet(10))
            val changes = snapshot(id)!!.entries.first().changes
            assertEquals(3, changes.size)
            assertEquals(0L to 10L, changes.single { it.counterId == low }.let { it.before to it.after })
            assertEquals(
                CounterConstants.MAX_COUNT - 2 to CounterConstants.MAX_COUNT,
                changes.single { it.counterId == high }.let {
                    it.before to
                        it.after
                },
            )
            assertEquals(2, snapshot(id)!!.entries.size)
            repository.undo(id)
            assertEquals(
                listOf(high),
                snapshot(id)!!
                    .entries
                    .single()
                    .changes
                    .map { it.counterId },
            )
        }

    @Test fun namesDeletionAndSameNameIdentityRemainVisible() =
        runTest {
            val id = project()
            val a = counter(id)
            val b = counter(id)
            repository.mutate(id, CounterMutation.Increment)
            assertEquals(
                setOf(a, b),
                snapshot(id)!!
                    .entries
                    .single()
                    .changes
                    .mapNotNull { it.counterId }
                    .toSet(),
            )
            repository.additionalCounters.save(id, a, "Renamed", true)
            repository.additionalCounters.delete(id, a)
            val change =
                snapshot(id)!!
                    .entries
                    .single()
                    .changes
                    .single { it.counterId == a }
            assertEquals("Renamed", change.name)
            assertTrue(change.deleted)
            assertEquals(
                listOf(b),
                repository.additionalCounters
                    .observe(id)
                    .first()
                    .map { it.id },
            )
        }

    @Test fun stableOrderIsolationNoOpAndRetentionCountOperationsNotEffects() =
        runTest {
            val id = project()
            val other = project()
            counter(id)
            repeat(101) {
                time = if (it < 50) 900000000L else 1L
                repository.mutate(id, CounterMutation.Increment)
            }
            val entries = snapshot(id)!!.entries
            assertEquals(CounterConstants.MAX_HISTORY_ENTRIES, entries.size)
            assertEquals(entries.map { it.id }.sortedDescending(), entries.map { it.id })
            assertEquals(
                101L,
                entries
                    .first()
                    .changes
                    .first()
                    .after,
            )
            assertEquals(
                2L,
                entries
                    .last()
                    .changes
                    .first()
                    .after,
            )
            assertTrue(entries.all { it.changes.size == 2 })
            assertTrue(snapshot(other)!!.entries.isEmpty())
            repository.mutate(id, CounterMutation.ManualSet(101))
            assertEquals(entries, snapshot(id)!!.entries)
            time = 9000000000L
            assertEquals(entries, snapshot(id)!!.entries)
        }

    @Test fun unknownReasonAndUnchangedValuesAreNotInvented() =
        runTest {
            val id = project()
            db.counterHistoryDao().insert(
                CounterHistoryEntity(projectId = id, previousCount = 9, newCount = 9, changeReason = "OLD", createdAt = -1),
            )
            assertEquals("OLD", snapshot(id)!!.entries.single().reason)
            assertTrue(
                snapshot(id)!!
                    .entries
                    .single()
                    .changes
                    .isEmpty(),
            )
        }

    private suspend fun project() = repository.createProject("Work", CounterUnit.ROWS, 0, null, null).id

    private suspend fun counter(id: String): String {
        repository.additionalCounters.save(id, null, "Same name", true)
        return db
            .additionalCounterDao()
            .getActive(id)
            .last()
            .id
    }

    private suspend fun snapshot(id: String) = repository.history.observe(id).first()
}
