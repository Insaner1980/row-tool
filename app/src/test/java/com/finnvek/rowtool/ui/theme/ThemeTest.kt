package com.finnvek.rowtool.ui.theme

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
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
        val colors = colorScheme(darkTheme = false)
        assertTrue(contrastRatio(colors.tertiary, colors.onTertiary) >= 4.5)
    }

    @Test
    fun lightSurfaceLevelsUseFamilyPalette() {
        val colors = colorScheme(darkTheme = false)
        assertEquals(colors.background, colors.surfaceContainerLowest)
        assertEquals(colors.surface, colors.surfaceContainerLow)
        assertEquals(LightSurfaceMediumHigh, colors.surfaceContainer)
    }

    @Test
    fun darkSurfaceLevelsAndDividersUseFamilyPalette() {
        val colors = colorScheme(darkTheme = true)
        assertEquals(colors.background, colors.surfaceContainerLowest)
        assertEquals(colors.surface, colors.surfaceContainerLow)
        assertEquals(colors.surfaceVariant, colors.surfaceContainer)
        assertEquals(colors.surfaceVariant, colors.outlineVariant)
    }

    @Test
    fun primaryContainerUsesSameReadableAccentInBothThemes() {
        val light = colorScheme(darkTheme = false)
        val dark = colorScheme(darkTheme = true)
        assertEquals(dark.primaryContainer, light.primaryContainer)
        assertEquals(dark.onPrimaryContainer, light.onPrimaryContainer)
        assertTrue(contrastRatio(light.primaryContainer, light.onPrimaryContainer) >= 4.5)
    }

    @Test
    fun projectCardAndMenuContentMeetsMinimumContrast() {
        for (darkTheme in listOf(false, true)) {
            val colors = colorScheme(darkTheme)
            for (surface in listOf(colors.surfaceContainerLow, colors.surfaceContainer)) {
                for (content in listOf(colors.onSurface, colors.onSurfaceVariant, colors.secondary)) {
                    assertTrue(
                        "darkTheme=$darkTheme, surface=$surface, content=$content",
                        contrastRatio(surface, content) >= 4.5,
                    )
                }
            }
        }
    }

    @Test
    fun dialogAccentsDoNotLoseContrastWhenSurfaceRolesChange() {
        for (darkTheme in listOf(false, true)) {
            val colors = colorScheme(darkTheme)
            val previousSurface = if (darkTheme) DarkSurfaceRaised else LightSurfaceMediumHigh
            // Existing primary/dark-error contrast is limited; do not make it worse here.
            for (content in listOf(colors.primary, colors.error, colors.outline)) {
                assertTrue(
                    "darkTheme=$darkTheme, content=$content",
                    contrastRatio(colors.surfaceContainerHigh, content) >= contrastRatio(previousSurface, content),
                )
            }
        }
    }

    @Test
    fun lightErrorRemainsReadableOnBackgroundAndDialogSurface() {
        val colors = colorScheme(darkTheme = false)
        assertEquals(Color(0xFF8B3030), colors.error)
        assertTrue(contrastRatio(colors.background, colors.error) >= 4.5)
        assertTrue(contrastRatio(colors.surfaceContainerHigh, colors.error) >= 4.5)
    }

    private fun colorScheme(darkTheme: Boolean): ColorScheme {
        val controller = Robolectric.buildActivity(ComponentActivity::class.java).setup()
        val activity = controller.get()
        lateinit var colors: ColorScheme
        try {
            activity.setContent {
                RowToolTheme(darkTheme = darkTheme) {
                    colors = MaterialTheme.colorScheme
                }
            }
            shadowOf(activity.mainLooper).idle()
            return colors
        } finally {
            controller.pause().stop().destroy()
        }
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
