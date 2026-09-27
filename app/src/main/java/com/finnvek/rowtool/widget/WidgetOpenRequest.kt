package com.finnvek.rowtool.widget

import android.net.Uri

data class WidgetOpenRequest(
    val widgetId: Int,
    val token: String,
    val reminders: Boolean,
) {
    companion object {
        fun parse(uri: Uri?): WidgetOpenRequest? {
            val parts = uri?.pathSegments.orEmpty()
            val id = parts.firstOrNull()?.toIntOrNull()
            val token = parts.getOrNull(1)
            val validRoute = uri?.scheme == "rowtool-widget" && uri.host in setOf("open", "reminders")
            val validIdentity = parts.size == 2 && id != null && id > 0
            return if (validRoute && validIdentity &&
                !token.isNullOrBlank()
            ) {
                WidgetOpenRequest(requireNotNull(id), token, uri?.host == "reminders")
            } else {
                null
            }
        }
    }
}
