package com.finnvek.rowtool.ui

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.Lifecycle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.File

class AssertionFailureCaptureTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun capturesBeforeTeardownAndPreservesOriginalWhenStateProbeFails() {
        composeRule.setContent { Text("Capture helper screen") }
        composeRule.onNodeWithText("Capture helper screen").assertIsDisplayed()
        var original: Throwable? = null
        val thrown =
            runCatching {
                composeRule.captureAssertionFailure(
                    label = "helper-check",
                    activity = { composeRule.activity },
                    state = { error("Deliberately unavailable diagnostic state") },
                ) {
                    try {
                        composeRule.onNodeWithText("Deliberately absent heading").assertIsDisplayed()
                    } catch (failure: Throwable) {
                        original = failure
                        throw failure
                    }
                }
            }.exceptionOrNull()
        assertTrue(original is AssertionError)
        assertSame(original, thrown)
        val location = thrown!!.suppressed.first().message!!
        val directory = File(location.removePrefix("Failure capture: "))
        assertTrue(File(directory, "screen.png").length() > 0)
        assertTrue(File(directory, "failure.txt").readText().contains("Deliberately absent heading"))
        assertTrue(File(directory, "activity.txt").readText().contains("lifecycle=RESUMED"))
        assertTrue(File(directory, "state.txt.error.txt").readText().contains("Deliberately unavailable"))
        assertTrue(File(directory, "merged.txt").readText().contains("Capture helper screen"))
        assertTrue(File(directory, "unmerged.txt").readText().contains("Capture helper screen"))
        assertTrue(File(directory, "foreground.txt").length() > 0)
        assertTrue(File(directory, "logcat.txt").readText().contains("helper-check"))
        assertEquals(Lifecycle.State.RESUMED, composeRule.activityRule.scenario.state)
        composeRule.onNodeWithText("Capture helper screen").assertIsDisplayed()
    }

    @Test
    fun successfulAssertionDoesNotRunDiagnosticProbes() {
        composeRule.setContent { Text("Ready") }
        composeRule.captureAssertionFailure(
            label = "no-capture",
            activity = { error("Activity probe must not run") },
            state = { error("State probe must not run") },
        ) { composeRule.onNodeWithText("Ready").assertIsDisplayed() }
    }

    @Test
    fun missingComposeRootsDoNotPreventScreenAndLogCapture() {
        val original = AssertionError("Controlled failure without Compose content")
        val thrown =
            runCatching {
                composeRule.captureAssertionFailure("missing-roots", activity = { composeRule.activity }) { throw original }
            }.exceptionOrNull()
        assertSame(original, thrown)
        val location = original.suppressed.first().message!!
        val directory = File(location.removePrefix("Failure capture: "))
        assertTrue(File(directory, "screen.png").length() > 0)
        assertTrue(File(directory, "logcat.txt").readText().contains("missing-roots"))
        assertTrue(File(directory, "activity.txt").readText().contains("lifecycle=RESUMED"))
        assertTrue(File(directory, "merged.txt").exists() || File(directory, "merged.txt.error.txt").exists())
        assertTrue(File(directory, "unmerged.txt").exists() || File(directory, "unmerged.txt.error.txt").exists())
        assertEquals(Lifecycle.State.RESUMED, composeRule.activityRule.scenario.state)
    }
}
