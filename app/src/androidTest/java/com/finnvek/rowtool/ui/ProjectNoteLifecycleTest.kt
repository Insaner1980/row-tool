package com.finnvek.rowtool.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.MainActivity
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProjectNoteLifecycleTest {
    private val compose = createAndroidComposeRule<MainActivity>()
    private val container get() = testContainer()
    private lateinit var id: String

    @get:Rule val rules: TestRule =
        RuleChain
            .outerRule(
                PrepareApplicationStateRule {
                    id = container.counterRepository.createProject("Note lifecycle", CounterUnit.ROWS, 0, null, null).id
                    container.preferencesRepository.setLastActiveProjectId(id)
                },
            ).around(compose)

    @Test fun externalArchiveKeepsUnsavedEditorReadOnlyUntilConfirmedExit() {
        openNoteEditor()
        compose.onNodeWithTag("note-text").performTextReplacement("Unsaved work")
        runBlocking { container.counterRepository.setArchived(id, true) }
        compose.waitUntil(5000) { compose.onAllNodesWithTag("note-save").fetchSemanticsNodes().isEmpty() }
        compose.onNodeWithTag("note-text").assertTextContains("Unsaved work")
        compose.onNodeWithTag("note-back").performClick()
        compose.onNodeWithText("Discard changes?").assertIsDisplayed()
        compose.onNodeWithText("Cancel").performClick()
        compose.onNodeWithTag("note-text").assertTextContains("Unsaved work")
        runBlocking { container.counterRepository.deleteProject(id) }
        compose.onNodeWithTag("note-text").assertTextContains("Unsaved work")
        compose.onNodeWithTag("note-back").performClick()
        compose.onNodeWithText("Discard").performClick()
        compose.waitUntil(5000) { compose.onAllNodesWithTag("note-editor").fetchSemanticsNodes().isEmpty() }
        assertNull(
            runBlocking {
                container.counterRepository.notes
                    .load(id)
                    .note
            },
        )
    }

    @Test fun activityRecreationRetainsDraftAndSelectionButNeverReopensSavedDraft() {
        openNoteEditor()
        compose.onNodeWithTag("note-text").performTextReplacement("  Resume\n\n    🧶")
        compose.onNodeWithTag("note-attach").performScrollTo().performClick()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithTag("note-text").performScrollTo().assertTextContains("  Resume\n\n    🧶")
        compose.onNodeWithTag("note-attach").performScrollTo().assertIsOff()
        compose.onNodeWithTag("note-save").performClick()
        compose.activityRule.scenario.recreate()
        compose.waitUntil(5000) { compose.onAllNodesWithTag("note-editor").fetchSemanticsNodes().isEmpty() }
        val note =
            runBlocking {
                container.counterRepository.notes
                    .load(id)
                    .note!!
            }
        assertNull(note.savedCount)
        assertEquals("  Resume\n\n    🧶", note.text)
        compose.onNodeWithTag("note-preview").performScrollTo().performClick()
        compose.onNodeWithTag("note-text").assertTextContains(note.text)
        compose.onNodeWithTag("note-back").performClick()
        compose.onNodeWithText("Discard changes?").assertDoesNotExist()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithTag("note-editor").assertDoesNotExist()
    }

    @Test fun archivedNoteOpensReadOnlyFromProjectsAndRestoresForEditing() {
        runBlocking {
            container.counterRepository.mutate(id, CounterMutation.ManualSet(74))
            container.counterRepository.notes.save(id, null, "Archived\n    Work", true)
        }
        compose.waitUntil(5000) { compose.onAllNodesWithText("NOTE LIFECYCLE").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithContentDescription("Back").performClick()
        runBlocking { container.counterRepository.setArchived(id, true) }
        compose.onNodeWithText("ARCHIVED").performClick()
        compose.onNodeWithContentDescription("Options for Note lifecycle").performClick()
        compose.onNodeWithText("Note").performClick()
        compose.onNodeWithTag("note-text").assertTextContains("Archived\n    Work")
        compose.onNodeWithTag("note-save").assertDoesNotExist()
        compose.onNodeWithTag("note-delete").assertDoesNotExist()
        compose.onNodeWithTag("note-back").performClick()
        assertEquals(74L, runBlocking { container.counterRepository.getProject(id)!!.count })
        compose.onNodeWithContentDescription("Options for Note lifecycle").performClick()
        compose.onNodeWithText("Restore").performClick()
        compose.onNodeWithText("Note lifecycle").performClick()
        compose.onNodeWithTag("note-preview").performScrollTo().performClick()
        compose.onNodeWithTag("note-save").assertIsDisplayed()
    }

    private fun openNoteEditor() {
        compose.waitUntil(5000) { compose.onAllNodesWithText("NOTE LIFECYCLE").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithContentDescription("More options").performClick()
        compose.onNodeWithText("Note").performClick()
    }
}
