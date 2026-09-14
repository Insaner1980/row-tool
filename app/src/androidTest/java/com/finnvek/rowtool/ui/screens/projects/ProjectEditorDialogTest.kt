package com.finnvek.rowtool.ui.screens.projects

import android.content.res.Configuration
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
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Density
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
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
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.ImeAction, ImeAction.Done))
    }

    @Test
    fun outOfRangePastedTargetRemainsInvalidInsteadOfBeingTruncated() {
        composeRule.setContent {
            RowToolTheme {
                ProjectEditorDialog(
                    project = null,
                    onDismiss = {},
                    onSave = {},
                )
            }
        }

        composeRule.onNodeWithText("Project name").performTextInput("Project")
        composeRule.onNodeWithText("Set a target").performScrollTo().performClick()
        composeRule.onNodeWithText("Target count").performScrollTo().performTextInput("1000000")

        composeRule
            .onNodeWithText("Target count")
            .assertTextContains("1000000")
            .assert(SemanticsMatcher.keyIsDefined(SemanticsProperties.Error))
        composeRule.onNodeWithText("Save").performScrollTo().assertIsNotEnabled()
    }

    @Test
    fun createDefaultsAndSaveValuesAreUnchanged() {
        val saves = mutableListOf<ProjectEditorValues>()
        composeRule.setContent {
            RowToolTheme { ProjectEditorDialog(project = null, onDismiss = {}, onSave = saves::add) }
        }

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
        composeRule.onNodeWithText("Project name").performScrollTo().performTextInput("  Scarf  ")
        composeRule
            .onNodeWithText("Save")
            .performScrollTo()
            .assertIsEnabled()
            .performClick()

        composeRule.runOnIdle {
            assertEquals(listOf(ProjectEditorValues("Scarf", CounterUnit.ROWS, 0, null, null)), saves)
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
        composeRule.onNodeWithText("1").assertIsSelected()
        composeRule.onNodeWithText("Set a target").assertIsOn()
        composeRule.onNodeWithText("Track a repeat").assertIsOn()
        composeRule.onNodeWithText("Target count").assertTextContains("120")
        composeRule.onNodeWithText("Repeat length").assertTextContains("6")
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
        val save = resources.getString(R.string.action_save)
        val cancel = resources.getString(R.string.action_cancel)

        composeRule.onNodeWithText(target).performScrollTo().performTextReplacement("0")
        composeRule.onNodeWithText(repeat).performScrollTo().performTextReplacement("1")
        listOf(
            resources.getString(R.string.project_target_enabled),
            resources.getString(R.string.project_repeat_enabled),
            resources.getString(R.string.project_target_error, 1, 999_999L),
            resources.getString(R.string.project_repeat_error, 2, 999),
        ).forEach { text ->
            composeRule
                .onNodeWithText(text, useUnmergedTree = true)
                .performScrollTo()
                .assertIsDisplayed()
                .assertTextFits()
        }
        composeRule.onNodeWithText(save).performScrollTo().assertIsNotEnabled()
        composeRule.onNodeWithText(cancel).assertIsDisplayed()
        val saveBounds = composeRule.onNodeWithText(save).fetchSemanticsNode().boundsInRoot
        val cancelBounds = composeRule.onNodeWithText(cancel).fetchSemanticsNode().boundsInRoot
        composeRule.onNodeWithText(save, useUnmergedTree = true).assertTextFits()
        composeRule.onNodeWithText(cancel, useUnmergedTree = true).assertTextFits()
        assertFalse("Actions must not overlap", saveBounds.overlaps(cancelBounds))

        composeRule.onNodeWithText(target).performScrollTo().performTextReplacement("120")
        composeRule.onNodeWithText(repeat).performScrollTo().performTextReplacement("6")
        composeRule
            .onNodeWithText(save)
            .performScrollTo()
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()
        composeRule.runOnIdle {
            assertEquals(listOf(ProjectEditorValues("Scarf", CounterUnit.ROUNDS, 1, 120, 6)), saves)
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
