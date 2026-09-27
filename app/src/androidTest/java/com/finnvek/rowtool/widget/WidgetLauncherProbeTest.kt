package com.finnvek.rowtool.widget

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.data.repository.BackupDecodeResult
import com.finnvek.rowtool.data.repository.BackupImportResult
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/** Seeds synthetic projects and verifies Room alongside the recorded Pixel Launcher UI path. */
@RunWith(AndroidJUnit4::class)
class WidgetLauncherProbeTest {
    @Test fun verifyLauncherScenario(): Unit =
        runBlocking {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val container = (context.applicationContext as RowToolApplication).container
            val repository = container.counterRepository
            val arguments = InstrumentationRegistry.getArguments()
            // This probe accompanies a manual launcher session; it is not a standalone suite test.
            org.junit.Assume.assumeTrue(arguments.containsKey("operation"))
            if (arguments.getString("operation") == "seed") {
                if (repository.projects.first().none { it.name == "Launcher Beta" }) {
                    repository.createProject("Launcher Beta", CounterUnit.ROUNDS, 0, null, null)
                }
            }
            val alpha = repository.projects.first().single { it.name == "Selected widget project" }
            val beta = repository.projects.first().single { it.name == "Launcher Beta" }
            when (arguments.getString("operation")) {
                "archive" -> {
                    repository.setArchived(alpha.id, true)
                }

                "restore" -> {
                    repository.setArchived(alpha.id, false)
                }

                "replace" -> {
                    val backup = container.backupRepository.exportJson()
                    val validated = (container.backupRepository.prepareImport(backup.toByteArray()) as BackupDecodeResult.Valid).backup
                    assertTrue(container.backupRepository.replaceWith(validated) is BackupImportResult.Success)
                }
            }
            kotlinx.coroutines.delay(3_000) // Allow post-commit observer and platform update before instrumentation exits.
            arguments.getString("expectedAlpha")?.toLong()?.let { assertEquals(it, repository.getProject(alpha.id)!!.count) }
            arguments.getString("expectedBeta")?.toLong()?.let { assertEquals(it, repository.getProject(beta.id)!!.count) }
        }
}
