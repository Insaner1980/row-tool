package com.finnvek.rowtool.ui

import android.graphics.Bitmap
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.unit.Density
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.domain.model.HistoryCountChange
import com.finnvek.rowtool.domain.model.RecentHistory
import com.finnvek.rowtool.domain.model.RecentHistoryEntry
import com.finnvek.rowtool.ui.screens.history.HistoryScreen
import com.finnvek.rowtool.ui.screens.history.HistoryUiState
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class HistoryLayoutTest {
    @get:Rule val compose = createComposeRule()

    @Test fun longNamesMultipleEffectsDeletedAndEmptyFitBothThemesAt320dpAnd200Percent() {
        val longName = "A very long additional counter name with several words"
        val history =
            RecentHistory(
                "A very long project name with several words for wrapping",
                (1L..12L).map { id ->
                    RecentHistoryEntry(
                        id,
                        "MANUAL_SET",
                        1700000000000,
                        if (id == 1L) {
                            listOf(
                                HistoryCountChange(null, null, false, 999998, 999999),
                                HistoryCountChange("a", longName, true, 999998, 999999),
                                HistoryCountChange("b", "Second counter", false, 0, 1),
                            )
                        } else {
                            listOf(HistoryCountChange("other-$id", "Counter $id", false, 1, 2))
                        },
                    )
                },
            )
        var dark by mutableStateOf(false)
        var state by mutableStateOf<HistoryUiState>(HistoryUiState.Content(history))
        compose.setContent {
            val density = LocalWindowInfo.current.containerSize.width / 320f
            CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 2f)) {
                RowToolTheme(darkTheme = dark) { HistoryScreen(state, {}, {}) }
            }
        }
        for (theme in listOf(false, true)) {
            compose.runOnIdle {
                dark = theme
                state = HistoryUiState.Content(history)
            }
            compose.onNodeWithText("Recent counting actions").assertIsDisplayed().assertTextFits()
            compose.onNodeWithTag("history-list").performScrollToNode(hasText(history.projectName))
            compose.onNodeWithText(history.projectName).assertTextFits()
            screenshot("history-${if (theme) "dark" else "light"}-header")
            compose.onNodeWithTag("history-list").performScrollToNode(hasText(longName))
            compose.onNodeWithText(longName).assertTextFits()
            compose.onNodeWithTag("history-list").performScrollToNode(hasText("Deleted"))
            compose.onNodeWithText("Deleted").assertIsDisplayed().assertTextFits()
            screenshot("history-${if (theme) "dark" else "light"}-effects")
            compose.onNodeWithTag("history-list").performScrollToNode(hasText("Second counter"))
            compose.onNodeWithText("Second counter").assertIsDisplayed().assertTextFits()
            compose.onNodeWithTag("history-list").performScrollToNode(hasText("Counter 12"))
            compose.onNodeWithText("Counter 12").assertIsDisplayed().assertTextFits()
            compose.runOnIdle { state = HistoryUiState.Content(history.copy(entries = emptyList())) }
            compose.onNodeWithTag("history-list").performScrollToNode(hasText("No retained counting actions."))
            compose.onNodeWithTag("history-empty").assertIsDisplayed().assertTextFits()
            screenshot("history-${if (theme) "dark" else "light"}-empty")
        }
    }

    @Test fun loadingErrorAndRetryAreNotReportedAsEmpty() {
        var state by mutableStateOf<HistoryUiState>(HistoryUiState.Loading)
        var retries = 0
        compose.setContent { RowToolTheme { HistoryScreen(state, {}, { retries++ }) } }
        compose.onNodeWithTag("history-loading").assertIsDisplayed()
        compose.onNodeWithTag("history-empty").assertDoesNotExist()
        compose.runOnIdle { state = HistoryUiState.Error }
        compose.onNodeWithText("Could not load counting actions.").assertIsDisplayed()
        compose.onNodeWithTag("history-empty").assertDoesNotExist()
        compose.onNodeWithText("Try again").performClick()
        assertEquals(1, retries)
    }

    private fun screenshot(name: String) {
        compose.waitForIdle()
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val bitmap = instrumentation.uiAutomation.takeScreenshot()
        File(instrumentation.targetContext.getExternalFilesDir(null), "$name.png").outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        bitmap.recycle()
    }
}
