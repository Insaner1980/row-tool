package com.finnvek.rowtool.ui.screens.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.data.preferences.AppPreferences
import com.finnvek.rowtool.data.preferences.ThemeMode
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsScreenContentTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun themeAndCounterPreferencesUseVisibleAccessibleRows() {
        var theme: ThemeMode? = null
        var haptics: Boolean? = null
        var keepAwake: Boolean? = null

        composeRule.setContent {
            RowToolTheme {
                SettingsScreenContent(
                    preferences = AppPreferences(),
                    versionName = "1.0.0",
                    actions =
                        SettingsScreenActions(
                            onBack = {},
                            onThemeMode = { theme = it },
                            onHaptics = { haptics = it },
                            onKeepAwake = { keepAwake = it },
                            onExport = {},
                            onImport = {},
                        ),
                )
            }
        }

        composeRule.onNodeWithText("System default").assertIsSelected()
        composeRule.onNodeWithText("Haptic feedback").assertIsOn().performClick()
        composeRule.onNodeWithText("Keep screen awake").assertIsOn().performClick()
        composeRule.onNodeWithText("Dark").performClick()
        composeRule
            .onNodeWithText("No ads, accounts, analytics, or subscriptions.")
            .assertIsDisplayed()

        assertEquals(ThemeMode.DARK, theme)
        assertEquals(false, haptics)
        assertEquals(false, keepAwake)
    }

    @Test
    fun darkThemeRendersSettingsContent() {
        composeRule.setContent {
            RowToolTheme(darkTheme = true) {
                SettingsScreenContent(
                    preferences = AppPreferences(themeMode = ThemeMode.DARK),
                    versionName = "1.0.0",
                    actions = SettingsScreenActions({}, {}, {}, {}, {}, {}),
                )
            }
        }

        composeRule.onNodeWithText("Settings").assertIsDisplayed()
        composeRule.onNodeWithText("Dark").assertIsDisplayed()
        composeRule.onNodeWithText("Version 1.0.0").assertIsDisplayed()
    }
}
