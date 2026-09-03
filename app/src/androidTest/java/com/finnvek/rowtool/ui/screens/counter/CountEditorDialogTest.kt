package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountEditorDialogTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun outOfRangePastedCountRemainsInvalidInsteadOfKeepingThePreviousValue() {
        var savedCount: Long? = null
        composeRule.setContent {
            RowToolTheme {
                CountEditorDialog(
                    currentCount = 5,
                    onDismiss = {},
                    onSave = { savedCount = it },
                )
            }
        }

        composeRule.onNodeWithText("Count").performTextClearance()
        composeRule.onNodeWithText("Count").performTextInput("1000000")

        composeRule.onNodeWithText("Save").assertIsNotEnabled()
        assertNull(savedCount)
    }
}
