package com.finnvek.rowtool.ui.screens.settings

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.finnvek.rowtool.ui.assertTextFits
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class LanguageDialogTest {
    @get:Rule val compose = createComposeRule()

    @Test fun largeFontBothThemesKeepScrollableRadioRowsAndActionsUsable() {
        var dark by mutableStateOf(false)
        var pending by mutableStateOf("")
        var applied = ""
        var cancelled = 0
        compose.setContent {
            CompositionLocalProvider(LocalDensity provides Density(LocalDensity.current.density, 2f)) {
                RowToolTheme(darkTheme = dark) {
                    LanguageDialog("", pending, false, { pending = it }, { cancelled++ }, { applied = pending })
                }
            }
        }
        for (theme in listOf(false, true)) {
            compose.runOnIdle { dark = theme }
            compose.onNode(hasScrollToIndexAction()).performScrollToNode(hasText("Nederlands"))
            compose
                .onNodeWithText("Nederlands")
                .assertHeightIsAtLeast(48.dp)
                .performClick()
                .assertIsSelected()
            assertEquals("", applied)
            compose.onNodeWithTag("language-apply").assertIsDisplayed().assertHeightIsAtLeast(48.dp)
            compose
                .onNodeWithText("Cancel")
                .assertIsDisplayed()
                .assertTextFits()
                .performClick()
            val instrumentation =
                androidx.test.platform.app.InstrumentationRegistry
                    .getInstrumentation()
            val bitmap = instrumentation.uiAutomation.takeScreenshot()
            java.io.File(instrumentation.targetContext.getExternalFilesDir(null), "language-dialog-$theme.png").outputStream().use {
                bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, it)
            }
            bitmap.recycle()
        }
        assertEquals(2, cancelled)
        compose.onNodeWithTag("language-apply").performClick()
        assertEquals("nl", applied)
    }
}
