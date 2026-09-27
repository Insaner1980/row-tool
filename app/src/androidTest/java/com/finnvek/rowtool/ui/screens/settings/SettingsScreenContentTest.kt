package com.finnvek.rowtool.ui.screens.settings

import android.app.LocaleManager
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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

    private lateinit var callbackResources: Resources

    @Test
    fun preferenceRowsAndActionsInvokeEachCallbackOnce() =
        withSystemLanguage {
            var preferences by mutableStateOf(AppPreferences())
            val themes = mutableListOf<ThemeMode>()
            val haptics = mutableListOf<Boolean>()
            val keepAwake = mutableListOf<Boolean>()
            var exports = 0
            var imports = 0
            var backs = 0
            composeRule.setContent {
                LocalizedCallbackSettings(
                    locale = Locale.ENGLISH,
                    preferences = preferences,
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

            composeRule.waitForIdle()
            scrollToTheme(R.string.settings_theme_system).assertIsSelected()
            val expectedThemes = mutableListOf<ThemeMode>()
            listOf(
                R.string.settings_theme_light to ThemeMode.LIGHT,
                R.string.settings_theme_dark to ThemeMode.DARK,
                R.string.settings_theme_system to ThemeMode.SYSTEM,
            ).forEach { (label, theme) ->
                scrollToTheme(label)
                    .assertIsNotSelected()
                    .assertHeightIsAtLeast(48.dp)
                    .performClick()
                    .assertIsSelected()
                assertNoChildActions(Role.RadioButton)
                expectedThemes.add(theme)
                composeRule.onNodeWithTag("language-dialog").assertDoesNotExist()
                composeRule.runOnIdle {
                    assertEquals(expectedThemes, themes)
                    assertTrue(haptics.isEmpty())
                    assertTrue(keepAwake.isEmpty())
                    assertEquals(0, exports)
                    assertEquals(0, imports)
                    assertEquals(0, backs)
                    assertEquals("", applicationLanguageTags())
                }
            }
            listOf(R.string.settings_haptics, R.string.settings_keep_awake).forEach { label ->
                scrollToText(callbackResources.getString(label))
                    .assertIsOn()
                    .assertHeightIsAtLeast(48.dp)
                    .performClick()
                    .assertIsOff()
                assertNoChildActions(Role.Switch)
            }
            listOf(R.string.action_export, R.string.action_import).forEach { label ->
                scrollToText(callbackResources.getString(label))
                    .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
                    .assertHeightIsAtLeast(48.dp)
                    .performClick()
            }
            composeRule.onNodeWithContentDescription(callbackResources.getString(R.string.action_back)).assertIsDisplayed().performClick()
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
    fun systemThemeActionIsIsolatedFromMatchingLanguageSummaryInEnglishAndFinnish() =
        withSystemLanguage {
            var locale by mutableStateOf(Locale.ENGLISH)
            val themes = mutableListOf<ThemeMode>()
            val unrelated = mutableListOf<String>()
            composeRule.setContent {
                LocalizedCallbackSettings(
                    locale = locale,
                    preferences = AppPreferences(themeMode = ThemeMode.DARK),
                    actions =
                        SettingsScreenActions(
                            onBack = { unrelated.add("back") },
                            onThemeMode = { themes.add(it) },
                            onHaptics = { unrelated.add("haptics") },
                            onKeepAwake = { unrelated.add("keepAwake") },
                            onExport = { unrelated.add("export") },
                            onImport = { unrelated.add("import") },
                        ),
                )
            }
            for (tag in listOf("en", "fi")) {
                composeRule.runOnIdle {
                    locale = Locale.forLanguageTag(tag)
                    themes.clear()
                    unrelated.clear()
                }
                composeRule.waitForIdle()
                assertEquals(tag, callbackResources.configuration.locales[0].language)
                val systemLabel = callbackResources.getString(R.string.settings_theme_system)
                val languageLabel = callbackResources.getString(R.string.settings_language)
                assertEquals(systemLabel, callbackResources.getString(R.string.language_system))
                val languageRow =
                    composeRule.onNode(
                        hasText(languageLabel) and hasText(systemLabel) and
                            SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button) and
                            hasAnyAncestor(hasScrollToIndexAction()),
                    )
                val themeRow = scrollToTheme(R.string.settings_theme_system).assertIsNotSelected()
                languageRow.assertIsDisplayed().assert(hasClickAction()).assertTextEquals(languageLabel, systemLabel)
                composeRule.onNodeWithTag("language-dialog").assertDoesNotExist()
                composeRule.runOnIdle {
                    assertTrue(themes.isEmpty())
                    assertTrue(unrelated.isEmpty())
                }
                themeRow.performClick()
                composeRule.runOnIdle {
                    assertEquals(listOf(ThemeMode.SYSTEM), themes)
                    assertTrue(unrelated.isEmpty())
                    assertEquals("", applicationLanguageTags())
                }
                composeRule.onNodeWithTag("language-dialog").assertDoesNotExist()
                languageRow.assertIsDisplayed().assertTextEquals(languageLabel, systemLabel)
                // This fixture records callbacks without updating its controlled DARK preference.
                themeRow.assertIsNotSelected()
                scrollToTheme(R.string.settings_theme_dark).assertIsSelected()
            }
        }

    @Composable
    private fun LocalizedCallbackSettings(
        locale: Locale,
        preferences: AppPreferences,
        actions: SettingsScreenActions,
    ) {
        val configuration = Configuration(LocalConfiguration.current).apply { setLocale(locale) }
        callbackResources = LocalContext.current.createConfigurationContext(configuration).resources
        CompositionLocalProvider(
            LocalConfiguration provides configuration,
            LocalResources provides callbackResources,
        ) {
            RowToolTheme(darkTheme = false) {
                SettingsScreenContent(preferences, "1.0.0", actions)
            }
        }
    }

    private fun scrollToTheme(label: Int): SemanticsNodeInteraction {
        val text = callbackResources.getString(label)
        val action =
            hasText(text) and isSelectable() and hasClickAction() and
                SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.RadioButton) and
                hasAnyAncestor(hasScrollToIndexAction())
        composeRule.onNode(hasScrollToIndexAction()).performScrollToNode(action)
        return composeRule
            .onNode(action)
            .assertIsDisplayed()
            .assertIsEnabled()
            .assertTextEquals(text)
    }

    private fun applicationLanguageTags(): String =
        if (Build.VERSION.SDK_INT >= 33) {
            InstrumentationRegistry
                .getInstrumentation()
                .targetContext
                .getSystemService(LocaleManager::class.java)
                .applicationLocales
                .toLanguageTags()
        } else {
            AppCompatDelegate.getApplicationLocales().toLanguageTags()
        }

    private fun withSystemLanguage(test: () -> Unit) {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val previous = applicationLanguageTags()

        fun setLanguage(tags: String) {
            instrumentation.runOnMainSync {
                if (Build.VERSION.SDK_INT >= 33) {
                    instrumentation.targetContext
                        .getSystemService(LocaleManager::class.java)
                        .applicationLocales = LocaleList.forLanguageTags(tags)
                } else {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tags))
                }
            }
        }
        try {
            setLanguage("")
            assertEquals("", applicationLanguageTags())
            test()
        } finally {
            setLanguage(previous)
            assertEquals(previous, applicationLanguageTags())
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
        lateinit var fallbackResources: Resources
        composeRule.setContent {
            val context = LocalContext.current
            val configuration =
                Configuration(LocalConfiguration.current).apply {
                    setLocales(LocaleList(Locale.forLanguageTag("tr"), Locale.forLanguageTag("fi")))
                }
            // Supply Finnish fallback text explicitly: dependency resources make Turkish an asset locale,
            // so requesting tr,fi for resources selects the default English text for this missing translation.
            val resourceConfiguration = Configuration(configuration).apply { setLocale(Locale.forLanguageTag("fi-FI")) }
            fallbackResources = context.createConfigurationContext(resourceConfiguration).resources
            CompositionLocalProvider(
                LocalConfiguration provides configuration,
                LocalResources provides fallbackResources,
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

        assertEquals("Tietoja sovelluksesta", fallbackResources.getString(R.string.settings_about))
        scrollToText("T\u0130ETOJA SOVELLUKSESTA")
            .assertTextEquals("T\u0130ETOJA SOVELLUKSESTA")
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Heading, Unit))
    }

    @Test
    fun aboutHeadingsUseEnglishAndFinnishResourcesAndCasing() {
        var locale by mutableStateOf(Locale.ENGLISH)
        composeRule.setContent {
            LocalizedCallbackSettings(
                locale = locale,
                preferences = AppPreferences(),
                actions = SettingsScreenActions({}, {}, {}, {}, {}, {}),
            )
        }

        listOf(
            Triple(Locale.ENGLISH, "About", "ABOUT"),
            Triple(Locale.forLanguageTag("fi"), "Tietoja sovelluksesta", "TIETOJA SOVELLUKSESTA"),
        ).forEach { (requestedLocale, resourceText, heading) ->
            composeRule.runOnIdle { locale = requestedLocale }
            composeRule.runOnIdle {
                assertEquals(requestedLocale.language, callbackResources.configuration.locales[0].language)
                assertEquals(resourceText, callbackResources.getString(R.string.settings_about))
            }
            scrollToText(heading)
                .assertTextEquals(heading)
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.Heading, Unit))
        }
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
