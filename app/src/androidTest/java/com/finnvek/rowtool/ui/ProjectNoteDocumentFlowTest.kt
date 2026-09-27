package com.finnvek.rowtool.ui

import android.os.Bundle
import android.os.SystemClock
import android.view.accessibility.AccessibilityNodeInfo
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.MainActivity
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProjectNoteDocumentFlowTest {
    private val compose = createAndroidComposeRule<MainActivity>()
    private val instrumentation get() = InstrumentationRegistry.getInstrumentation()
    private val container get() = (instrumentation.targetContext.applicationContext as RowToolApplication).container
    private lateinit var id: String

    @get:Rule val rules: TestRule =
        RuleChain
            .outerRule(
                PrepareApplicationStateRule {
                    id = container.counterRepository.createProject("Note file", CounterUnit.ROWS, 0, null, null).id
                    container.counterRepository.mutate(id, CounterMutation.ManualSet(74))
                    container.counterRepository.notes.save(id, null, "  Resume here\n\n    Knit 🧶\nNext", true)
                    container.counterRepository.mutate(id, CounterMutation.Reset)
                    container.preferencesRepository.setLastActiveProjectId(id)
                },
            ).around(compose)

    @Test fun v5ExportAndRestoreThroughSystemDocumentsProviderPreservesNote() {
        val original =
            runBlocking {
                container.counterRepository.notes
                    .load(id)
                    .note!!
            }
        val filename = "rowtool-note-${System.currentTimeMillis()}.json"
        compose.waitUntil(5000) { compose.onAllNodesWithText("NOTE FILE").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithContentDescription("Settings").performClick()
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Export data"))
        compose.onNodeWithText("Export data").performClick()
        val nameField = documentNode { it.isEditable }
        assertTrue(
            nameField.performAction(
                AccessibilityNodeInfo.ACTION_SET_TEXT,
                Bundle().apply {
                    putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, filename)
                },
            ),
        )
        clickDocumentNode { it.text?.toString()?.equals("Save", ignoreCase = true) == true && it.isClickable }
        compose.waitUntil(15000) { compose.onAllNodesWithText("Data exported.").fetchSemanticsNodes().isNotEmpty() }
        runBlocking { container.counterRepository.notes.delete(id, original.version) }
        compose.onNode(hasScrollAction()).performScrollToNode(hasText("Import data"))
        compose.onNodeWithText("Import data").performClick()
        clickDocumentNode { it.text?.toString() == filename }
        compose.waitUntil(15000) { compose.onAllNodesWithText("Replace all projects?").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithText("Replace projects").performClick()
        compose.waitUntil(15000) {
            runBlocking {
                container.counterRepository.notes
                    .load(id)
                    .note != null
            }
        }
        val restored =
            runBlocking {
                container.counterRepository.notes
                    .load(id)
                    .note!!
            }
        assertEquals(original.text, restored.text)
        assertEquals(74L, restored.savedCount)
        assertEquals(original.savedAt, restored.savedAt)
        assertEquals(0L, runBlocking { container.counterRepository.getProject(id)!!.count })
    }

    private fun clickDocumentNode(predicate: (AccessibilityNodeInfo) -> Boolean) {
        var node = documentNode(predicate)
        while (!node.isClickable) node = requireNotNull(node.parent)
        assertTrue(node.performAction(AccessibilityNodeInfo.ACTION_CLICK))
    }

    private fun documentNode(predicate: (AccessibilityNodeInfo) -> Boolean): AccessibilityNodeInfo {
        val deadline = SystemClock.uptimeMillis() + 15000
        while (SystemClock.uptimeMillis() < deadline) {
            val root = instrumentation.uiAutomation.rootInActiveWindow
            if (root?.packageName?.toString()?.contains("documentsui") == true) {
                findNode(root, predicate)?.let { return it }
            }
            SystemClock.sleep(100)
        }
        error("System document picker control not found")
    }

    private fun findNode(
        node: AccessibilityNodeInfo,
        predicate: (AccessibilityNodeInfo) -> Boolean,
    ): AccessibilityNodeInfo? {
        if (predicate(node)) return node
        for (index in 0 until node.childCount) {
            val child = node.getChild(index) ?: continue
            findNode(child, predicate)?.let { return it }
        }
        return null
    }
}
