package com.finnvek.rowtool.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.MainActivity
import com.finnvek.rowtool.data.preferences.ThemeMode
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RowToolFlowTest {
    private val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val rules: TestRule =
        RuleChain
            .outerRule(
                PrepareApplicationStateRule {
                    container.preferencesRepository.setThemeMode(ThemeMode.SYSTEM)
                    container.preferencesRepository.setHapticFeedbackEnabled(true)
                    container.preferencesRepository.setKeepScreenAwake(true)
                    container.preferencesRepository.setLastActiveProjectId(null)
                },
            ).around(composeRule)

    @Test
    fun createCountCorrectUndoAndRecreateKeepsTheAcceptedCount() {
        createProject("Test scarf")

        composeRule.onNodeWithContentDescription("Add one row").performClick()
        composeRule.onNodeWithContentDescription("Add one row").performClick()
        composeRule.onNodeWithContentDescription("Add one row").performClick()
        waitForText("3")
        composeRule.onNodeWithContentDescription("Remove one row").performClick()
        waitForText("2")
        composeRule.onNodeWithContentDescription("Undo last count change").performClick()
        waitForText("3")

        composeRule.activityRule.scenario.recreate()
        waitForText("3")
    }

    @Test
    fun tappingCountSetsAnUndoableManualValue() {
        createProject("Manual counter")

        composeRule.onNodeWithText("0").performClick()
        composeRule.onNode(hasSetTextAction()).performTextClearance()
        composeRule.onNode(hasSetTextAction()).performTextInput("12")
        Espresso.closeSoftKeyboard()
        composeRule.onNodeWithText("Save").performClick()
        waitForText("12")
        composeRule.onNodeWithContentDescription("Undo last count change").performClick()
        waitForText("0")
    }

    @Test
    fun resetAndDeleteAreProtectedByNamedConfirmationDialogs() {
        createProject("Protected counter")

        composeRule.onNodeWithContentDescription("More options").performClick()
        composeRule.onNodeWithText("Reset count").performClick()
        composeRule.onNodeWithText("Reset count?").assertIsDisplayed()
        composeRule.onNodeWithText("Cancel").performClick()

        composeRule.onNodeWithContentDescription("More options").performClick()
        composeRule.onNodeWithText("Delete").performClick()
        composeRule.onNodeWithText("Delete project?").assertIsDisplayed()
        composeRule.onNodeWithText("Delete").assertIsDisplayed()
    }

    private fun createProject(name: String) {
        composeRule.onNodeWithText("New project").performClick()
        composeRule.onNode(hasSetTextAction()).performTextInput(name)
        Espresso.closeSoftKeyboard()
        composeRule.onNodeWithText("Save").performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithContentDescription("Add one row").fetchSemanticsNodes().size == 1
        }
    }

    private fun waitForText(text: String) {
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText(text).assertIsDisplayed()
    }
}
