package com.finnvek.rowtool

import android.app.LocaleManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

internal fun preserveFrameworkLanguage(context: Context) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
    if (context.getSystemService(LocaleManager::class.java).applicationLocales.isEmpty) return
    // AppCompat 1.8.0's upgrade check can run before its first Activity delegate exists.
    // Use its existing migration marker so a newer framework choice cannot be overwritten.
    val marker = ComponentName(context, "androidx.appcompat.app.AppLocalesMetadataHolderService")
    if (context.packageManager.getComponentEnabledSetting(marker) != PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
        context.packageManager.setComponentEnabledSetting(
            marker,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP,
        )
    }
}
