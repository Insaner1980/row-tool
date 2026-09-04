package com.finnvek.rowtool.ui.screens.projects

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.ProjectEntity
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.test.InMemoryPreferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
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

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ProjectsViewModelTest {
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

    @Test
    fun projectLimitUsesDedicatedMessage() =
        runTest(dispatcher) {
            database.projectDao().insertAll(
                List(CounterConstants.MAX_PROJECTS_IN_BACKUP) { index ->
                    ProjectEntity(
                        id = "project-$index",
                        name = "Project $index",
                        counterUnit = CounterUnit.ROWS.name,
                        count = 0,
                        startValue = 0,
                        targetCount = null,
                        repeatLength = null,
                        isArchived = false,
                        createdAt = index.toLong(),
                        updatedAt = index.toLong(),
                    )
                },
            )
            assertEquals(CounterConstants.MAX_PROJECTS_IN_BACKUP, database.projectDao().count())
            val preferencesRepository =
                PreferencesRepository(InMemoryPreferencesDataStore(), database.projectDao())
            val viewModel = ProjectsViewModel(CounterRepository(database), preferencesRepository)
            val effect =
                async(UnconfinedTestDispatcher(testScheduler)) {
                    viewModel.effects.first()
                }

            viewModel.create(
                ProjectEditorValues(
                    name = "One project too many",
                    counterUnit = CounterUnit.ROWS,
                    startValue = 0,
                    targetCount = null,
                    repeatLength = null,
                ),
            )
            advanceUntilIdle()

            val message = effect.await() as ProjectsEffect.ShowMessage
            val context = ApplicationProvider.getApplicationContext<android.content.Context>()
            assertEquals("Project limit reached.", context.getString(message.message))
        }
}
