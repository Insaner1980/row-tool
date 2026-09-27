package com.finnvek.rowtool.ui.screens.projects

import android.content.res.Configuration
import android.os.ParcelFileDescriptor
import android.os.SystemClock
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.printToString
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Density
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.assertTextFits
import com.finnvek.rowtool.ui.captureAssertionFailure
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

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

        listOf("Set a target", "Track a repeat").forEach { label ->
            composeRule
                .onNodeWithText(label)
                .performScrollTo()
                .assertIsOff()
                .assertHasClickAction()
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Switch))
                .performClick()
                .assertIsOn()
            composeRule
                .onAllNodes(
                    hasClickAction() and hasAnyAncestor(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Switch)),
                    useUnmergedTree = true,
                ).assertCountEquals(0)
        }
        composeRule
            .onNodeWithText("Target count")
            .performScrollTo()
            .assertIsDisplayed()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.ImeAction, ImeAction.Next))
        composeRule
            .onNodeWithText("Repeat length")
            .performScrollTo()
            .assertIsDisplayed()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.ImeAction, ImeAction.Next))
        composeRule
            .onNodeWithText("First repeat row")
            .performScrollTo()
            .assertIsDisplayed()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.ImeAction, ImeAction.Done))
    }

    @Test
    fun outOfRangePastedTargetRemainsInvalidInsteadOfBeingTruncated() {
        val saves = mutableListOf<ProjectEditorValues>()
        var boundary = "Before input"
        lateinit var resources: android.content.res.Resources
        composeRule.setContent {
            resources = LocalResources.current
            RowToolTheme {
                ProjectEditorDialog(project = null, onDismiss = {}, onSave = saves::add)
            }
        }
        composeRule.captureAssertionFailure(
            label = "project-editor-target-input",
            activity = { ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED).single() },
            state = {
                "$boundary saves=$saves\n" +
                    listOf("window", "input_method").joinToString("\n") { service ->
                        val observedAt = SystemClock.elapsedRealtimeNanos()
                        val dump =
                            ParcelFileDescriptor
                                .AutoCloseInputStream(
                                    InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("dumpsys $service"),
                                ).bufferedReader()
                                .use { it.readText() }
                        "$service observedNanos=$observedAt\n$dump"
                    }
            },
        ) {
            val nameLabel = resources.getString(R.string.project_name_label)
            val toggleLabel = resources.getString(R.string.project_target_enabled)
            val targetLabel = resources.getString(R.string.project_target_label)
            val saveLabel = resources.getString(R.string.action_save)
            val field = composeRule.onNode(hasText(targetLabel) and hasSetTextAction())
            val toggle =
                composeRule.onNode(
                    hasText(toggleLabel) and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Switch),
                )
            EditorSaveProbe(composeRule, saveLabel).use { probe ->
                composeRule.onNode(hasText(nameLabel) and hasSetTextAction()).performTextInput("Project")
                boundary = "Name input returned nanos=${SystemClock.elapsedRealtimeNanos()}"
                // Text injection can return before the platform keyboard changes the dialog viewport.
                probe.awaitKeyboard(true)
                toggle
                    .performScrollTo()
                    .assertIsDisplayed()
                    .assertIsOff()
                    .performClick()
                    .assertIsOn()
                boundary += "; target enabled nanos=${SystemClock.elapsedRealtimeNanos()}"
            }
            field.performScrollTo().assertIsDisplayed().performTextInput("1000000")
            field
                .assertTextContains("1000000")
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.EditableText, AnnotatedString("1000000")))
                .assert(SemanticsMatcher.keyIsDefined(SemanticsProperties.Error))
            composeRule.onNodeWithText(saveLabel).performScrollTo().assertIsNotEnabled()
            composeRule.runOnIdle { assertTrue("Invalid target must not emit a save", saves.isEmpty()) }
            Log.i(
                "RowToolTarget",
                "invalid nanos=${SystemClock.elapsedRealtimeNanos()} locale=${resources.configuration.locales} " + field.printToString(),
            )

            field.performScrollTo().performTextReplacement(CounterConstants.MAX_COUNT.toString())
            field
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.EditableText, AnnotatedString("999999")))
                .assert(SemanticsMatcher.keyNotDefined(SemanticsProperties.Error))
            composeRule.onNodeWithText(saveLabel).performScrollTo().assertIsEnabled()
            composeRule.runOnIdle { assertTrue("Editing alone must not emit a save", saves.isEmpty()) }
            Log.i("RowToolTarget", "valid nanos=${SystemClock.elapsedRealtimeNanos()} " + field.printToString())
        }
    }

    @Test
    fun keyboardAppearanceAfterScrollRequiresTargetToBeReachedAgain() {
        lateinit var resources: android.content.res.Resources
        composeRule.setContent {
            resources = LocalResources.current
            RowToolTheme { ProjectEditorDialog(project = null, onDismiss = {}, onSave = {}) }
        }
        composeRule.captureAssertionFailure(
            label = "project-editor-target-keyboard",
            activity = { ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED).single() },
        ) {
            val toggleLabel = resources.getString(R.string.project_target_enabled)
            val fieldLabel = resources.getString(R.string.project_target_label)
            val toggle = composeRule.onNode(hasText(toggleLabel) and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Switch))
            EditorSaveProbe(composeRule, resources.getString(R.string.action_save)).use { probe ->
                composeRule.onNodeWithText(resources.getString(R.string.project_name_label)).performTextInput("Project")
                probe.awaitKeyboard(true)
                androidx.test.espresso.Espresso
                    .closeSoftKeyboard()
                probe.awaitKeyboard(false)
                toggle.performScrollTo().assertIsOff().assertIsDisplayed()
                Log.i("RowToolTarget", "before keyboard nanos=${SystemClock.elapsedRealtimeNanos()} " + toggle.printToString())
                composeRule.runOnUiThread {
                    probe.view.context
                        .getSystemService(InputMethodManager::class.java)
                        .showSoftInput(probe.view, 0)
                }
                probe.awaitKeyboard(true)
                Log.i("RowToolTarget", "after keyboard nanos=${SystemClock.elapsedRealtimeNanos()} " + toggle.printToString())
                toggle.assertIsNotDisplayed().performClick().assertIsOff()
                composeRule.onNodeWithText(fieldLabel).assertDoesNotExist()
                // One controlled negative activation above; repair the viewport before the positive comparison.
                toggle
                    .performScrollTo()
                    .assertIsDisplayed()
                    .performClick()
                    .assertIsOn()
                composeRule.onNode(hasText(fieldLabel) and hasSetTextAction()).performScrollTo().assertIsDisplayed()
            }
        }
    }

    @Test
    fun createDefaultsAndSaveValuesAreUnchanged() {
        val saves = mutableListOf<ProjectEditorValues>()
        var callbackObservation = "Callback not entered"
        var assertionObservation = "Callback assertion not reached"
        composeRule.setContent {
            RowToolTheme {
                ProjectEditorDialog(
                    project = null,
                    onDismiss = {},
                    onSave = {
                        callbackObservation =
                            "callbackNanos=${SystemClock.elapsedRealtimeNanos()} thread=${Thread.currentThread().name} value=$it"
                        Log.i("RowToolEditorSave", callbackObservation)
                        saves.add(it)
                    },
                )
            }
        }

        composeRule.captureAssertionFailure(
            label = "project-editor-create-save",
            activity = { ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED).single() },
            state = {
                "$assertionObservation\n$callbackObservation\n" +
                    listOf("window", "input_method").joinToString("\n") { service ->
                        // These later observations complement the interaction-time trace.
                        val observedAt = SystemClock.elapsedRealtimeNanos()
                        val dump =
                            runCatching {
                                ParcelFileDescriptor
                                    .AutoCloseInputStream(
                                        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("dumpsys $service"),
                                    ).bufferedReader()
                                    .use { it.readText() }
                            }.getOrElse { it.stackTraceToString() }
                        "$service observedNanos=$observedAt\n$dump"
                    }
            },
        ) {
            composeRule
                .onNodeWithText("New project")
                .assert(SemanticsMatcher.keyIsDefined(SemanticsProperties.Heading))
            composeRule
                .onNodeWithText("Project name")
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.EditableText, AnnotatedString("")))
            composeRule.onNodeWithText("Rows").assertIsSelected()
            composeRule.onNodeWithText("0").assertIsSelected()
            composeRule.onNodeWithText("Set a target").assertIsOff()
            composeRule.onNodeWithText("Track a repeat").assertIsOff()
            composeRule.onNodeWithText("Save").assertIsNotEnabled()
            EditorSaveProbe(composeRule).use { probe ->
                composeRule.onNodeWithText("Project name").performScrollTo().performTextInput("  Scarf  ")
                probe.mark("input completed")
                probe.awaitKeyboard(true)
                composeRule.onNodeWithText("Save").performScrollTo().assertIsEnabled()
                probe.mark("scroll completed")
                probe.awaitReachable()
                probe.clickSave()
            }

            composeRule.runOnIdle {
                assertionObservation = "assertionNanos=${SystemClock.elapsedRealtimeNanos()} saves=$saves; $callbackObservation"
                try {
                    assertEquals(listOf(ProjectEditorValues("Scarf", CounterUnit.ROWS, 0, null, null)), saves)
                } finally {
                    Log.i("RowToolEditorSave", assertionObservation)
                }
            }
        }
    }

    @Test
    fun keyboardAppearanceAfterScrollRequiresSaveToBeReachedAgain() {
        val saves = mutableListOf<ProjectEditorValues>()
        composeRule.setContent {
            RowToolTheme { ProjectEditorDialog(project = null, onDismiss = {}, onSave = saves::add) }
        }
        composeRule.captureAssertionFailure(
            label = "project-editor-keyboard-reachability",
            activity = { ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED).single() },
        ) {
            EditorSaveProbe(composeRule).use { probe ->
                composeRule.onNodeWithText("Project name").performScrollTo().performTextInput("  Scarf  ")
                probe.awaitKeyboard(true)
                androidx.test.espresso.Espresso
                    .closeSoftKeyboard()
                probe.awaitKeyboard(false)
                composeRule.onNodeWithText("Save").performScrollTo().assertIsEnabled()
                probe.awaitReachable()
                probe.mark("scrolled before keyboard")
                composeRule.runOnUiThread {
                    probe.view.context
                        .getSystemService(InputMethodManager::class.java)
                        .showSoftInput(probe.view, 0)
                }
                probe.awaitKeyboard(true)
                probe.mark("keyboard appeared after scroll")
                composeRule.onNodeWithText("Save").assertIsEnabled()
                assertFalse("The old scroll must not establish reachability after the viewport changes", probe.reachable())
                composeRule.onNodeWithText("Save").performScrollTo().assertIsEnabled()
                probe.awaitReachable()
                probe.clickSave()
                composeRule.runOnIdle {
                    assertEquals(listOf(ProjectEditorValues("Scarf", CounterUnit.ROWS, 0, null, null)), saves)
                }
            }
        }
    }

    @Test
    fun editShowsPersistedValuesAndSavesChangedChoicesOnce() {
        val project = existingProject()
        val saves = mutableListOf<ProjectEditorValues>()
        composeRule.setContent {
            RowToolTheme { ProjectEditorDialog(project = project, onDismiss = {}, onSave = saves::add) }
        }

        composeRule.onNodeWithText("Project name").assertTextContains("Scarf")
        composeRule.onNodeWithText("Rounds").assertIsSelected()
        composeRule.onNode(hasText("1") and SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Checkbox)).assertIsSelected()
        composeRule.onNodeWithText("Set a target").assertIsOn()
        composeRule.onNodeWithText("Track a repeat").assertIsOn()
        composeRule.onNodeWithText("Target count").assertTextContains("120")
        composeRule.onNodeWithText("Repeat length").assertTextContains("6")
        composeRule.onNodeWithText("First repeat round").assertTextContains("1")
        composeRule
            .onNodeWithText("Rows")
            .performScrollTo()
            .performClick()
            .assertIsSelected()
        composeRule
            .onNodeWithText("0")
            .performScrollTo()
            .performClick()
            .assertIsSelected()
        composeRule.onNodeWithText("Save").performScrollTo().performClick()

        composeRule.runOnIdle {
            assertEquals(listOf(ProjectEditorValues("Scarf", CounterUnit.ROWS, 0, 120, 6)), saves)
        }
    }

    @Test
    fun projectEditorSavesVisibleRepeatStartCount() {
        val saves = mutableListOf<ProjectEditorValues>()
        composeRule.setContent {
            RowToolTheme { ProjectEditorDialog(project = existingProject(), onDismiss = {}, onSave = saves::add) }
        }
        composeRule.onNodeWithText("First repeat round").performScrollTo().performTextReplacement("11")
        androidx.test.espresso.Espresso
            .closeSoftKeyboard()
        composeRule
            .onNodeWithText("Save")
            .performScrollTo()
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()
        composeRule.runOnIdle { assertEquals(11L, saves.single().repeatStartCount) }
    }

    @Test
    fun invalidRepeatPreventsSaveAndDisabledOptionalTextSurvivesRestoration() {
        val saves = mutableListOf<ProjectEditorValues>()
        val restorationTester = StateRestorationTester(composeRule)
        restorationTester.setContent {
            RowToolTheme {
                ProjectEditorDialog(project = existingProject(), onDismiss = {}, onSave = saves::add)
            }
        }

        composeRule.onNodeWithText("Repeat length").performScrollTo().performTextReplacement("1000")
        composeRule
            .onNodeWithText("Repeat length")
            .assertTextContains("1000")
            .assert(SemanticsMatcher.keyIsDefined(SemanticsProperties.Error))
        composeRule.onNodeWithText("Save").performScrollTo().assertIsNotEnabled()
        composeRule.onNodeWithText("Project name").performScrollTo().performTextReplacement("Changed")
        composeRule.onNodeWithText("Rows").performScrollTo().performClick()
        composeRule.onNodeWithText("0").performScrollTo().performClick()
        composeRule.onNodeWithText("Set a target").performScrollTo().performClick()
        composeRule.onNodeWithText("Track a repeat").performScrollTo().performClick()

        restorationTester.emulateSavedInstanceStateRestore()

        composeRule.onNodeWithText("Project name").assertTextContains("Changed")
        composeRule.onNodeWithText("Rows").assertIsSelected()
        composeRule.onNodeWithText("0").assertIsSelected()
        composeRule
            .onNodeWithText("Set a target")
            .performScrollTo()
            .assertIsOff()
            .performClick()
        composeRule.onNodeWithText("Target count").assertTextContains("120")
        composeRule.onNodeWithText("Set a target").performScrollTo().performClick()
        composeRule
            .onNodeWithText("Track a repeat")
            .performScrollTo()
            .assertIsOff()
            .performClick()
        composeRule.onNodeWithText("Repeat length").assertTextContains("1000")
        composeRule.onNodeWithText("Track a repeat").performScrollTo().performClick()
        composeRule
            .onNodeWithText("Save")
            .performScrollTo()
            .assertIsEnabled()
            .performClick()

        composeRule.runOnIdle {
            assertEquals(listOf(ProjectEditorValues("Changed", CounterUnit.ROWS, 0, null, null)), saves)
        }
    }

    @Test
    fun cancelInvokesDismissOnceWithoutSaving() {
        var dismissals = 0
        var saves = 0
        composeRule.setContent {
            RowToolTheme {
                ProjectEditorDialog(project = existingProject(), onDismiss = { dismissals++ }, onSave = { saves++ })
            }
        }
        composeRule.onNodeWithText("Cancel").performScrollTo().performClick()
        composeRule.runOnIdle {
            assertEquals(1, dismissals)
            assertEquals(0, saves)
        }
    }

    @Test
    fun narrowLargeTextLightFormWrapsFrenchActionsAndErrors() {
        assertLargeTextForm(locale = Locale.FRENCH, darkTheme = false)
    }

    @Test
    fun narrowLargeTextDarkFormWrapsItalianToggleLabels() {
        assertLargeTextForm(locale = Locale.ITALIAN, darkTheme = true)
    }

    private fun assertLargeTextForm(
        locale: Locale,
        darkTheme: Boolean,
    ) {
        lateinit var resources: android.content.res.Resources
        val saves = mutableListOf<ProjectEditorValues>()
        composeRule.setContent {
            val context = LocalContext.current
            val configuration = Configuration(LocalConfiguration.current).apply { setLocale(locale) }
            resources = context.createConfigurationContext(configuration).resources
            val density = LocalWindowInfo.current.containerSize.width / 320f
            CompositionLocalProvider(
                LocalResources provides resources,
                LocalDensity provides Density(density, fontScale = 2f),
            ) {
                RowToolTheme(darkTheme = darkTheme) {
                    ProjectEditorDialog(project = existingProject(), onDismiss = {}, onSave = saves::add)
                }
            }
        }
        composeRule.waitForIdle()
        val target = resources.getString(R.string.project_target_label)
        val repeat = resources.getString(R.string.project_repeat_label)
        val repeatStart = resources.getString(R.string.repeat_start_round)
        val targetError = resources.getString(R.string.project_target_error, 1, 999_999L)
        val repeatStartError = resources.getString(R.string.repeat_start_error, 999_999L)
        val save = resources.getString(R.string.action_save)
        val cancel = resources.getString(R.string.action_cancel)

        composeRule.onNodeWithText(target).performScrollTo().performTextReplacement("0")
        composeRule.onNodeWithText(repeat).performScrollTo().performTextReplacement("1")
        composeRule.onNodeWithText(repeatStart).performScrollTo().performTextReplacement("0")
        val uniqueErrors = if (repeatStartError == targetError) emptyList() else listOf(targetError, repeatStartError)
        val checkedTexts =
            listOf(
                resources.getString(R.string.project_target_enabled),
                resources.getString(R.string.project_repeat_enabled),
                resources.getString(R.string.project_repeat_error, 2, 999),
            ) + uniqueErrors
        checkedTexts.forEach { text ->
            composeRule
                .onNodeWithText(text, useUnmergedTree = true)
                .performScrollTo()
                .assertIsDisplayed()
                .assertTextFits()
        }
        composeRule
            .onAllNodesWithText(repeatStartError, useUnmergedTree = true)
            .assertCountEquals(if (repeatStartError == targetError) 2 else 1)
        composeRule.onNodeWithText(save).performScrollTo().assertIsNotEnabled()
        composeRule.onNodeWithText(cancel).assertIsDisplayed()
        val saveBounds = composeRule.onNodeWithText(save).fetchSemanticsNode().boundsInRoot
        val cancelBounds = composeRule.onNodeWithText(cancel).fetchSemanticsNode().boundsInRoot
        composeRule.onNodeWithText(save, useUnmergedTree = true).assertTextFits()
        composeRule.onNodeWithText(cancel, useUnmergedTree = true).assertTextFits()
        assertFalse("Actions must not overlap", saveBounds.overlaps(cancelBounds))

        composeRule.onNodeWithText(target).performScrollTo().performTextReplacement("120")
        composeRule.onNodeWithText(repeat).performScrollTo().performTextReplacement("6")
        composeRule.onNodeWithText(repeatStart).performScrollTo().performTextReplacement("11")
        composeRule
            .onNodeWithText(save)
            .performScrollTo()
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()
        composeRule.runOnIdle {
            assertEquals(listOf(ProjectEditorValues("Scarf", CounterUnit.ROUNDS, 1, 120, 6, 11)), saves)
        }
    }

    private fun existingProject() =
        CounterProject(
            id = "project",
            name = "Scarf",
            counterUnit = CounterUnit.ROUNDS,
            count = 25,
            startValue = 1,
            targetCount = 120,
            repeatLength = 6,
            isArchived = false,
            createdAt = 1,
            updatedAt = 1,
        )
}
