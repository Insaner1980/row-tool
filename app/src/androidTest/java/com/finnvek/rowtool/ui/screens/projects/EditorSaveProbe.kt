package com.finnvek.rowtool.ui.screens.projects

import android.graphics.Rect
import android.os.SystemClock
import android.util.Log
import android.view.ViewTreeObserver
import androidx.compose.ui.platform.ViewRootForTest
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.click
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/** Observes only this dialog; never consumes input or replaces an inset listener. */
internal class EditorSaveProbe(
    private val rule: ComposeTestRule,
    private val saveLabel: String = "Save",
) : AutoCloseable {
    private val save = rule.onNodeWithText(saveLabel).fetchSemanticsNode()
    private val root = save.root as ViewRootForTest
    val view = root.view
    private var previous = ""
    private var stableFrames = 0
    private val listener =
        ViewTreeObserver.OnPreDrawListener {
            val state = state()
            stableFrames = if (state == previous) stableFrames + 1 else 0
            if (state != previous) record("layout $state")
            previous = state
            true
        }

    init {
        rule.runOnUiThread { view.viewTreeObserver.addOnPreDrawListener(listener) }
    }

    fun mark(label: String) = rule.runOnUiThread { record("$label ${state()}") }

    fun awaitKeyboard(visible: Boolean) {
        rule.waitUntil(5_000) {
            rule.runOnUiThread {
                view.postInvalidateOnAnimation()
                val insets = ViewCompat.getRootWindowInsets(view)
                insets?.isVisible(WindowInsetsCompat.Type.ime()) == visible &&
                    stableFrames >= 2 && state() == previous && !root.hasPendingMeasureOrLayout && !view.isLayoutRequested
            }
        }
        mark("keyboard settled visible=$visible")
    }

    fun reachable(): Boolean {
        val current = rule.onNodeWithText(saveLabel).fetchSemanticsNode()
        return rule.runOnUiThread {
            current.config.contains(SemanticsActions.OnClick) && !current.config.contains(SemanticsProperties.Disabled) &&
                geometryReachable(current)
        }
    }

    fun awaitReachable() {
        rule.waitUntil(5_000) { reachable() }
        mark("reachable")
    }

    fun clickSave() {
        // Reacquire after scrolling. This remains the Android performClick down/up path.
        val current = rule.onNodeWithText(saveLabel).assertIsEnabled()
        val node = current.fetchSemanticsNode()
        current.performTouchInput {
            rule.runOnUiThread {
                check(geometryReachable(node)) { "Save moved outside the visible dialog before activation: ${state()}" }
                record("activation localCenter=$center visibleSize=$visibleSize ${state()}")
            }
            click()
        }
        mark("injection returned")
    }

    private fun geometryReachable(node: SemanticsNode): Boolean {
        val visible = Rect()
        view.getWindowVisibleDisplayFrame(visible)
        val screen = node.positionOnScreen
        val bounds = node.boundsInRoot
        return node.root === root && view.hasWindowFocus() &&
            !root.hasPendingMeasureOrLayout && !view.isLayoutRequested &&
            bounds.width >= node.size.width && bounds.height >= node.size.height &&
            node.size.width > 0 && node.size.height > 0 &&
            screen.x >= visible.left && screen.y >= visible.top &&
            screen.x + node.size.width <= visible.right && screen.y + node.size.height <= visible.bottom
    }

    private fun state(): String {
        val visible = Rect()
        view.getWindowVisibleDisplayFrame(visible)
        val insets = ViewCompat.getRootWindowInsets(view)
        val scroll =
            generateSequence(save.parent) { it.parent }
                .firstOrNull { it.config.contains(SemanticsProperties.VerticalScrollAxisRange) }
        val offset =
            scroll
                ?.config
                ?.get(SemanticsProperties.VerticalScrollAxisRange)
                ?.value
                ?.invoke()
        return "node=${save.id} root=${view.javaClass.simpleName} focus=${view.hasWindowFocus()} " +
            "ime=${insets?.isVisible(WindowInsetsCompat.Type.ime())} inset=${insets?.getInsets(WindowInsetsCompat.Type.ime())} " +
            "viewportScreen=$visible saveScreen=${save.positionOnScreen} saveRoot=${save.boundsInRoot} " +
            "size=${save.size} scrollRoot=${scroll?.boundsInRoot} offset=$offset geometryReachable=${geometryReachable(save)}"
    }

    private fun record(message: String) {
        Log.i("RowToolEditorSave", "nanos=${SystemClock.elapsedRealtimeNanos()} $message")
    }

    override fun close() {
        rule.runOnUiThread { view.viewTreeObserver.removeOnPreDrawListener(listener) }
    }
}
