package com.finnvek.rowtool.ui.screens.counter

import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.LocaleList
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.click
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.domain.model.CounterConstants
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
import java.io.File
import java.text.NumberFormat
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class CounterScreenContentTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun primaryPairAndSecondaryUndoKeepRolesTouchTargetsAndCallbackOwnership() {
        val clicks = IntArray(3)
        composeRule.setContent {
            RowToolTheme {
                CounterScreenContent(
                    state = CounterUiState(project = project(5, null, null), canUndo = true),
                    actions =
                        counterActions(
                            onIncrement = { clicks[0] += 1 },
                            onDecrement = { clicks[1] += 1 },
                            onUndo = { clicks[2] += 1 },
                        ),
                )
            }
        }
        val descriptions = listOf("Add one row", "Remove one row", "Undo last count change")
        val bounds =
            descriptions.map { description ->
                composeRule.onNodeWithContentDescription(description).fetchSemanticsNode().boundsInRoot
            }
        assertEquals(bounds[0].top, bounds[1].top, 1f)
        assertTrue(bounds[1].right <= bounds[0].left)
        assertTrue(bounds[2].top > bounds[0].bottom)
        assertEquals(bounds[0].width, bounds[1].width, 1f)
        assertTrue(bounds[2].width < bounds[0].width)
        descriptions.forEach { description ->
            composeRule
                .onNodeWithContentDescription(description)
                .performScrollTo()
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
                .assertWidthIsAtLeast(48.dp)
                .assertHeightIsAtLeast(48.dp)
                .performClick()
        }
        assertEquals(listOf(1, 1, 1), clicks.toList())
    }

    @Test
    fun maximumCountDisablesIncrement() {
        var increments = 0
        composeRule.setContent {
            RowToolTheme {
                CounterScreenContent(
                    state = CounterUiState(project = project(CounterConstants.MAX_COUNT, null, null)),
                    actions = counterActions(onIncrement = { increments += 1 }),
                )
            }
        }
        composeRule.onNodeWithContentDescription("Add one row").assertIsNotEnabled().performClick()
        assertEquals(0, increments)
    }

    @Test
    fun repeatDisplayAndMenuOpenSameEditorWithShiftedStart() {
        var opens = 0
        composeRule.setContent {
            RowToolTheme {
                CounterScreenContent(
                    state = CounterUiState(project = project(10, null, 8).copy(repeatStartCount = 11)),
                    actions = counterActions(onRepeatEdit = { opens++ }),
                )
            }
        }
        composeRule
            .onNodeWithText("Repeat 0/8")
            .performScrollTo()
            .assertHasClickAction()
            .performClick()
        composeRule.onNodeWithText("Starts at row 11").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("More options").performClick()
        composeRule.onNodeWithText("Repeat", useUnmergedTree = true).performClick()
        assertEquals(2, opens)
    }

    @Test
    fun zeroCountDisablesMinusAndUndoWhilePlusRemainsActionable() {
        var increments = 0
        var disabledClicks = 0
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
                            onDecrement = { disabledClicks += 1 },
                            onUndo = { disabledClicks += 1 },
                            onSetCount = { countEdits += 1 },
                        ),
                )
            }
        }

        composeRule.onNodeWithText("Repeat 0/6").assertIsDisplayed()
        composeRule.onNodeWithText("0 of 1 row").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Remove one row").assertIsNotEnabled().performClick()
        composeRule.onNodeWithContentDescription("Undo last count change").assertIsNotEnabled().performClick()
        composeRule.onNodeWithContentDescription("Add one row").performClick()
        composeRule
            .onNodeWithText("0")
            .assert(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.StateDescription,
                    "Edit count, currently 0",
                ),
            ).assertHasClickAction()
            .performTouchInput { click() }

        assertEquals(1, increments)
        assertEquals(0, disabledClicks)
        assertEquals(1, countEdits)
        val countNode = composeRule.onNodeWithText("0")
        assertEquals("Set count", countNode.fetchSemanticsNode().config[SemanticsActions.OnClick].label)
        countNode.performClick()
        assertEquals(2, countEdits)
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

    @Test
    fun archivedProjectDisablesCountEditingAndProjectMenu() {
        var countEdits = 0
        var controlClicks = 0

        composeRule.setContent {
            RowToolTheme {
                CounterScreenContent(
                    state =
                        CounterUiState(
                            project = project(count = 5, target = null, repeat = null).copy(isArchived = true),
                            canUndo = true,
                        ),
                    actions =
                        counterActions(
                            onSetCount = { countEdits += 1 },
                            onIncrement = { controlClicks += 1 },
                            onDecrement = { controlClicks += 1 },
                            onUndo = { controlClicks += 1 },
                        ),
                )
            }
        }

        composeRule.onNodeWithText("5").assertIsNotEnabled().performClick()
        composeRule.onNodeWithContentDescription("More options").assertIsNotEnabled()
        listOf("Add one row", "Remove one row", "Undo last count change").forEach { description ->
            composeRule.onNodeWithContentDescription(description).assertIsNotEnabled().performClick()
        }

        assertEquals(0, countEdits)
        assertEquals(0, controlClicks)
    }

    @Test
    fun optionalDetailsAndTargetProgressKeepTheirConditionsAndValues() {
        var currentProject by mutableStateOf(project(3, null, null))
        var increments = 0
        composeRule.setContent {
            RowToolTheme {
                CounterScreenContent(
                    state = CounterUiState(project = currentProject),
                    actions = counterActions(onIncrement = { increments += 1 }),
                )
            }
        }
        val progress = composeRule.onNode(SemanticsMatcher.keyIsDefined(SemanticsProperties.ProgressBarRangeInfo))
        progress.assertDoesNotExist()
        composeRule.onNodeWithText("Repeat", substring = true).assertDoesNotExist()

        composeRule.runOnIdle { currentProject = project(3, 6, null) }
        progress.assert(
            SemanticsMatcher.expectValue(SemanticsProperties.ProgressBarRangeInfo, ProgressBarRangeInfo(0.5f, 0f..1f)),
        )
        composeRule.onNodeWithText("3 of 6 rows").assertIsDisplayed()
        composeRule.onNodeWithText("Repeat", substring = true).assertDoesNotExist()

        composeRule.runOnIdle { currentProject = project(3, null, 6) }
        progress.assertDoesNotExist()
        composeRule.onNodeWithText("Repeat 3/6").assertIsDisplayed()
        composeRule.onNodeWithText("completed", substring = true).assertDoesNotExist()

        composeRule.runOnIdle { currentProject = project(7, 6, 6) }
        progress.assert(
            SemanticsMatcher.expectValue(SemanticsProperties.ProgressBarRangeInfo, ProgressBarRangeInfo(1f, 0f..1f)),
        )
        composeRule.onNodeWithText("Target reached").assertIsDisplayed()
        composeRule.onNodeWithText("Repeat 1/6").assertIsDisplayed()
        composeRule.onNodeWithText("1 repeat completed").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Add one row").performScrollTo().performClick()
        assertEquals(1, increments)
    }

    @Test
    fun projectTitleUsesTheConfigurationLocaleAndKeepsHeadingSemantics() {
        composeRule.setContent {
            val configuration =
                Configuration(LocalConfiguration.current).apply {
                    setLocales(LocaleList(Locale.forLanguageTag("tr")))
                }
            CompositionLocalProvider(LocalConfiguration provides configuration) {
                RowToolTheme {
                    CounterScreenContent(
                        state = CounterUiState(project = project(0, null, null).copy(name = "inci")),
                        actions = counterActions(),
                    )
                }
            }
        }
        composeRule.onNodeWithText("İNCİ").assert(SemanticsMatcher.keyIsDefined(SemanticsProperties.Heading))
    }

    @Test
    fun heroFitsNarrowAndPhoneWidthsWithLargeTextInBothThemes() {
        var configuration by mutableStateOf(Triple(320, 1f, false))
        var count by mutableLongStateOf(0L)
        composeRule.setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, configuration.second)) {
                RowToolTheme(darkTheme = configuration.third) {
                    key(configuration, count) {
                        Box(Modifier.width(configuration.first.dp)) {
                            CounterScreenContent(
                                state =
                                    CounterUiState(
                                        project =
                                            project(count, 999_999, 6).copy(
                                                name = "A very long project name for a garden scarf",
                                                counterUnit = if (count == 0L) CounterUnit.ROWS else CounterUnit.ROUNDS,
                                            ),
                                    ),
                                actions = counterActions(),
                            )
                        }
                    }
                }
            }
        }
        for (width in listOf(320, 400)) {
            for (scale in listOf(1f, 2f)) {
                for (dark in listOf(false, true)) {
                    for (value in listOf(0L, 999_999L)) {
                        composeRule.runOnIdle {
                            configuration = Triple(width, scale, dark)
                            count = value
                        }
                        val formatted = NumberFormat.getIntegerInstance().format(value)
                        composeRule
                            .onNodeWithText(formatted)
                            .performScrollTo()
                            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
                            .assert(SemanticsMatcher.keyIsDefined(SemanticsProperties.Heading))
                            .assertTextFits()
                        captureHero("hero-$width-$scale-$dark-$value")
                        composeRule.onNodeWithText(if (value == 0L) "ROWS" else "ROUNDS").assertTextFits()
                        composeRule
                            .onNodeWithText(if (value == 0L) "Repeat 0/6" else "Repeat 3/6")
                            .performScrollTo()
                            .assertTextFits()
                        if (value > 0L) {
                            composeRule.onNodeWithText("166666 repeats completed").performScrollTo().assertTextFits()
                            composeRule.onNodeWithText("Target reached").performScrollTo().assertTextFits()
                        } else {
                            composeRule
                                .onNodeWithText("0 of ${NumberFormat.getIntegerInstance().format(999_999)} rows")
                                .performScrollTo()
                                .assertTextFits()
                        }
                        composeRule.onNodeWithContentDescription("Undo last count change").performScrollTo().assertIsDisplayed()
                    }
                }
            }
        }
    }

    private fun captureHero(name: String) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val directory = File(context.getExternalFilesDir(null), "counter-presentation").apply { mkdirs() }
        File(directory, "$name.png").outputStream().use { output ->
            composeRule
                .onRoot()
                .captureToImage()
                .asAndroidBitmap()
                .compress(Bitmap.CompressFormat.PNG, 100, output)
        }
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
        onDecrement: () -> Unit = {},
        onUndo: () -> Unit = {},
        onRepeatEdit: () -> Unit = {},
    ) = CounterScreenActions(
        navigation = CounterNavigationActions({}, {}),
        value = CounterValueActions(onIncrement, onDecrement, onUndo, onSetCount),
        project = CounterProjectActions({}, {}, {}, {}, onRepeatEdit),
    )
}
