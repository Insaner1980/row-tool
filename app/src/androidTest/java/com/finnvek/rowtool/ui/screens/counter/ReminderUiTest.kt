package com.finnvek.rowtool.ui.screens.counter

import android.view.View
import android.view.WindowInsets
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.unit.Density
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.Reminder
import com.finnvek.rowtool.ui.assertTextFits
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReminderUiTest {
    @get:Rule val rule = createComposeRule()
    private val project = CounterProject("p", "Work", CounterUnit.ROWS, 32, 0, null, null, false, 1, 1)

    private fun reminder(
        id: String,
        message: String,
    ) = Reminder(id, "p", message, 32, null, true, null, 1)

    @Test fun listShowsWholeMessagesAndAcknowledgesOnlySelectedReminder() {
        val acknowledged = mutableListOf<String>()
        val longMessage = "Check the pattern before continuing the next row. ".repeat(3).trim()
        var dark by mutableStateOf(false)
        rule.setContent {
            val density = LocalWindowInfo.current.containerSize.width / 320f
            CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 2f)) {
                RowToolTheme(darkTheme = dark) {
                    ReminderDialogs(
                        project,
                        listOf(reminder("a", longMessage), reminder("b", "Second message")),
                        actions(onAcknowledge = { _, id, _, _ ->
                            acknowledged += id
                            true
                        }),
                        onDismiss = {},
                    )
                }
            }
        }
        for (theme in listOf(false, true)) {
            rule.runOnIdle { dark = theme }
            rule
                .onNodeWithText(longMessage)
                .performScrollTo()
                .assertIsDisplayed()
                .assertTextFits()
            rule
                .onNodeWithText("Second message")
                .performScrollTo()
                .assertIsDisplayed()
                .assertTextFits()
        }
        rule.onAllNodesWithText("Acknowledge")[0].performScrollTo().performClick()
        rule.waitUntil(5_000) { acknowledged.size == 1 }
        assertEquals(listOf("a"), acknowledged)
    }

    @Test fun editorRetainsDraftOnSaveFailureAndStateRestoration() {
        var attempts = 0
        var dark by mutableStateOf(false)
        var composeView: View? = null
        val restoration = StateRestorationTester(rule)
        restoration.setContent {
            composeView = LocalView.current
            val density = LocalWindowInfo.current.containerSize.width / 320f
            CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 2f)) {
                RowToolTheme(darkTheme = dark) {
                    ReminderDialogs(
                        project,
                        emptyList(),
                        actions(onSave = { _, _, _, _, _, _, _, _ ->
                            attempts++
                            attempts > 1
                        }),
                        onDismiss = {},
                    )
                }
            }
        }
        rule.onNodeWithText("Add reminder").performClick()
        rule.onNodeWithText("Message").performClick()
        rule.waitUntil(5_000) {
            rule.runOnIdle { composeView?.rootWindowInsets?.isVisible(WindowInsets.Type.ime()) == true }
        }
        rule.onNodeWithText("Message").performTextReplacement("Long draft that should remain readable")
        rule.onNodeWithText("Save").performScrollTo().assertIsDisplayed()
        rule.onNodeWithText("Save").performScrollTo().performClick()
        rule.waitUntil(5_000) { rule.onAllNodesWithText("Could not save the change. Try again.").fetchSemanticsNodes().isNotEmpty() }
        restoration.emulateSavedInstanceStateRestore()
        rule.onNodeWithText("Message").assertTextContains("Long draft that should remain readable")
        rule.runOnIdle { dark = true }
        rule.onNodeWithText("Save").performScrollTo().performClick()
        rule.waitUntil(5_000) { attempts == 2 }
        assertEquals(2, attempts)
    }

    @Test fun arrivalDoesNotMovePrimaryButtons() {
        var count by mutableLongStateOf(31)
        rule.setContent {
            RowToolTheme {
                CounterScreenContent(
                    state =
                        CounterUiState(
                            project = project.copy(count = count),
                            reminders = listOf(reminder("a", "Check the pattern")),
                        ),
                    actions =
                        CounterScreenActions(
                            CounterNavigationActions({}, {}),
                            CounterValueActions({ count++ }, {}, {}, {}),
                            CounterProjectActions({}, {}, {}, {}),
                        ),
                )
            }
        }
        val before =
            rule
                .onNodeWithContentDescription("Add one row")
                .fetchSemanticsNode()
                .boundsInRoot.top
        rule.onNodeWithContentDescription("Add one row").performClick()
        rule.waitForIdle()
        val after =
            rule
                .onNodeWithContentDescription("Add one row")
                .fetchSemanticsNode()
                .boundsInRoot.top
        assertEquals(before, after, 1f)
        rule.onNodeWithText("Check the pattern").assertIsDisplayed()
    }

    private fun actions(
        onSave: suspend (String, String?, Long?, String, Long, Long?, Boolean, String?) -> Boolean = { _, _, _, _, _, _, _, _ -> true },
        onAcknowledge: suspend (String, String, Long, Long) -> Boolean = { _, _, _, _ -> true },
    ) = ReminderActions(onSave, onAcknowledge, { _, _, _ -> true }, { _, _, _ -> true })
}
