package com.finnvek.rowtool.widget

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.test.ComposeTimeoutException
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.test.core.app.ActivityScenario

/** Only used with synthetic widget fixtures; never records binding tokens. */
internal fun ComposeTestRule.awaitRecreatedWidget(
    scenario: ActivityScenario<WidgetTestHostActivity>,
    previousHost: Int,
    id: Int,
    language: String,
    text: String,
    timeoutMillis: Long = 10_000,
) {
    var snapshot = "No host callback"
    try {
        waitUntil(timeoutMillis) {
            var ready = false
            scenario.onActivity { activity ->
                if (System.identityHashCode(activity) != previousHost &&
                    activity.resources.configuration.locales[0]
                        .language == language &&
                    !activity.views.containsKey(id)
                ) {
                    activity.attach(id)
                }
                ready = activity.recreatedWidgetReady(previousHost, id, language, text)
                val view = activity.views[id]
                snapshot = "host=${System.identityHashCode(activity)} previousHost=$previousHost widgetId=$id " +
                    "viewWidgetId=${view?.appWidgetId} destroyed=${activity.isDestroyed} " +
                    "attached=${view?.isAttachedToWindow} currentWindow=${view?.rootView === activity.window.decorView} " +
                    "locale=${activity.resources.configuration.locales} visibleText=${view?.visibleWidgetTexts()}"
            }
            ready
        }
    } catch (timeout: ComposeTimeoutException) {
        throw AssertionError("Widget not ready after ${timeoutMillis}ms: expected=$language/$text; $snapshot", timeout)
    }
}

internal fun WidgetTestHostActivity.recreatedWidgetReady(
    previousHost: Int,
    id: Int,
    language: String,
    text: String,
): Boolean {
    val view = views[id] ?: return false
    return System.identityHashCode(this) != previousHost && !isDestroyed &&
        resources.configuration.locales[0].language == language && view.appWidgetId == id &&
        view.isAttachedToWindow && view.rootView === window.decorView && view.isShown &&
        view.width > 0 && view.height > 0 && text in view.visibleWidgetTexts()
}

private fun View.visibleWidgetTexts(): List<String> =
    when {
        !isShown -> emptyList()
        this is TextView -> listOf(text.toString())
        this is ViewGroup -> (0 until childCount).flatMap { getChildAt(it).visibleWidgetTexts() }
        else -> emptyList()
    }
