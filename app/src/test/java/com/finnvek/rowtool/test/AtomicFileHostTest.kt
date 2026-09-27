package com.finnvek.rowtool.test

import android.util.AtomicFile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class AtomicFileHostTest {
    @get:Rule val folder = TemporaryFolder()

    @Test fun committedWriteReplacesExistingFile() {
        val base = folder.root.resolve("state")
        val file = AtomicFile(base)
        val first = file.startWrite()
        first.write("first".toByteArray())
        file.finishWrite(first)
        assertEquals("first", base.readText())
        val next = file.startWrite()
        next.write("second".toByteArray())
        file.finishWrite(next)
        assertEquals("second", AtomicFile(base).openRead().bufferedReader().use { it.readText() })
        assertFalse(folder.root.resolve("state.new").exists())
    }

    @Test fun failedWritePreservesPreviouslyCommittedFile() {
        val base = folder.newFile("state").apply { writeText("committed") }
        val file = AtomicFile(base)
        val output = file.startWrite()
        output.write("unfinished".toByteArray())
        file.failWrite(output)
        assertEquals("committed", AtomicFile(base).openRead().bufferedReader().use { it.readText() })
        assertFalse(folder.root.resolve("state.new").exists())
    }

    @Test fun hostRenameFailureIsNotReportedAsSuccessfulCommit() {
        val base = folder.root.resolve("state")
        val file = AtomicFile(base)
        val output = file.startWrite()
        output.write("new".toByteArray())
        // A nonempty directory cannot be replaced by the pending file.
        base.mkdir()
        val obstruction = base.resolve("keep").apply { writeText("unchanged") }
        try {
            assertThrows(IOException::class.java) { file.finishWrite(output) }
            assertEquals("unchanged", obstruction.readText())
        } finally {
            file.failWrite(output)
        }
    }
}
