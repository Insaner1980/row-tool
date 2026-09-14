package com.finnvek.rowtool.ui.navigation

import android.net.Uri

object Screen {
    const val PROJECTS = "projects"
    const val SETTINGS = "settings"
    private const val COUNTER_BASE = "counter"
    internal const val COUNTER_PROJECT_ID_ARG = "projectId"
    const val COUNTER_PATTERN = "$COUNTER_BASE/{$COUNTER_PROJECT_ID_ARG}"

    fun counter(projectId: String): String = "$COUNTER_BASE/${Uri.encode(projectId)}"
}
