package com.finnvek.rowtool.ui

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.MainActivity
import com.finnvek.rowtool.R
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.data.preferences.ThemeMode
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class DirectCounterNavigationTest {
    private val composeRule = createAndroidComposeRule<MainActivity>()
    private lateinit var fixtureId: String

    @get:Rule
    val rules: TestRule =
        RuleChain
            .outerRule(
                PrepareApplicationStateRule {
                    val project =
                        container.counterRepository.createProject(
                            name = "Direct start",
                            counterUnit = CounterUnit.ROWS,
                            startValue = 0,
                            targetCount = null,
                            repeatLength = null,
                        )
                    container.preferencesRepository.setThemeMode(ThemeMode.SYSTEM)
                    container.preferencesRepository.setHapticFeedbackEnabled(true)
                    container.preferencesRepository.setKeepScreenAwake(true)
                    container.preferencesRepository.setLastActiveProjectId(project.id)
                    fixtureId = project.id
                },
            ).around(composeRule)

    @Test
    fun deletedProjectWhileStoppedReturnsToProjectsAfterRecreation() {
        assertDirectStart()
        val application =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as RowToolApplication
        val projectId = runBlocking { application.container.preferencesRepository.resolveLastActiveProjectId()!! }

        // Stop lifecycle-aware state collection before Room publishes the deletion.
        composeRule.activityRule.scenario.moveToState(Lifecycle.State.CREATED)
        runBlocking { application.container.counterRepository.deleteProject(projectId) }
        composeRule.activityRule.scenario.recreate()
        composeRule.activityRule.scenario.moveToState(Lifecycle.State.RESUMED)
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("Create your first project").fetchSemanticsNodes().isNotEmpty()
        }

        val projectDao = application.container.database.projectDao()
        assertEquals(0, runBlocking { projectDao.count() })
        composeRule.onNodeWithText("Create your first project").assertIsDisplayed()
        composeRule
            .onNodeWithText("New project")
            .assertIsDisplayed()
            .assertHasClickAction()
        pressBackAndAssertActivityNotResumed()
    }

    @Test
    fun emptyImportAfterDirectCounterFallbackDoesNotLeaveSettingsOnBackStack() {
        assertDirectStart()
        composeRule.onNodeWithContentDescription("Back").performClick()
        composeRule.onNodeWithText("ACTIVE PROJECTS").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Settings").performClick()
        val importAction =
            hasText(composeRule.activity.getString(R.string.action_import)) and
                hasClickAction() and hasAnyAncestor(hasScrollToIndexAction())
        composeRule.captureAssertionFailure(
            label = "settings-import",
            activity = { composeRule.activity },
        ) {
            composeRule.onNode(hasScrollToIndexAction()).performScrollToNode(importAction)
            composeRule
                .onNode(importAction)
                .assertIsDisplayed()
                .assertIsEnabled()
                .assertHasClickAction()
        }

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val backup = File.createTempFile("empty-backup", ".json", instrumentation.targetContext.cacheDir)
        backup.writeText("""{"schemaVersion":1,"application":"RowTool","exportedAt":0,"projects":[]}""")
        val picker =
            instrumentation.addMonitor(
                IntentFilter(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    addDataType("*/*")
                },
                ActivityResult(Activity.RESULT_OK, Intent().setData(Uri.fromFile(backup))),
                true,
            )
        try {
            composeRule.onNode(importAction).performClick()
            composeRule.waitUntil(timeoutMillis = 5_000) {
                composeRule.onAllNodesWithText("Replace all projects?").fetchSemanticsNodes().isNotEmpty()
            }
            composeRule.onNodeWithText("Replace projects").performClick()
            composeRule.waitUntil(timeoutMillis = 5_000) {
                composeRule.onAllNodesWithText("Create your first project").fetchSemanticsNodes().isNotEmpty()
            }
            composeRule.onNodeWithText("Create your first project").assertIsDisplayed()

            pressBackAndAssertActivityNotResumed()
        } finally {
            instrumentation.removeMonitor(picker)
            backup.delete()
        }
    }

    @Test
    fun projectsFromDirectCounterDoesNotLeaveCounterOnBackStack() {
        assertDirectStart()

        composeRule.onNodeWithContentDescription("Back").performClick()
        composeRule.onNodeWithText("ACTIVE PROJECTS").assertIsDisplayed()

        pressBackAndAssertActivityNotResumed()
    }

    private fun assertDirectStart() {
        composeRule.captureAssertionFailure(
            label = "direct-start",
            activity = { composeRule.activity },
            state = { "fixtureProjectId=$fixtureId" },
        ) {
            composeRule.awaitDirectCounter(
                expectedProjectId = fixtureId,
                selectedProjectId = { composeRule.runOnUiThread { composeRule.activity.existingStartupState()?.projectId } },
            )
        }
    }

    private fun pressBackAndAssertActivityNotResumed() {
        composeRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.activityRule.scenario.state != Lifecycle.State.RESUMED
        }

        assertNotEquals(Lifecycle.State.RESUMED, composeRule.activityRule.scenario.state)
    }
}
