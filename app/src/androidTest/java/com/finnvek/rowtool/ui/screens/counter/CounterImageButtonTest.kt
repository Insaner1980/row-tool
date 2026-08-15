package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.R
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CounterImageButtonTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun enabledButtonExposesDescriptionAndInvokesClick() {
        var clicks = 0

        composeRule.setContent {
            RowToolTheme {
                CounterImageButton(
                    imageRes = R.drawable.counter_plus_button,
                    contentDescription = "Add one row",
                    layout = CounterButtonLayout(visualSize = 128.dp, touchSize = 148.dp),
                    enabled = true,
                    onClick = { clicks += 1 },
                )
            }
        }

        composeRule
            .onNodeWithContentDescription("Add one row")
            .assertIsEnabled()
            .assertHasClickAction()
            .performClick()

        assertEquals(1, clicks)
    }

    @Test
    fun disabledButtonExposesDisabledSemanticsAndIgnoresClick() {
        var clicks = 0

        composeRule.setContent {
            RowToolTheme {
                CounterImageButton(
                    imageRes = R.drawable.counter_minus_button,
                    contentDescription = "Remove one row",
                    layout = CounterButtonLayout(visualSize = 100.dp, touchSize = 120.dp),
                    enabled = false,
                    onClick = { clicks += 1 },
                )
            }
        }

        composeRule
            .onNodeWithContentDescription("Remove one row")
            .assertIsNotEnabled()
            .assertHasClickAction()
            .performClick()

        assertEquals(0, clicks)
    }
}
