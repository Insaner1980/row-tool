package com.finnvek.rowtool.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.MainActivity
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.data.repository.BackupCodec
import com.finnvek.rowtool.data.repository.BackupDecodeResult
import com.finnvek.rowtool.data.repository.BackupFile
import com.finnvek.rowtool.data.repository.BackupImportResult
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoryNavigationTest {
    private val compose = createAndroidComposeRule<MainActivity>()
    private val container get() =
        (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as RowToolApplication)
            .container
    private val id = "imported / project ? # % ä"
    private val title = "Recent counting actions"

    @get:Rule val rules: TestRule =
        RuleChain
            .outerRule(
                PrepareApplicationStateRule {
                    CounterRepository(
                        container.database,
                        idGenerator = { id },
                    ).createProject("History work", CounterUnit.ROWS, 0, null, null)
                    container.preferencesRepository.setLastActiveProjectId(id)
                },
            ).around(compose)

    @Test fun counterRouteRecreationAndBackRetainExplicitImportedId() {
        openCounterHistory()
        compose.onNodeWithTag("history-empty").assertIsDisplayed()
        val other = runBlocking { container.counterRepository.createProject("Other", CounterUnit.ROWS, 0, null, null).id }
        runBlocking { container.preferencesRepository.setLastActiveProjectId(other) }
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("History work").assertIsDisplayed()
        compose.onNodeWithTag("history-back").performClick()
        compose.onNodeWithText("HISTORY WORK").assertIsDisplayed()
        compose.onNodeWithContentDescription("More options").performClick()
        compose.onNodeWithText(title).performClick()
        Espresso.pressBack()
        compose.onNodeWithText("HISTORY WORK").assertIsDisplayed()
        compose.onNodeWithContentDescription("Back").performClick()
        compose.onNodeWithText("ACTIVE PROJECTS").assertIsDisplayed()
        compose.onNodeWithTag("history-screen").assertDoesNotExist()
    }

    @Test fun projectsAndArchivedHistoryDoNotActivateOrWrite() {
        waitForCounter()
        compose.onNodeWithContentDescription("Back").performClick()
        compose.onNodeWithContentDescription("Options for History work").performClick()
        compose.onNodeWithText(title).performClick()
        compose.onNodeWithTag("history-empty").assertIsDisplayed()
        compose.onNodeWithTag("history-back").performClick()
        compose.onNodeWithText("ACTIVE PROJECTS").assertIsDisplayed()
        runBlocking { container.counterRepository.setArchived(id, true) }
        compose.onNodeWithContentDescription("Show archived projects").performClick()
        val before = runBlocking { container.counterRepository.getProject(id) }
        val preferences = runBlocking { container.preferencesRepository.preferences.first() }
        compose.onNodeWithContentDescription("Options for History work").performClick()
        compose.onNodeWithText(title).performClick()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithTag("history-empty").assertIsDisplayed()
        Espresso.pressBack()
        compose.onNodeWithContentDescription("Hide archived projects").assertIsDisplayed()
        compose.onNodeWithContentDescription("Options for History work").assertIsDisplayed()
        assertEquals(before, runBlocking { container.counterRepository.getProject(id) })
        assertEquals(preferences, runBlocking { container.preferencesRepository.preferences.first() })
    }

    @Test fun liveRoomChangesUndoRenameAndDeletionUpdateVisibleEffects() {
        val counter =
            runBlocking {
                container.counterRepository.additionalCounters.save(id, null, "Sleeve", true)
                container.database
                    .additionalCounterDao()
                    .getActive(id)
                    .single()
                    .id
            }
        openCounterHistory()
        runBlocking { container.counterRepository.mutate(id, CounterMutation.Increment) }
        waitForText("Sleeve")
        compose.onNodeWithTag("history-list").performScrollToNode(hasText("Sleeve"))
        compose.onNodeWithText("Sleeve").assertIsDisplayed()
        runBlocking {
            container.counterRepository.additionalCounters.save(id, counter, "Renamed sleeve", true)
            container.counterRepository.additionalCounters.delete(id, counter)
        }
        waitForText("Renamed sleeve")
        compose.onNodeWithTag("history-list").performScrollToNode(hasText("Deleted"))
        compose.onNodeWithText("Deleted").assertIsDisplayed()
        runBlocking { container.counterRepository.undo(id) }
        waitForTag("history-empty")
        compose.onNodeWithText("Renamed sleeve").assertDoesNotExist()
        assertTrue(
            runBlocking {
                container.database
                    .counterHistoryEffectDao()
                    .getAll()
                    .isEmpty()
            },
        )
    }

    @Test fun replacementWithSameIdRefreshesEffectsAndLegacyImportClearsList() {
        val backup =
            runBlocking {
                container.counterRepository.additionalCounters.save(id, null, "Restored sleeve", true)
                container.counterRepository.mutate(id, CounterMutation.ManualSet(24))
                container.backupRepository.exportJson()
            }
        runBlocking { container.counterRepository.mutate(id, CounterMutation.ManualSet(99)) }
        openCounterHistory()
        compose.onNodeWithTag("history-list").performScrollToNode(hasText("After: 99"))
        compose.onAllNodesWithText("After: 99")[0].assertIsDisplayed()
        val restored = (BackupCodec.decode(backup.encodeToByteArray()) as BackupDecodeResult.Valid).backup
        assertTrue(runBlocking { container.backupRepository.replaceWith(restored) } is BackupImportResult.Success)
        compose.waitUntil(5000) { compose.onAllNodesWithText("After: 99").fetchSemanticsNodes().isEmpty() }
        compose.onNodeWithTag("history-list").performScrollToNode(hasText("Restored sleeve"))
        compose.onNodeWithText("Restored sleeve").assertIsDisplayed()
        compose.onAllNodesWithText("After: 24")[0].assertIsDisplayed()
        val legacy =
            Json
                .decodeFromString<BackupFile>(
                    backup,
                ).copy(schemaVersion = 1, counters = null, history = null, reminders = null, notes = null)
        val old = (BackupCodec.decode(BackupCodec.encode(legacy).encodeToByteArray()) as BackupDecodeResult.Valid).backup
        assertTrue(runBlocking { container.backupRepository.replaceWith(old) } is BackupImportResult.Success)
        waitForTag("history-empty")
        compose.onNodeWithText("Restored sleeve").assertDoesNotExist()
    }

    @Test fun deletingOpenProjectReturnsToProjectsIncludingAfterRecreation() {
        openCounterHistory()
        runBlocking { container.counterRepository.deleteProject(id) }
        compose.activityRule.scenario.recreate()
        compose.waitUntil(5000) { compose.onAllNodesWithText("Create your first project").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithText("Create your first project").assertIsDisplayed()
        compose.onNodeWithTag("history-screen").assertDoesNotExist()
    }

    @Test fun deletingWhileStoppedReturnsToProjectsOnResume() {
        openCounterHistory()
        compose.activityRule.scenario.moveToState(Lifecycle.State.CREATED)
        runBlocking { container.counterRepository.deleteProject(id) }
        compose.activityRule.scenario.moveToState(Lifecycle.State.RESUMED)
        waitForText("Create your first project")
        compose.onNodeWithText("Create your first project").assertIsDisplayed()
        compose.onNodeWithTag("history-screen").assertDoesNotExist()
    }

    private fun openCounterHistory() {
        waitForCounter()
        compose.onNodeWithContentDescription("More options").performClick()
        compose.onNodeWithText(title).performClick()
        waitForTag("history-list")
        compose.waitUntil(5000) { compose.onAllNodesWithTag("history-loading").fetchSemanticsNodes().isEmpty() }
    }

    private fun waitForCounter() = waitForText("HISTORY WORK")

    private fun waitForText(text: String) {
        compose.waitUntil(5000) { compose.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty() }
    }

    private fun waitForTag(tag: String) {
        compose.waitUntil(5000) { compose.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty() }
    }
}
