package com.finnvek.rowtool.ui.navigation

import android.net.Uri

object Screen {
    const val PROJECTS = "projects"
    const val SETTINGS = "settings"
    private const val COUNTER_BASE = "counter"
    internal const val COUNTER_PROJECT_ID_ARG = "projectId"
    const val COUNTER_PATTERN = "$COUNTER_BASE/{$COUNTER_PROJECT_ID_ARG}"
    const val HISTORY_PATTERN = "history/{$COUNTER_PROJECT_ID_ARG}"

    fun history(projectId: String): String = "history/${Uri.encode(projectId)}"

    fun counter(projectId: String): String = "$COUNTER_BASE/${Uri.encode(projectId)}"
}
