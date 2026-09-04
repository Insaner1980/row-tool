package com.finnvek.rowtool.ui.screens.counter

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.R
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.test.InMemoryPreferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
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
    fun deleteEmitsOneReturnToProjectsEffect() =
        runTest(dispatcher) {
            val counterRepository = CounterRepository(database, idGenerator = { "project" })
            val preferencesRepository = PreferencesRepository(InMemoryPreferencesDataStore(), database.projectDao())
            val project =
                counterRepository.createProject(
                    name = "Project",
                    counterUnit = CounterUnit.ROWS,
                    startValue = 0,
                    targetCount = null,
                    repeatLength = null,
                )
            val viewModel = CounterViewModel(project.id, counterRepository, preferencesRepository)
            val effects = mutableListOf<CounterEffect>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.uiState.collect()
            }
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.effects.collect(effects::add)
            }
            viewModel.uiState.first { !it.isLoading }

            viewModel.delete()
            advanceUntilIdle()

            assertEquals(null, counterRepository.getProject(project.id))
            assertEquals(1, effects.filterIsInstance<CounterEffect.ReturnToProjects>().size)
        }

    @Test
    fun archivedProjectEmissionReturnsToProjects() =
        runTest(dispatcher) {
            val counterRepository = CounterRepository(database, idGenerator = { "project" })
            val preferencesRepository = PreferencesRepository(InMemoryPreferencesDataStore(), database.projectDao())
            val project =
                counterRepository.createProject(
                    name = "Project",
                    counterUnit = CounterUnit.ROWS,
                    startValue = 0,
                    targetCount = null,
                    repeatLength = null,
                )
            val viewModel = CounterViewModel(project.id, counterRepository, preferencesRepository)
            val effects = mutableListOf<CounterEffect>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.uiState.collect()
            }
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.effects.collect(effects::add)
            }
            viewModel.uiState.first { !it.isLoading }

            counterRepository.setArchived(project.id, true)
            advanceUntilIdle()

            assertEquals(
                listOf(CounterEffect.ReturnToProjects(R.string.error_archived_project)),
                effects.filterIsInstance<CounterEffect.ReturnToProjects>(),
            )
        }
}
