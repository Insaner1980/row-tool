package com.finnvek.rowtool.ui.screens.projects

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProjectEditorDialogTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun optionalNumberFieldsExposeNamedToggleState() {
        composeRule.setContent {
            RowToolTheme {
                ProjectEditorDialog(
                    project = null,
                    onDismiss = {},
                    onSave = {},
                )
            }
        }

        composeRule.onNodeWithText("Set a target").assertIsOff().performClick()
        composeRule.onNodeWithText("Target count").assertIsDisplayed()
        composeRule.onNodeWithText("Track a repeat").assertIsOff().performClick()
        composeRule.onNodeWithText("Repeat length").assertIsDisplayed()
    }
}
