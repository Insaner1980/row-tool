package com.finnvek.rowtool.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.finnvek.rowtool.domain.model.CounterMutation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

internal fun widgetActionIntent(
    context: Context,
    id: Int,
    token: String,
    operation: String,
): Intent =
    Intent(context, WidgetActionReceiver::class.java)
        .setAction("com.finnvek.rowtool.WIDGET_ACTION")
        .setData(
            Uri
                .Builder()
                .scheme("rowtool-widget")
                .authority(operation)
                .appendPath(id.toString())
                .appendPath(token)
                .build(),
        )

class WidgetActionReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val uri = intent.data
        val id = uri?.pathSegments?.firstOrNull()?.toIntOrNull()
        val operation = uri?.host
        val token = uri?.pathSegments?.getOrNull(1)
        val validAction = intent.action == "com.finnvek.rowtool.WIDGET_ACTION" && uri?.scheme == "rowtool-widget"
        val validOperation = operation in setOf("plus", "minus", "refresh")
        if (!validAction || !validOperation) return
        if (id == null || !context.ownsWidget(id)) return
        val pending = goAsync()
        scope.launch {
            try {
                performWidgetAction(context, id, operation, token)
            } catch (failure: java.io.IOException) {
                CounterWidget().onCompositionError(
                    context,
                    androidx.glance.appwidget
                        .GlanceAppWidgetManager(context)
                        .getGlanceIdBy(id),
                    id,
                    failure,
                )
            } catch (failure: android.database.SQLException) {
                CounterWidget().onCompositionError(
                    context,
                    androidx.glance.appwidget
                        .GlanceAppWidgetManager(context)
                        .getGlanceIdBy(id),
                    id,
                    failure,
                )
            } finally {
                pending.finish()
            }
        }
    }

    private companion object {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}

internal suspend fun performWidgetAction(
    context: Context,
    id: Int,
    operation: String?,
    token: String?,
) {
    val container = context.widgetContainer()
    if (operation == "refresh") {
        container.widgetBindings.setFailed(id, false)
    } else {
        try {
            container.widgetBindings.withBinding(id, token.orEmpty()) {
                if (!context.ownsWidget(id)) return@withBinding
                container.counterRepository.mutate(
                    it,
                    if (operation ==
                        "plus"
                    ) {
                        CounterMutation.Increment
                    } else {
                        CounterMutation.Decrement
                    },
                )
            }
        } catch (_: java.io.IOException) {
            container.widgetBindings.setFailed(id, true)
        } catch (_: android.database.SQLException) {
            container.widgetBindings.setFailed(id, true)
        }
    }
// Refresh is separate from mutation: a render failure never repeats a count.
    requestWidgetUpdate(context)
}
