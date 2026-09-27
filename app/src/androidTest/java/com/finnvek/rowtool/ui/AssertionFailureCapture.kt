package com.finnvek.rowtool.ui

import android.app.Activity
import android.graphics.Bitmap
import android.os.ParcelFileDescriptor
import android.os.SystemClock
import android.util.Log
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.printToString
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.MainActivity
import java.io.File

/** Runs no probes on success. Every probe is a later observation, not an atomic failure snapshot. */
internal fun ComposeTestRule.captureAssertionFailure(
    label: String,
    activity: () -> Activity,
    state: () -> String = { "No additional state supplied" },
    assertion: () -> Unit,
) {
    try {
        assertion()
    } catch (original: Throwable) {
        val failedAt = captureTimestamp()
        try {
            val instrumentation = InstrumentationRegistry.getInstrumentation()
            val directory =
                File(
                    instrumentation.targetContext.getExternalFilesDir(null),
                    "assertion-failures/$label-${SystemClock.elapsedRealtimeNanos()}",
                )
            check(directory.mkdirs()) { "Cannot create $directory" }
            original.addSuppressed(AssertionError("Failure capture: ${directory.absolutePath}"))
            File(directory, "failure.txt").writeText("$failedAt\n${original.stackTraceToString()}")
            Log.e("RowToolAssertion", "$label failed at $failedAt; capture=$directory", original)

            fun probe(
                name: String,
                action: (File) -> Unit,
            ) {
                try {
                    File(directory, "$name.start.txt").writeText(captureTimestamp())
                    action(File(directory, name))
                    File(directory, "$name.end.txt").writeText(captureTimestamp())
                } catch (secondary: Throwable) {
                    original.addSuppressed(IllegalStateException("Capture probe $name failed", secondary))
                    runCatching {
                        File(directory, "$name.error.txt").writeText("${captureTimestamp()}\n${secondary.stackTraceToString()}")
                    }
                }
            }

            // Whole-screen capture comes before any Compose inspection or target-readiness wait.
            probe("screen.png") { file ->
                val bitmap = checkNotNull(instrumentation.uiAutomation.takeScreenshot())
                try {
                    file.outputStream().use { check(bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)) }
                } finally {
                    bitmap.recycle()
                }
            }
            probe("activity.txt") { file ->
                instrumentation.runOnMainSync {
                    val current = activity()
                    val config = current.resources.configuration
                    file.writeText(
                        "component=${current.componentName.flattenToShortString()}\n" +
                            "lifecycle=${androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry.getInstance().getLifecycleStageOf(
                                current,
                            )}\n" +
                            "focus=${current.hasWindowFocus()} finishing=${current.isFinishing}\n" +
                            "locales=${config.locales.toLanguageTags()} fontScale=${config.fontScale}\n" +
                            "window=${current.window.decorView.width}x${current.window.decorView.height}\n" +
                            "screenDp=${config.screenWidthDp}x${config.screenHeightDp}",
                    )
                }
            }
            probe("startup.txt") { file ->
                instrumentation.runOnMainSync {
                    val current = activity()
                    file.writeText(if (current is MainActivity) "startup=${current.existingStartupState()}" else "Not a MainActivity")
                }
            }
            probe("state.txt") { it.writeText(state()) }
            probe("foreground.txt") { it.writeText(captureShell("dumpsys activity activities")) }
            probe("logcat.txt") { it.writeText(captureShell("logcat -d -v threadtime -t 2000")) }
            // These Compose APIs synchronize; timestamps explicitly distinguish their later state.
            probe("merged.txt") { it.writeText(onAllNodes(isRoot()).printToString(maxDepth = Int.MAX_VALUE)) }
            probe("unmerged.txt") { it.writeText(onAllNodes(isRoot(), useUnmergedTree = true).printToString(maxDepth = Int.MAX_VALUE)) }
        } catch (secondary: Throwable) {
            original.addSuppressed(IllegalStateException("Failure capture could not complete", secondary))
        }
        throw original
    }
}

private fun captureTimestamp(): String = "wallMillis=${System.currentTimeMillis()} elapsedNanos=${SystemClock.elapsedRealtimeNanos()}"

private fun captureShell(command: String): String =
    ParcelFileDescriptor
        .AutoCloseInputStream(InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand(command))
        .bufferedReader()
        .use { it.readText() }

internal fun MainActivity.existingStartupState(): StartupState? {
    // Read the existing lazy only: ViewModelProvider.get could create the object being diagnosed.
    val field = MainActivity::class.java.getDeclaredField("appViewModel\$delegate").apply { isAccessible = true }
    val lazy = field.get(this) as Lazy<*>
    if (!lazy.isInitialized()) return null
    val model = lazy.value as RowToolAppViewModel
    return model.startupState.value
}
