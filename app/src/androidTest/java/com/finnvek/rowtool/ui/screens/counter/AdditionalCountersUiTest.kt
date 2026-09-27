package com.finnvek.rowtool.ui.screens.counter

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToString
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.AdditionalCounter
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.assertTextFits
import com.finnvek.rowtool.ui.captureAssertionFailure
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.text.NumberFormat
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class AdditionalCountersUiTest {
    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var rowResources: Resources
    private var configurationEvidence = ""
    private var contentWidth = 0
    private var contentDensity = 0f

    @Test
    fun narrowLargeFontRowsFitBothThemesAndExposeOnlyManualButtons() {
        val dark = mutableStateOf(false)
        val locale = mutableStateOf(Locale.ENGLISH)
        val longName = "A very long sleeve counter name that still needs to be read"
        val manual = AdditionalCounter("manual", "p", longName, 999999, false)
        val linked = AdditionalCounter("linked", "p", "Linked", 42, true)
        val counters = listOf(manual, linked)
        var editedId = ""
        assertEquals(999999L, manual.count)
        assertEquals(CounterConstants.MAX_COUNT, manual.count)
        assertEquals(42L, linked.count)
        composeRule.setContent {
            LocalizedRows(locale.value, dark.value) {
                CounterScreenContent(
                    CounterUiState(project = project(), additionalCounters = counters),
                    actions().copy(additional = AdditionalCounterActions(onSetCount = { editedId = it })),
                )
            }
        }
        for ((tag, expected) in listOf("en" to "999,999", "fi" to "999\u00A0999")) {
            for (isDark in listOf(false, true)) {
                composeRule.runOnIdle {
                    locale.value = Locale.forLanguageTag(tag)
                    dark.value = isDark
                }
                composeRule.waitForIdle()
                val label = "additional-$tag-${if (isDark) "dark" else "light"}"
                composeRule.captureAssertionFailure(
                    label = label,
                    activity = {
                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED).single() as ComponentActivity
                    },
                    state = { "$configurationEvidence widthPx=$contentWidth dark=$isDark counters=$counters" },
                ) {
                    assertEquals(tag, rowResources.configuration.locales[0].language)
                    assertEquals(expected, NumberFormat.getIntegerInstance(locale.value).format(manual.count))
                    assertEquals(320f, contentWidth / contentDensity, 0.01f)
                    composeRule
                        .onNodeWithText(longName)
                        .performScrollTo()
                        .assertIsDisplayed()
                        .assertTextFits()
                    val value = assertCounterValue(manual, expected)
                    value.performClick()
                    assertEquals(manual.id, editedId)
                    recordRow(label, value)
                    composeRule
                        .onNodeWithContentDescription(rowResources.getString(R.string.additional_decrement, longName))
                        .performScrollTo()
                        .assertIsDisplayed()
                        .assertIsEnabled()
                        .assertWidthIsAtLeast(48.dp)
                        .assertHeightIsAtLeast(48.dp)
                    composeRule
                        .onNodeWithContentDescription(rowResources.getString(R.string.additional_increment, longName))
                        .performScrollTo()
                        .assertIsDisplayed()
                        .assertIsNotEnabled()
                        .assertWidthIsAtLeast(48.dp)
                        .assertHeightIsAtLeast(48.dp)
                    capture("$label-controls")
                    composeRule
                        .onNodeWithText("Linked")
                        .performScrollTo()
                        .assertIsDisplayed()
                        .assertTextFits()
                    assertCounterValue(linked, "42").performClick()
                    assertEquals(linked.id, editedId)
                    composeRule
                        .onNodeWithText(rowResources.getString(R.string.additional_following))
                        .performScrollTo()
                        .assertIsDisplayed()
                        .assertTextFits()
                    composeRule
                        .onNodeWithContentDescription(rowResources.getString(R.string.additional_increment, linked.name))
                        .assertDoesNotExist()
                    composeRule
                        .onNodeWithContentDescription(rowResources.getString(R.string.additional_decrement, linked.name))
                        .assertDoesNotExist()
                    recordRow("$label-linked", assertCounterValue(linked, "42"))
                    composeRule
                        .onNodeWithText(rowResources.getString(R.string.additional_add))
                        .performScrollTo()
                        .assertIsDisplayed()
                        .assertTextFits()
                }
            }
        }
    }

    @Test
    fun counterValueAssertionRejectsWrongValueAndMatchingValueInOtherRow() {
        val manual = AdditionalCounter("manual-negative", "p", "Manual negative", 41, false)
        val linked = AdditionalCounter("linked-negative", "p", "Linked negative", 999999, true)
        var editedId = ""
        composeRule.setContent {
            LocalizedRows(Locale.ENGLISH, false) {
                CounterScreenContent(
                    CounterUiState(project = project(), additionalCounters = listOf(manual, linked)),
                    actions().copy(additional = AdditionalCounterActions(onSetCount = { editedId = it })),
                )
            }
        }
        assertCounterValue(manual, "41").performClick()
        assertEquals(manual.id, editedId)
        assertCounterValue(linked, "999,999").performClick()
        assertEquals(linked.id, editedId)
        // The same positive assertion must reject both an incorrect value and the other row's value.
        assertThrows(AssertionError::class.java) { assertCounterValue(manual, "42") }
        assertThrows(AssertionError::class.java) { assertCounterValue(manual, "999,999") }
        assertCounterValue(manual, "41")
    }

    @Composable
    private fun LocalizedRows(
        locale: Locale,
        dark: Boolean,
        content: @Composable () -> Unit,
    ) {
        val context = LocalContext.current
        val configuration = Configuration(LocalConfiguration.current).apply { setLocale(locale) }
        rowResources = context.createConfigurationContext(configuration).resources
        val density = LocalDensity.current.density
        // Composition-scoped overrides never mutate the application's or device's locale/configuration.
        CompositionLocalProvider(
            LocalConfiguration provides configuration,
            LocalResources provides rowResources,
            LocalDensity provides Density(density, fontScale = 2f),
        ) {
            val effectiveDensity = LocalDensity.current
            contentDensity = effectiveDensity.density
            configurationEvidence = "locales=${LocalConfiguration.current.locales.toLanguageTags()} " +
                "resources=${LocalResources.current.configuration.locales.toLanguageTags()} " +
                "density=${effectiveDensity.density} fontScale=${effectiveDensity.fontScale} dark=$dark"
            assertEquals(2f, effectiveDensity.fontScale, 0f)
            RowToolTheme(darkTheme = dark) {
                Box(Modifier.width(320.dp).onSizeChanged { contentWidth = it.width }) { content() }
            }
        }
    }

    private fun assertCounterValue(
        counter: AdditionalCounter,
        expected: String,
    ): SemanticsNodeInteraction =
        composeRule
            .onNodeWithContentDescription(rowResources.getString(R.string.additional_edit_count, counter.name, counter.count))
            .performScrollTo()
            .assertIsDisplayed()
            .assertTextEquals(expected)
            .assertTextFits()
            .assertWidthIsAtLeast(48.dp)
            .assertHeightIsAtLeast(48.dp)

    private fun recordRow(
        label: String,
        value: SemanticsNodeInteraction,
    ) {
        val node = value.fetchSemanticsNode()
        val text = node.config[SemanticsProperties.Text].single().text
        val layouts = mutableListOf<androidx.compose.ui.text.TextLayoutResult>()
        value.performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(layouts) }
        assertTrue(layouts.isNotEmpty())
        val evidence =
            "$configurationEvidence widthPx=$contentWidth bounds=${node.boundsInRoot}\n" +
                "text=$text escaped=${text.map { "\\u%04X".format(it.code) }}\n" +
                layouts.joinToString("\n") { "textSize=${it.size} constraints=${it.layoutInput.constraints} text=${it.layoutInput.text}" }
        val directory = InstrumentationRegistry.getInstrumentation().targetContext.getExternalFilesDir(null)
        File(directory, "$label.txt").writeText(evidence)
        File(directory, "$label-merged.txt").writeText(composeRule.onRoot().printToString(maxDepth = Int.MAX_VALUE))
        File(directory, "$label-unmerged.txt").writeText(composeRule.onRoot(useUnmergedTree = true).printToString(maxDepth = Int.MAX_VALUE))
        capture(label)
    }

    @Test
    fun emptySectionHasDirectAddAction() {
        var additions = 0
        composeRule.setContent {
            RowToolTheme {
                CounterScreenContent(
                    CounterUiState(project = project()),
                    actions().copy(additional = AdditionalCounterActions(onAdd = { additions++ })),
                )
            }
        }
        composeRule.onNodeWithText("Add counter").performScrollTo().performClick()
        assertEquals(1, additions)
    }

    @Test
    fun editorDraftAndModeSurviveRestorationAndSavingDisablesDuplicateSubmission() {
        val restoration = StateRestorationTester(composeRule)
        val saving = mutableStateOf(false)
        var savedName = ""
        var savedMode = false
        var calls = 0
        restoration.setContent {
            RowToolTheme {
                AdditionalCounterEditorDialog(
                    counter = null,
                    isSaving = saving.value,
                    saveFailed = true,
                    onDismiss = {},
                    onSave = { name, mode ->
                        savedName = name
                        savedMode = mode
                        calls++
                    },
                )
            }
        }
        composeRule.onNodeWithText("Counter name").performTextInput("Sleeve")
        composeRule.onNodeWithText("Follow the main counter").performClick()
        restoration.emulateSavedInstanceStateRestore()
        composeRule.onNodeWithText("Sleeve").assertIsDisplayed()
        composeRule.onNodeWithText("Could not save the change. Try again.").assertIsDisplayed()
        composeRule.runOnIdle { saving.value = true }
        composeRule.onNodeWithText("Save").assertIsNotEnabled().performClick()
        assertEquals(0, calls)
        composeRule.runOnIdle { saving.value = false }
        composeRule.onNodeWithText("Save").performClick()
        assertEquals("Sleeve", savedName)
        assertEquals(true, savedMode)
        assertEquals(1, calls)
    }

    @Test
    fun countEditorKeepsDraftAcrossLiveCountChangesAndRestoration() {
        val restoration = StateRestorationTester(composeRule)
        val current = mutableLongStateOf(5L)
        var saved = 0L
        restoration.setContent {
            RowToolTheme {
                CountEditorDialog(current.longValue, {}, { saved = it }, saveFailed = true)
            }
        }
        composeRule.onNodeWithText("Count").performTextClearance()
        composeRule.onNodeWithText("Count").performTextInput("27")
        composeRule.runOnIdle { current.longValue = 8 }
        restoration.emulateSavedInstanceStateRestore()
        composeRule.onNodeWithText("27").assertIsDisplayed()
        composeRule.onNodeWithText("Save").performClick()
        assertEquals(27L, saved)
    }

    @Test
    fun largeFontEditorFitsBothThemesWithLongName() {
        val dark = mutableStateOf(false)
        val longName = "A very long sleeve counter name that still needs to be read"
        composeRule.setContent {
            val density = LocalDensity.current.density
            CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 2f)) {
                RowToolTheme(darkTheme = dark.value) {
                    AdditionalCounterEditorDialog(
                        counter = AdditionalCounter("counter", "p", longName, 0, false),
                        isSaving = false,
                        saveFailed = true,
                        onDismiss = {},
                        onSave = { _, _ -> },
                    )
                }
            }
        }
        for (isDark in listOf(false, true)) {
            composeRule.runOnIdle { dark.value = isDark }
            composeRule.onNodeWithText("Counter name").performClick()
            composeRule.onNodeWithText("Follow the main counter").performScrollTo().assertTextFits()
            composeRule.onNodeWithText("Save").assertIsDisplayed().assertTextFits()
            composeRule.onNodeWithText("Cancel").assertIsDisplayed().assertTextFits()
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            File(context.getExternalFilesDir(null), "additional-editor-${if (isDark) "dark" else "light"}.png").outputStream().use {
                composeRule
                    .onNode(isDialog())
                    .captureToImage()
                    .asAndroidBitmap()
                    .compress(Bitmap.CompressFormat.PNG, 100, it)
            }
        }
    }

    private fun capture(name: String) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        File(context.getExternalFilesDir(null), "$name.png").outputStream().use {
            composeRule
                .onRoot()
                .captureToImage()
                .asAndroidBitmap()
                .compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    private fun project() = CounterProject("p", "Project", CounterUnit.ROWS, 1, 0, null, null, false, 0, 0)

    private fun actions() =
        CounterScreenActions(
            CounterNavigationActions({}, {}),
            CounterValueActions({}, {}, {}, {}),
            CounterProjectActions({}, {}, {}, {}),
        )
}
