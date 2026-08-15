package com.finnvek.rowtool.ui.screens.settings

import com.finnvek.rowtool.data.preferences.ThemeMode

data class SettingsScreenActions(
    val onBack: () -> Unit,
    val onThemeMode: (ThemeMode) -> Unit,
    val onHaptics: (Boolean) -> Unit,
    val onKeepAwake: (Boolean) -> Unit,
    val onExport: () -> Unit,
    val onImport: () -> Unit,
)
