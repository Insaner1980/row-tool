package com.finnvek.rowtool.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ComposeTimeoutException
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.ui.screens.counter.CounterRoute
import com.finnvek.rowtool.ui.screens.counter.CounterViewModel
import com.finnvek.rowtool.ui.screens.projects.ProjectsRoute
import com.finnvek.rowtool.ui.screens.projects.ProjectsViewModel
import com.finnvek.rowtool.ui.theme.RowToolTheme
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

class CounterLoadingContractTest {
    private val composeRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var database: RowToolDatabase
    private lateinit var repository: CounterRepository
    private lateinit var preferences: PreferencesRepository
    private lateinit var projectId: String
    private lateinit var counter: CounterViewModel
    private val writeEntered = CompletableDeferred<Unit>()
    private val releaseWrite = CompletableDeferred<Unit>()
    private var navigationAttempts = 0

    @get:Rule
    val rules: TestRule =
        RuleChain
            .outerRule(
                object : ExternalResource() {
                    override fun before() {
                        database =
                            Room
                                .inMemoryDatabaseBuilder(
                                    InstrumentationRegistry.getInstrumentation().targetContext,
                                    RowToolDatabase::class.java,
                                ).build()
                        repository = CounterRepository(database)
                        projectId =
                            runBlocking {
                                repository
                                    .createProject(
                                        "DIRECT START",
                                        com.finnvek.rowtool.domain.model.CounterUnit.ROWS,
                                        0,
                                        null,
                                        null,
                                    ).id
                            }
                        val store =
                            object : DataStore<Preferences> {
                                override val data =
                                    MutableStateFlow(preferencesOf(stringPreferencesKey("last_active_project_id") to projectId))

                                override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences {
                                    writeEntered.complete(Unit)
                                    releaseWrite.await()
                                    return transform(data.value).also { data.value = it }
                                }
                            }
                        preferences = PreferencesRepository(store, database.projectDao())
                    }

                    override fun after() {
                        // The inner Activity rule has already destroyed its ViewModels and composition.
                        releaseWrite.complete(Unit)
                        database.close()
                    }
                },
            ).around(composeRule)

    @Test
    fun composeIdleDoesNotMeanCounterDataReadyWhilePreferenceWriteIsSuspended() {
        showCounter(projectId)
        runBlocking { withTimeout(5_000) { writeEntered.await() } }
        composeRule.waitForIdle()
        assertTrue(counter.uiState.value.isLoading)
        assertNull(counter.uiState.value.project)
        assertEquals(projectId, runBlocking { repository.getProject(projectId) }!!.id)

        // Expected assertion failure checks the allowed loading schedule, not historical causation.
        val failure =
            runCatching {
                composeRule.captureAssertionFailure(
                    label = "controlled-loading",
                    activity = { composeRule.activity },
                    state = {
                        val current = counter.uiState.value
                        "fixture=$projectId loading=${current.isLoading} project=${current.project?.id}"
                    },
                ) { composeRule.onNodeWithText("DIRECT START").assertIsDisplayed() }
            }.exceptionOrNull()
        assertTrue(failure is AssertionError)
        assertTrue(failure!!.message!!.contains("DIRECT START"))
        var observedPendingData = false
        composeRule.awaitDirectCounter(projectId, selectedProjectId = {
            val loadedId =
                counter.uiState.value.project
                    ?.id
            if (!releaseWrite.isCompleted) {
                observedPendingData = loadedId == null
                releaseWrite.complete(Unit)
            }
            loadedId
        })
        assertTrue(observedPendingData)
        composeRule.onNodeWithText("DIRECT START").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Add one row").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Remove one row").assertIsDisplayed()
        assertEquals(
            projectId,
            counter.uiState.value.project
                ?.id,
        )
        assertEquals(0, navigationAttempts)
    }

    @Test
    fun readinessRejectsDifferentProjectEvenWithIdenticalCounterHeading() {
        val other =
            runBlocking {
                repository.createProject("DIRECT START", com.finnvek.rowtool.domain.model.CounterUnit.ROWS, 0, null, null)
            }
        releaseWrite.complete(Unit)
        showCounter(other.id)
        composeRule.waitUntil(5_000) {
            counter.uiState.value.project
                ?.id == other.id
        }
        composeRule.onNodeWithText("DIRECT START").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Add one row").assertIsDisplayed()
        assertReadinessTimeout("wrong-project") {
            counter.uiState.value.project
                ?.id
        }
    }

    @Test
    fun readinessRejectsLoadingThatNeverCompletes() {
        showCounter(projectId)
        runBlocking { withTimeout(5_000) { writeEntered.await() } }
        assertReadinessTimeout("never-ready") { projectId }
        assertTrue(counter.uiState.value.isLoading)
        assertTrue(!releaseWrite.isCompleted)
    }

    @Test
    fun readinessRejectsProjectsRowWithMatchingNameAndSelectedId() {
        releaseWrite.complete(Unit)
        composeRule.setContent {
            val projects: ProjectsViewModel = viewModel(factory = ProjectsViewModel.factory(repository, preferences))
            RowToolTheme {
                ProjectsRoute(projects, onOpenProject = {}, onSettings = {}, onMessage = {})
            }
        }
        composeRule.waitUntil(5_000) { composeRule.onNodeWithText("DIRECT START").isDisplayed() }
        composeRule.onNodeWithText("ACTIVE PROJECTS").assertIsDisplayed()
        assertReadinessTimeout("wrong-destination") { projectId }
    }

    private fun assertReadinessTimeout(
        label: String,
        selectedId: () -> String?,
    ) {
        val failure =
            runCatching {
                composeRule.captureAssertionFailure(
                    label = label,
                    activity = { composeRule.activity },
                    state = { "fixture=$projectId selected=${selectedId()}" },
                ) { composeRule.awaitDirectCounter(projectId, selectedId, timeoutMillis = 250) }
            }.exceptionOrNull()
        assertTrue("Expected readiness timeout, got $failure", failure is ComposeTimeoutException)
    }

    private fun showCounter(id: String) {
        composeRule.setContent {
            counter = viewModel(factory = CounterViewModel.factory(id, repository, preferences))
            RowToolTheme {
                CounterRoute(
                    viewModel = counter,
                    onProjects = { navigationAttempts++ },
                    onSettings = {},
                    onMessage = {},
                )
            }
        }
    }
}
