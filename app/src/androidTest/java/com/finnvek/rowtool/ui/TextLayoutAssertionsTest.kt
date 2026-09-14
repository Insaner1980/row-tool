package com.finnvek.rowtool.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test

class TextLayoutAssertionsTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun fittingTextPassesButClippedAndEllipsizedTextFail() {
        composeRule.setContent {
            RowToolTheme {
                Column(Modifier.width(200.dp)) {
                    Text("0", textAlign = TextAlign.Center)
                    Text("Wrapped text that needs several lines to remain readable")
                    Text("Clipped horizontally", modifier = Modifier.width(8.dp), maxLines = 1)
                    Text("Clipped vertically", modifier = Modifier.height(2.dp))
                    Text("Ellipsized", modifier = Modifier.width(32.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }
        composeRule.onNodeWithText("0").assertTextFits()
        composeRule.onNodeWithText("Wrapped text that needs several lines to remain readable").assertTextFits()
        listOf("Clipped horizontally", "Clipped vertically", "Ellipsized").forEach { text ->
            assertThrows(AssertionError::class.java) { composeRule.onNodeWithText(text).assertTextFits() }
        }
    }
}
