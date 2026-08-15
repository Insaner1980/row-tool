package com.finnvek.rowtool.ui.navigation

import android.net.Uri

object Screen {
    const val PROJECTS = "projects"
    const val SETTINGS = "settings"
    const val COUNTER_PATTERN = "counter/{projectId}"

    fun counter(projectId: String): String = "counter/${Uri.encode(projectId)}"
}
