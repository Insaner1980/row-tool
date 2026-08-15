package com.finnvek.rowtool.data.preferences

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK,
}

data class AppPreferences(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val hapticFeedbackEnabled: Boolean = true,
    val keepScreenAwake: Boolean = true,
    val lastActiveProjectId: String? = null,
)
