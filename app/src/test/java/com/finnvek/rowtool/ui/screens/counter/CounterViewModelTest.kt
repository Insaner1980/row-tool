package com.finnvek.rowtool.ui.screens.counter

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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
                ).allowMainThreadQueries()
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
}

// CPD-OFF: This small in-memory DataStore intentionally mirrors the settings test fixture.
private class InMemoryPreferencesDataStore : DataStore<Preferences> {
    private val state = MutableStateFlow<Preferences>(emptyPreferences())

    override val data: Flow<Preferences> = state

    override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences {
        val updated = transform(state.value)
        state.value = updated
        return updated
    }
}
// CPD-ON
