package com.finnvek.rowtool.ui.theme

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ThemeTest {
    @Test
    fun lightTertiaryForegroundMeetsMinimumContrast() {
        val activity = Robolectric.buildActivity(ComponentActivity::class.java).setup().get()
        var tertiary = Color.Unspecified
        var onTertiary = Color.Unspecified
        activity.setContent {
            RowToolTheme(darkTheme = false) {
                tertiary = MaterialTheme.colorScheme.tertiary
                onTertiary = MaterialTheme.colorScheme.onTertiary
            }
        }
        shadowOf(activity.mainLooper).idle()

        assertTrue(contrastRatio(tertiary, onTertiary) >= 4.5)
    }

    private fun contrastRatio(
        first: Color,
        second: Color,
    ): Double {
        val firstLuminance = relativeLuminance(first)
        val secondLuminance = relativeLuminance(second)
        val lighter = maxOf(firstLuminance, secondLuminance)
        val darker = minOf(firstLuminance, secondLuminance)
        return (lighter + 0.05) / (darker + 0.05)
    }

    private fun relativeLuminance(color: Color): Double {
        fun linear(channel: Float): Double =
            if (channel <= 0.04045f) {
                channel / 12.92
            } else {
                Math.pow((channel + 0.055) / 1.055, 2.4)
            }

        return 0.2126 * linear(color.red) +
            0.7152 * linear(color.green) +
            0.0722 * linear(color.blue)
    }
}
