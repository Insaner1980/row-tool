package com.finnvek.rowtool.data.repository

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ProjectNoteBackupTest {
    private val base =
        BackupFile(
            5,
            "RowTool",
            100,
            listOf(
                BackupProject("p", "Work", "ROWS", 0, 0, null, null, false, 1, 2),
            ),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
        )
    private val note = BackupNote("p", "  Next\n\n    🧶", 90, 74)

    @Test fun roundTripPreservesWhitespaceTimeAndCountAboveCurrent() {
        val valid = decode(base.copy(notes = listOf(note))) as BackupDecodeResult.Valid
        assertEquals(listOf(note), valid.backup.notes)
        val normalized = decode(base.copy(notes = listOf(note.copy(text = " A\r\n B\rC")))) as BackupDecodeResult.Valid
        assertEquals(
            " A\n B\nC",
            normalized.backup.notes
                .single()
                .text,
        )
    }

    @Test fun rejectsInvalidNoteCollectionsAndFields() {
        val invalid =
            listOf(
                listOf(note, note),
                listOf(note.copy(projectId = "missing")),
                listOf(note.copy(text = " \n\t")),
                listOf(note.copy(text = "🧶".repeat(5001))),
                listOf(note.copy(savedCount = -1)),
                listOf(note.copy(savedCount = 1000000)),
                listOf(note.copy(savedAt = -1)),
            )
        invalid.forEach { assertEquals(BackupDecodeResult.Invalid(BackupValidationError.INVALID_NOTE), decode(base.copy(notes = it))) }
        assertTrue(decode(base.copy(notes = null)) is BackupDecodeResult.Invalid)
        assertTrue(
            BackupCodec.decode(
                BackupCodec
                    .encode(
                        base.copy(notes = listOf(note)),
                    ).replace("\"text\":\"  Next\\n\\n    🧶\"", "\"text\":42")
                    .encodeToByteArray(),
            ) is BackupDecodeResult.Invalid,
        )
        assertTrue(decode(base.copy(notes = listOf(note.copy(text = "🧶".repeat(5000), savedCount = 0)))) is BackupDecodeResult.Valid)
    }

    @Test fun oldVersionsHaveNoNotesAndCannotSmuggleThem() {
        for (version in 1..4) {
            val old =
                base.copy(
                    schemaVersion = version,
                    notes = null,
                    counters = if (version == 1) null else emptyList(),
                    history = if (version == 1) null else emptyList(),
                    reminders = if (version < 4) null else emptyList(),
                )
            assertTrue((decode(old) as BackupDecodeResult.Valid).backup.notes.isEmpty())
            assertTrue(decode(old.copy(notes = listOf(note))) is BackupDecodeResult.Invalid)
        }
    }

    private fun decode(file: BackupFile) = BackupCodec.decode(BackupCodec.encode(file).encodeToByteArray())
}
