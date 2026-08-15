package com.finnvek.rowtool.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val RowToolFontFamily = FontFamily.SansSerif

private fun rowToolTextStyle(
    weight: FontWeight,
    size: androidx.compose.ui.unit.TextUnit,
    letterSpacing: androidx.compose.ui.unit.TextUnit = 0.sp,
) = TextStyle(
    fontFamily = RowToolFontFamily,
    fontWeight = weight,
    fontSize = size,
    letterSpacing = letterSpacing,
)

internal val RowToolTypography =
    Typography(
        displayLarge = rowToolTextStyle(FontWeight.Bold, 57.sp, (-0.25).sp),
        displayMedium = rowToolTextStyle(FontWeight.Bold, 45.sp),
        displaySmall = rowToolTextStyle(FontWeight.SemiBold, 36.sp),
        headlineLarge = rowToolTextStyle(FontWeight.Bold, 32.sp),
        headlineMedium = rowToolTextStyle(FontWeight.SemiBold, 28.sp),
        headlineSmall = rowToolTextStyle(FontWeight.SemiBold, 24.sp),
        titleLarge = rowToolTextStyle(FontWeight.SemiBold, 22.sp),
        titleMedium = rowToolTextStyle(FontWeight.SemiBold, 16.sp, 0.15.sp),
        titleSmall = rowToolTextStyle(FontWeight.Medium, 14.sp, 0.1.sp),
        bodyLarge = rowToolTextStyle(FontWeight.Normal, 16.sp, 0.5.sp),
        bodyMedium = rowToolTextStyle(FontWeight.Normal, 14.sp, 0.25.sp),
        bodySmall = rowToolTextStyle(FontWeight.Normal, 12.sp, 0.4.sp),
        labelLarge = rowToolTextStyle(FontWeight.SemiBold, 14.sp, 0.1.sp),
        labelMedium = rowToolTextStyle(FontWeight.SemiBold, 12.sp, 0.5.sp),
        labelSmall = rowToolTextStyle(FontWeight.SemiBold, 11.sp, 1.5.sp),
    )
