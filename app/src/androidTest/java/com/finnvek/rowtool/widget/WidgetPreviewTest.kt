package com.finnvek.rowtool.widget

import android.appwidget.AppWidgetHostView
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 31)
class WidgetPreviewTest {
    @Test
    fun pickerPreviewInflatesInPlatformHostWithSampleContent() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val context = instrumentation.targetContext
        val component = ComponentName(context, CounterWidgetReceiver::class.java)
        val provider = AppWidgetManager.getInstance(context).installedProviders.single { it.provider == component }
        assertEquals(R.layout.widget_preview, provider.previewLayout)
        // Launcher uses the preview layout as the default view of an unbound host.
        val preview = provider.clone().apply { initialLayout = previewLayout }
        instrumentation.runOnMainSync {
            val host = AppWidgetHostView(context)
            host.setAppWidget(-1, preview)
            host.updateAppWidget(null)
            val text = texts(host)
            val expected =
                listOf(
                    R.string.widget_preview_project,
                    R.string.widget_preview_count,
                    R.string.project_rows,
                    R.string.widget_preview_controls,
                )
            for (resource in expected) {
                assertTrue("Missing preview content ${context.getString(resource)}: $text", context.getString(resource) in text)
            }
        }
    }

    private fun texts(view: View): List<String> =
        when (view) {
            is TextView -> listOf(view.text.toString())
            is ViewGroup -> (0 until view.childCount).flatMap { texts(view.getChildAt(it)) }
            else -> emptyList()
        }
}
