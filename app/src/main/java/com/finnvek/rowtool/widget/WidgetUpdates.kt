package com.finnvek.rowtool.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.room.InvalidationTracker
import com.finnvek.rowtool.RowToolApplication

internal fun Context.ownsWidget(id: Int): Boolean =
    id > 0 && AppWidgetManager.getInstance(this).getAppWidgetInfo(id)?.provider ==
        ComponentName(this, CounterWidgetReceiver::class.java)

internal fun Context.widgetContainer() = (applicationContext as RowToolApplication).container

internal fun requestWidgetUpdate(context: Context) {
    (context.applicationContext as RowToolApplication).widgetConfiguration.value =
        android.content.res.Configuration(
            androidx.core.content.ContextCompat
                .getContextForLanguage(context)
                .resources.configuration,
        )
    val provider = ComponentName(context, CounterWidgetReceiver::class.java)
    val ids = AppWidgetManager.getInstance(context).getAppWidgetIds(provider)
    if (ids.isEmpty()) return
    context.sendBroadcast(
        Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            .setComponent(provider)
            .putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids),
    )
}

internal class WidgetDatabaseObserver(
    private val context: Context,
) : InvalidationTracker.Observer("projects", "reminders") {
    override fun onInvalidated(tables: Set<String>) = requestWidgetUpdate(context)
}

internal fun refreshWidgetLanguage(context: Context) {
    val current =
        androidx.core.content.ContextCompat
            .getContextForLanguage(context)
            .resources.configuration
    if ((context.applicationContext as RowToolApplication).widgetConfiguration.value != current) requestWidgetUpdate(context)
}
