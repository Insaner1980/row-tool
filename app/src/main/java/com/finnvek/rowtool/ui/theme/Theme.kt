package com.finnvek.rowtool.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
    darkColorScheme(
        primary = DarkPrimary,
        onPrimary = Color.White,
        primaryContainer = DarkPrimaryContainer,
        onPrimaryContainer = DarkNavigation,
        secondary = DarkSecondary,
        onSecondary = DarkNavigation,
        secondaryContainer = DarkSecondaryContainer,
        onSecondaryContainer = DarkText,
        tertiary = DarkTertiary,
        onTertiary = DarkNavigation,
        tertiaryContainer = DarkTertiaryContainer,
        onTertiaryContainer = DarkText,
        error = DarkError,
        onError = Color.White,
        errorContainer = DarkErrorContainer,
        onErrorContainer = DarkText,
        background = DarkBackground,
        onBackground = DarkText,
        surface = DarkSurface,
        onSurface = DarkText,
        surfaceVariant = DarkSurfaceRaised,
        onSurfaceVariant = DarkTextSecondary,
        surfaceContainerLowest = DarkBackground,
        surfaceContainerLow = DarkSurface,
        surfaceContainer = DarkSurfaceRaised,
        // Keep dialog primary/error content from losing contrast on a lighter surface.
        surfaceContainerHigh = DarkSurfaceRaised,
        surfaceContainerHighest = DarkSurfaceHighest,
        outline = DarkTextMuted,
        outlineVariant = DarkSurfaceRaised,
        scrim = Color.Black,
        inverseSurface = LightSurface,
        inverseOnSurface = LightText,
        inversePrimary = LightPrimary,
    )

private val LightColorScheme =
    lightColorScheme(
        primary = LightPrimary,
        onPrimary = Color.White,
        primaryContainer = DarkPrimaryContainer,
        onPrimaryContainer = DarkNavigation,
        secondary = LightSecondary,
        onSecondary = Color.White,
        secondaryContainer = LightSecondaryContainer,
        onSecondaryContainer = LightText,
        tertiary = LightTertiary,
        onTertiary = DarkNavigation,
        tertiaryContainer = LightTertiaryContainer,
        onTertiaryContainer = LightText,
        // Retain RowTool's darker red for readable validation text.
        error = LightError,
        onError = Color.White,
        errorContainer = LightErrorContainer,
        onErrorContainer = LightText,
        background = LightBackground,
        onBackground = LightText,
        surface = LightSurface,
        onSurface = LightText,
        surfaceVariant = LightSurfaceRaised,
        onSurfaceVariant = LightTextSecondary,
        surfaceContainerLowest = LightBackground,
        surfaceContainerLow = LightSurface,
        surfaceContainer = LightSurfaceMediumHigh,
        // KnitTools' darker high surface drops error-text contrast below 4.5:1.
        surfaceContainerHigh = LightSurfaceMediumHigh,
        surfaceContainerHighest = LightSurfaceHighest,
        outline = LightTextMuted,
        outlineVariant = LightDivider,
        scrim = Color.Black,
        inverseSurface = DarkSurface,
        inverseOnSurface = DarkText,
        inversePrimary = DarkPrimaryContainer,
    )

@Composable
fun RowToolTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = RowToolTypography,
        shapes = RowToolShapes,
        content = content,
    )
}
