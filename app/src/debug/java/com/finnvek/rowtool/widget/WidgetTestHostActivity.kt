package com.finnvek.rowtool.widget

import android.app.Activity
import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetHostView
import android.appwidget.AppWidgetManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView

/** Instrumentation-only real RemoteViews host; absent from release builds. */
class WidgetTestHostActivity : Activity() {
    lateinit var host: AppWidgetHost
        private set
    val views = mutableMapOf<Int, AppWidgetHostView>()
    private lateinit var column: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        host = AppWidgetHost(this, 5327)
        column = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        setContentView(ScrollView(this).apply { addView(column) })
        host.startListening()
    }

    fun attach(id: Int) {
        val manager = AppWidgetManager.getInstance(this)
        val view = host.createView(this, id, manager.getAppWidgetInfo(id))
        view.updateAppWidgetSize(Bundle(), 280, 280, 280, 280)
        val density = resources.displayMetrics.density
        column.addView(view, LinearLayout.LayoutParams((280 * density).toInt(), (280 * density).toInt()))
        views[id] = view
    }

    override fun onDestroy() {
        host.stopListening()
        super.onDestroy()
    }
}
