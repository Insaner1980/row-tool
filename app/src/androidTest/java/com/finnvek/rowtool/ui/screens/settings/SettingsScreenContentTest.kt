package com.finnvek.rowtool.ui.screens.settings

import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.R
import com.finnvek.rowtool.data.preferences.AppPreferences
import com.finnvek.rowtool.data.preferences.ThemeMode
import com.finnvek.rowtool.ui.assertTextFits
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class SettingsScreenContentTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun preferenceRowsAndActionsInvokeEachCallbackOnce() {
        var preferences by mutableStateOf(AppPreferences())
        val themes = mutableListOf<ThemeMode>()
        val haptics = mutableListOf<Boolean>()
        val keepAwake = mutableListOf<Boolean>()
        var exports = 0
        var imports = 0
        var backs = 0
        composeRule.setContent {
            RowToolTheme(darkTheme = false) {
                SettingsScreenContent(
                    preferences = preferences,
                    versionName = "1.0.0",
                    actions =
                        SettingsScreenActions(
                            onBack = { backs++ },
                            onThemeMode = {
                                themes.add(it)
                                preferences = preferences.copy(themeMode = it)
                            },
                            onHaptics = {
                                haptics.add(it)
                                preferences = preferences.copy(hapticFeedbackEnabled = it)
                            },
                            onKeepAwake = {
                                keepAwake.add(it)
                                preferences = preferences.copy(keepScreenAwake = it)
                            },
                            onExport = { exports++ },
                            onImport = { imports++ },
                        ),
                )
            }
        }

        scrollToText("System default").assertIsSelected()
        listOf("Light", "Dark", "System default").forEach { label ->
            scrollToText(label)
                .assertIsNotSelected()
                .assertHeightIsAtLeast(48.dp)
                .performClick()
                .assertIsSelected()
            assertNoChildActions(Role.RadioButton)
        }
        listOf("Haptic feedback", "Keep screen awake").forEach { label ->
            scrollToText(label)
                .assertIsOn()
                .assertHeightIsAtLeast(48.dp)
                .performClick()
                .assertIsOff()
            assertNoChildActions(Role.Switch)
        }
        listOf("Export data", "Import data").forEach { label ->
            scrollToText(label)
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
                .assertHeightIsAtLeast(48.dp)
                .performClick()
        }
        composeRule.onNodeWithContentDescription("Back").assertIsDisplayed().performClick()
        composeRule.runOnIdle {
            assertEquals(listOf(ThemeMode.LIGHT, ThemeMode.DARK, ThemeMode.SYSTEM), themes)
            assertEquals(listOf(false), haptics)
            assertEquals(listOf(false), keepAwake)
            assertEquals(1, exports)
            assertEquals(1, imports)
            assertEquals(1, backs)
        }
    }

    @Test
    fun darkThemePreservesAllAboutInformation() {
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
        scrollToText("Dark").assertIsSelected()
        listOf(
            "RowTool",
            "Version 1.0.0",
            "A Finnvek app",
            "Your project data stays on this device unless you export it.",
            "No ads, accounts, analytics, or subscriptions.",
        ).forEach { scrollToText(it) }
    }

    @Test
    fun sectionHeadingsUseTheAppLocaleWithLocalizedFallbackText() {
        composeRule.setContent {
            val context = LocalContext.current
            val configuration =
                Configuration(LocalConfiguration.current).apply {
                    setLocales(LocaleList(Locale.forLanguageTag("tr"), Locale.forLanguageTag("fi")))
                }
            val resources = context.createConfigurationContext(configuration).resources
            CompositionLocalProvider(
                LocalConfiguration provides configuration,
                LocalResources provides resources,
            ) {
                RowToolTheme {
                    SettingsScreenContent(
                        preferences = AppPreferences(),
                        versionName = "1.0.0",
                        actions = SettingsScreenActions({}, {}, {}, {}, {}, {}),
                    )
                }
            }
        }

        scrollToText("T\u0130ETOJA SOVELLUKSESTA")
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Heading, Unit))
    }

    @Test
    fun narrowLightSettingsWrapFrenchTextAsFontScaleIncreases() {
        assertLargeTextSettings(locale = Locale.FRENCH, darkTheme = false)
    }

    @Test
    fun narrowDarkSettingsWrapItalianTextAsFontScaleIncreases() {
        assertLargeTextSettings(locale = Locale.ITALIAN, darkTheme = true)
    }

    private fun assertLargeTextSettings(
        locale: Locale,
        darkTheme: Boolean,
    ) {
        lateinit var resources: Resources
        var fontScale by mutableFloatStateOf(1f)
        composeRule.setContent {
            val context = LocalContext.current
            val configuration = Configuration(LocalConfiguration.current).apply { setLocale(locale) }
            resources = context.createConfigurationContext(configuration).resources
            val density = LocalDensity.current.density
            CompositionLocalProvider(
                LocalConfiguration provides configuration,
                LocalResources provides resources,
                LocalDensity provides Density(density, fontScale),
            ) {
                RowToolTheme(darkTheme = darkTheme) {
                    Box(Modifier.width(320.dp)) {
                        SettingsScreenContent(
                            preferences = AppPreferences(),
                            versionName = "1.0.0",
                            actions = SettingsScreenActions({}, {}, {}, {}, {}, {}),
                        )
                    }
                }
            }
        }
        composeRule.waitForIdle()
        val keepAwake = resources.getString(R.string.settings_keep_awake)
        val normalHeight = scrollToText(keepAwake).fetchSemanticsNode().size.height
        composeRule.runOnIdle { fontScale = 2f }
        val largeHeight = scrollToText(keepAwake).fetchSemanticsNode().size.height
        assertTrue("Wrapped settings rows must grow with the text", largeHeight > normalHeight)

        listOf(
            R.string.settings_theme_system,
            R.string.settings_theme_light,
            R.string.settings_theme_dark,
            R.string.settings_haptics,
            R.string.settings_keep_awake,
            R.string.action_export,
            R.string.action_import,
        ).forEach { id -> scrollToText(resources.getString(id)).assertHeightIsAtLeast(48.dp) }
        listOf(
            R.string.settings_theme_system,
            R.string.settings_theme_light,
            R.string.settings_theme_dark,
            R.string.settings_haptics,
            R.string.settings_haptics_summary,
            R.string.settings_keep_awake,
            R.string.settings_keep_awake_summary,
            R.string.action_export,
            R.string.settings_export_summary,
            R.string.action_import,
            R.string.settings_import_summary,
            R.string.settings_privacy,
            R.string.settings_business_model,
        ).forEach { id ->
            val label = resources.getString(id)
            scrollToText(label)
            composeRule
                .onNodeWithText(label, useUnmergedTree = true)
                .assertTextFits()
        }
    }

    private fun scrollToText(text: String): SemanticsNodeInteraction {
        composeRule.onNode(hasScrollToIndexAction()).performScrollToNode(hasText(text))
        return composeRule.onNodeWithText(text).assertIsDisplayed()
    }

    private fun assertNoChildActions(role: Role) {
        composeRule
            .onAllNodes(
                hasClickAction() and hasAnyAncestor(SemanticsMatcher.expectValue(SemanticsProperties.Role, role)),
                useUnmergedTree = true,
            ).assertCountEquals(0)
    }
}
