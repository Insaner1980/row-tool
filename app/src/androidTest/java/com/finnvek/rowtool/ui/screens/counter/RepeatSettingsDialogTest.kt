package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.assertTextFits
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepeatSettingsDialogTest {
    @get:Rule val composeRule = createComposeRule()

    @Test
    fun nextCountIsShownBeforeSavingAndDraftSurvivesRecreationAndFailure() {
        val saves = mutableListOf<Triple<String, Int?, Long?>>()
        var attempts = 0
        val restoration = StateRestorationTester(composeRule)
        restoration.setContent {
            RowToolTheme {
                RepeatSettingsDialog(
                    project = project(10),
                    onDismiss = {},
                    onSave = { id, length, start ->
                        saves += Triple(id, length, start)
                        attempts++
                        attempts > 1
                    },
                )
            }
        }
        composeRule.onNodeWithText("Start at next count").performScrollTo().performClick()
        composeRule.onNodeWithText("First repeat row").assertTextContains("11")
        assertFailedRepeatSaveAndRestore(composeRule, restoration)
        composeRule.onNodeWithText("Save").performClick()
        composeRule.waitUntil(5_000) { saves.size == 2 }
        assertEquals(listOf(11L, 11L), saves.map { it.third })
    }

    @Test
    fun maximumCountDisablesShortcutAndStartInputRejectsOutOfRangeValue() {
        composeRule.setContent {
            RowToolTheme {
                RepeatSettingsDialog(project = project(CounterConstants.MAX_COUNT), onDismiss = {}, onSave = { _, _, _ -> true })
            }
        }
        composeRule.onNodeWithText("Start at next count").performScrollTo().assertIsNotEnabled()
        composeRule.onNodeWithText("The main count is already at its maximum.").assertIsDisplayed()
        composeRule.onNodeWithText("First repeat row").performScrollTo().performTextReplacement("1000000")
        composeRule.onNodeWithText("Save").performScrollTo().assertIsNotEnabled()
        composeRule.onNodeWithText("First repeat row").performTextReplacement("999999")
        composeRule.onNodeWithText("Save").assertIsEnabled()
    }

    @Test
    fun narrowLargeTextEditorRemainsUsableWithKeyboardInBothThemes() {
        var dark by mutableStateOf(false)
        val savedStarts = mutableListOf<Long?>()
        composeRule.setContent {
            val density = LocalWindowInfo.current.containerSize.width / 320f
            CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 2f)) {
                RowToolTheme(darkTheme = dark) {
                    RepeatSettingsDialog(
                        project = project(10),
                        onDismiss = {},
                        onSave = { _, _, start ->
                            savedStarts += start
                            true
                        },
                    )
                }
            }
        }
        for (theme in listOf(false, true)) {
            composeRule.runOnIdle { dark = theme }
            composeRule.onNodeWithText("First repeat row").performScrollTo().performClick()
            composeRule.onNodeWithText("First repeat row").performTextReplacement("11")
            composeRule.onNodeWithText("Repeat settings").assertTextFits()
            composeRule
                .onNodeWithText("Save")
                .performScrollTo()
                .assertIsDisplayed()
                .assertHeightIsAtLeast(48.dp)
                .assertIsEnabled()
            if (theme) {
                composeRule.onNodeWithText("Save").performClick()
                composeRule.waitUntil(5_000) { savedStarts.size == 1 }
            }
        }
        assertEquals(listOf(11L), savedStarts)
    }

    private fun project(count: Long) =
        CounterProject(
            id = "repeat-project",
            name = "Scarf",
            counterUnit = CounterUnit.ROWS,
            count = count,
            startValue = 0,
            targetCount = null,
            repeatLength = 8,
            isArchived = false,
            createdAt = 1,
            updatedAt = 1,
        )
}
