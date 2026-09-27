package com.finnvek.rowtool.ui.screens.counter

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.R
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.test.InMemoryPreferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

// CPD-OFF: Test setup intentionally mirrors the settings ViewModel fixture.
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class CounterViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var database: RowToolDatabase

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        database =
            Room
                .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    RowToolDatabase::class.java,
                ).setQueryCoroutineContext(dispatcher)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.resetMain()
    }
    // CPD-ON

    @Test
    fun deletePublishesMissingProjectWithoutNavigationEffect() =
        runTest(dispatcher) {
            val fixture = createLoadedViewModel()

            fixture.viewModel.delete()
            advanceUntilIdle()

            assertEquals(null, fixture.repository.getProject(fixture.project.id))
            assertEquals(null, fixture.viewModel.uiState.value.project)
            assertEquals(false, fixture.viewModel.uiState.value.isLoading)
            assertEquals(emptyList<CounterEffect>(), fixture.effects)
        }

    @Test
    fun archivedProjectStateRemainsAvailableAfterFeedbackIsConsumed() =
        runTest(dispatcher) {
            val fixture = createLoadedViewModel()

            fixture.repository.setArchived(fixture.project.id, true)
            advanceUntilIdle()

            val state = fixture.viewModel.uiState.value
            assertEquals(true, state.project?.isArchived)
            assertEquals(false, state.isLoading)
            assertEquals(
                listOf(CounterEffect.ShowMessage(R.string.error_archived_project)),
                fixture.effects.filterIsInstance<CounterEffect.ShowMessage>(),
            )
        }

    @Test
    fun mutationCompletingWithoutCollectorDoesNotReplayButNewMutationsStillEmit() =
        runTest(dispatcher) {
            val fixture = createLoadedViewModel()
            val haptics = mutableListOf<CounterEffect.Haptic>()
            val collector =
                backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                    fixture.viewModel.haptics.collect(haptics::add)
                }
            // StandardTestDispatcher holds the requested mutation until the collector is gone.
            fixture.viewModel.increment()
            assertEquals(fixture.project, fixture.viewModel.uiState.value.project)
            collector.cancel()
            advanceUntilIdle()
            assertEquals(1L, fixture.repository.getProject(fixture.project.id)?.count)

            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                fixture.viewModel.haptics.collect(haptics::add)
            }
            advanceUntilIdle()
            assertEquals(emptyList<CounterEffect.Haptic>(), haptics)
            fixture.viewModel.increment()
            advanceUntilIdle()
            fixture.viewModel.decrement()
            advanceUntilIdle()
            fixture.viewModel.undo()
            advanceUntilIdle()
            assertEquals(List(3) { CounterEffect.Haptic(false) }, haptics)
            assertEquals(emptyList<CounterEffect>(), fixture.effects)
        }

    @Test
    fun targetAndRepeatFeedbackStayStrongAndManualChangesStaySilent() =
        runTest(dispatcher) {
            val fixture = createLoadedViewModel(targetCount = 2, repeatLength = 3)
            val haptics = mutableListOf<CounterEffect.Haptic>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                fixture.viewModel.haptics.collect(haptics::add)
            }
            repeat(3) {
                fixture.viewModel.increment()
                advanceUntilIdle()
            }
            fixture.viewModel.setCount(4)
            advanceUntilIdle()
            fixture.viewModel.reset()
            advanceUntilIdle()
            fixture.viewModel.decrement()
            advanceUntilIdle()
            assertEquals(listOf(false, true, true), haptics.map { it.strong })
        }

    @Test
    fun repeatBoundaryFeedbackUsesConfiguredStartWhileTargetRemainsStrong() =
        runTest(dispatcher) {
            val fixture = createLoadedViewModel(targetCount = 2, repeatLength = 8, repeatStartCount = 11)
            val haptics = mutableListOf<CounterEffect.Haptic>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                fixture.viewModel.haptics.collect(haptics::add)
            }
            fixture.viewModel.increment()
            advanceUntilIdle()
            fixture.viewModel.increment()
            advanceUntilIdle()
            fixture.viewModel.setCount(17)
            advanceUntilIdle()
            fixture.viewModel.increment()
            advanceUntilIdle()
            assertEquals(listOf(false, true, true), haptics.map { it.strong })
        }

    @Test
    fun reminderAndTargetTogetherEmitOneStrongHapticAndAcknowledgedReminderDoesNotReplay() =
        runTest(dispatcher) {
            val fixture = createLoadedViewModel(targetCount = 1)
            val reminder = fixture.repository.reminders.save(fixture.project.id, null, null, "Check", 1, null, true)!!
            val haptics = mutableListOf<CounterEffect.Haptic>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { fixture.viewModel.haptics.collect(haptics::add) }
            fixture.viewModel.increment()
            advanceUntilIdle()
            assertEquals(listOf(true), haptics.map { it.strong })
            fixture.repository.reminders.acknowledge(fixture.project.id, reminder.id, reminder.revision, 1)
            fixture.viewModel.setCount(0)
            advanceUntilIdle()
            fixture.viewModel.increment()
            advanceUntilIdle()
            assertEquals(listOf(true, true), haptics.map { it.strong }) // The project target still applies.
            fixture.viewModel.reset()
            advanceUntilIdle()
            assertEquals(2, haptics.size)
        }

    @Test
    fun reminderOnlyIsStrongOnIncrementButManualSetAndResetAreSilent() =
        runTest(dispatcher) {
            val fixture = createLoadedViewModel()
            val reminder = fixture.repository.reminders.save(fixture.project.id, null, null, "Check", 2, null, true)!!
            val haptics = mutableListOf<CounterEffect.Haptic>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { fixture.viewModel.haptics.collect(haptics::add) }
            fixture.viewModel.setCount(2)
            advanceUntilIdle()
            fixture.viewModel.reset()
            advanceUntilIdle()
            fixture.viewModel.increment()
            advanceUntilIdle()
            fixture.viewModel.increment()
            advanceUntilIdle()
            assertEquals(listOf(false, true), haptics.map { it.strong })
            fixture.repository.reminders.acknowledge(fixture.project.id, reminder.id, reminder.revision, 2)
            fixture.viewModel.decrement()
            advanceUntilIdle()
            fixture.viewModel.increment()
            advanceUntilIdle()
            assertEquals(listOf(false, true, false, false), haptics.map { it.strong })
        }

    private suspend fun TestScope.createLoadedViewModel(
        targetCount: Long? = null,
        repeatLength: Int? = null,
        repeatStartCount: Long? = if (repeatLength != null) 1L else null,
    ): CounterViewModelFixture {
        val repository = CounterRepository(database, idGenerator = { "project" })
        val preferences = PreferencesRepository(InMemoryPreferencesDataStore(), database.projectDao())
        val project = repository.createProject("Project", CounterUnit.ROWS, 0, targetCount, repeatLength, repeatStartCount)
        val viewModel = CounterViewModel(project.id, repository, preferences)
        val effects = mutableListOf<CounterEffect>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect() }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.effects.collect(effects::add) }
        viewModel.uiState.first { !it.isLoading }
        return CounterViewModelFixture(repository, project, viewModel, effects)
    }

    private data class CounterViewModelFixture(
        val repository: CounterRepository,
        val project: CounterProject,
        val viewModel: CounterViewModel,
        val effects: List<CounterEffect>,
    )
}
