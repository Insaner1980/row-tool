package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CounterScreenContentTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun zeroCountDisablesMinusAndUndoWhilePlusRemainsActionable() {
        var increments = 0
        var countEdits = 0

        composeRule.setContent {
            RowToolTheme {
                CounterScreenContent(
                    state =
                        CounterUiState(
                            project = project(count = 0, target = 1, repeat = 6),
                            canUndo = false,
                        ),
                    actions =
                        counterActions(
                            onIncrement = { increments += 1 },
                            onSetCount = { countEdits += 1 },
                        ),
                )
            }
        }

        composeRule.onNodeWithText("Repeat 0/6").assertIsDisplayed()
        composeRule.onNodeWithText("0 of 1 row").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Remove one row").assertIsNotEnabled()
        composeRule.onNodeWithContentDescription("Undo last count change").assertIsNotEnabled()
        composeRule.onNodeWithContentDescription("Add one row").performClick()
        composeRule
            .onNodeWithText("0")
            .assert(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.StateDescription,
                    "Edit count, currently 0",
                ),
            ).assertHasClickAction()
            .performClick()

        assertEquals(1, increments)
        assertEquals(1, countEdits)
    }

    @Test
    fun completedRepeatAndReachedTargetRemainVisibleWithoutColorDependence() {
        composeRule.setContent {
            RowToolTheme(darkTheme = true) {
                // CPD-OFF: Explicit no-op callbacks keep this UI test scenario self-contained.
                CounterScreenContent(
                    state =
                        CounterUiState(
                            project = project(count = 6, target = 6, repeat = 6),
                            canUndo = true,
                        ),
                    actions = counterActions(),
                )
                // CPD-ON
            }
        }

        composeRule.onNodeWithText("Repeat 6/6").assertIsDisplayed()
        composeRule.onNodeWithText("1 repeat completed").assertIsDisplayed()
        composeRule.onNodeWithText("Target reached").assertIsDisplayed()
    }

    private fun project(
        count: Long,
        target: Long?,
        repeat: Int?,
    ) = CounterProject(
        id = "project",
        name = "Garden scarf",
        counterUnit = CounterUnit.ROWS,
        count = count,
        startValue = 0,
        targetCount = target,
        repeatLength = repeat,
        isArchived = false,
        createdAt = 1,
        updatedAt = 1,
    )

    private fun counterActions(
        onIncrement: () -> Unit = {},
        onSetCount: () -> Unit = {},
    ) = CounterScreenActions(
        navigation = CounterNavigationActions({}, {}),
        value = CounterValueActions(onIncrement, {}, {}, onSetCount),
        project = CounterProjectActions({}, {}, {}, {}),
    )
}
