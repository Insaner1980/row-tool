package com.finnvek.rowtool.widget

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class WidgetCountingTest {
    @get:Rule val folder = TemporaryFolder()
    private lateinit var database: RowToolDatabase
    private lateinit var repository: CounterRepository
    private lateinit var bindings: WidgetBindings

    @Before fun setup() {
        database =
            Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), RowToolDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        repository = CounterRepository(database)
        bindings = WidgetBindings(folder.root.resolve("bindings"))
    }

    @After fun close() = database.close()

    @Test fun widgetEffectsRemainAtomicUndoAndRetainHistoryLimit() =
        runTest {
            val project = repository.createProject("Effects", CounterUnit.ROWS, 0, null, null)
            repository.additionalCounters.save(project.id, null, "Follow", true)
            repository.additionalCounters.save(project.id, null, "Manual", false)
            val binding = bindings.bind(1, project.id)
            bindings.withBinding(1, binding.token) { repository.mutate(it, CounterMutation.Increment) }
            val counters = repository.additionalCounters.observe(project.id).first()
            assertEquals(1L, counters.single { it.name == "Follow" }.count)
            assertEquals(0L, counters.single { it.name == "Manual" }.count)
            assertEquals(
                2,
                repository.history
                    .observe(project.id)
                    .first()!!
                    .entries
                    .single()
                    .changes.size,
            )
            repository.undo(project.id)
            assertEquals(0L, repository.getProject(project.id)!!.count)
            assertEquals(
                0L,
                repository.additionalCounters
                    .observe(project.id)
                    .first()
                    .single { it.name == "Follow" }
                    .count,
            )
            repeat(101) { bindings.withBinding(1, binding.token) { repository.mutate(it, CounterMutation.Increment) } }
            assertEquals(
                100,
                repository.history
                    .observe(project.id)
                    .first()!!
                    .entries.size,
            )
            repository.mutate(project.id, CounterMutation.ManualSet(999999))
            val before = database.projectDao().getById(project.id)
            val history = repository.history.observe(project.id).first()
            bindings.withBinding(1, binding.token) { repository.mutate(it, CounterMutation.Increment) }
            assertEquals(before, database.projectDao().getById(project.id))
            assertEquals(history, repository.history.observe(project.id).first())
        }

    @Test fun simultaneousAppAndTwoWidgetsUseCurrentRoomCountAndHistory() =
        runTest {
            val project = repository.createProject("Project", CounterUnit.ROWS, 0, null, null)
            val one = bindings.bind(1, project.id)
            val two = bindings.bind(2, project.id)
            (1..30)
                .map { index ->
                    async {
                        when (index % 3) {
                            0 -> repository.mutate(project.id, CounterMutation.Increment)
                            1 -> bindings.withBinding(1, one.token) { repository.mutate(it, CounterMutation.Increment) }
                            else -> bindings.withBinding(2, two.token) { repository.mutate(it, CounterMutation.Increment) }
                        }
                    }
                }.awaitAll()
            assertEquals(30L, repository.getProject(project.id)!!.count)
            assertEquals(
                30,
                repository.history
                    .observe(project.id)
                    .first()!!
                    .entries.size,
            )
            repository.undo(project.id)
            assertEquals(29L, repository.getProject(project.id)!!.count)
        }

    @Test fun noOpArchiveDeleteAndRebindingCannotChangeAnotherProject() =
        runTest {
            val first = repository.createProject("First", CounterUnit.ROWS, 0, null, null)
            val second = repository.createProject("Second", CounterUnit.ROUNDS, 0, null, null)
            val old = bindings.bind(1, first.id)
            bindings.withBinding(1, old.token) { repository.mutate(it, CounterMutation.Decrement) }
            assertEquals(
                0,
                repository.history
                    .observe(first.id)
                    .first()!!
                    .entries.size,
            )
            repository.setArchived(first.id, true)
            bindings.withBinding(1, old.token) { repository.mutate(it, CounterMutation.Increment) }
            assertEquals(0L, repository.getProject(first.id)!!.count)
            bindings.bind(1, second.id)
            assertNull(bindings.withBinding(1, old.token) { repository.mutate(it, CounterMutation.Increment) })
            assertEquals(0L, repository.getProject(second.id)!!.count)
            repository.deleteProject(first.id)
            assertNull(repository.getProject(first.id))
        }
}
