package com.finnvek.rowtool.data.repository

import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ReminderBackupTest {
    private val project = CounterProject("p", "Work", CounterUnit.ROWS, 0, 0, null, null, false, 1, 1)

    @Test fun v4RoundTripAndAcknowledgementAboveCurrentCount() {
        val file = backupFile(listOf(BackupReminder("r", "p", "Check", 32, 6, true, 50, 2)))
        val decoded = BackupCodec.decode(BackupCodec.encode(file).encodeToByteArray()) as BackupDecodeResult.Valid
        assertEquals(
            50L,
            decoded.backup.reminders
                .single()
                .acknowledgedThrough,
        )
    }

    @Test fun invalidReminderRejectsWholeImport() {
        val base = backupFile(emptyList())
        val invalid =
            listOf(
                BackupReminder("r", "missing", "Check", 32, 6, true, null, 1),
                BackupReminder("r", "p", " ", 32, 6, true, null, 1),
                BackupReminder("r", "p", "Check", 32, 6, true, 33, 1),
                BackupReminder("r", "p", "Check", 0, 6, true, null, 1),
            )
        invalid.forEach { reminder ->
            assertEquals(
                BackupDecodeResult.Invalid(BackupValidationError.INVALID_REMINDER),
                BackupCodec.decode(BackupCodec.encode(base.copy(reminders = listOf(reminder))).encodeToByteArray()),
            )
        }
        assertEquals(
            BackupDecodeResult.Invalid(BackupValidationError.INVALID_REMINDER),
            BackupCodec.decode(
                BackupCodec
                    .encode(
                        base.copy(reminders = listOf(invalid.first().copy(projectId = "p"), invalid.first().copy(projectId = "p"))),
                    ).encodeToByteArray(),
            ),
        )
    }

    @Test fun olderBackupsHaveNoReminders() {
        val result = ValidatedBackup.create(1, listOf(project)) as BackupDecodeResult.Valid
        assertTrue(result.backup.reminders.isEmpty())
    }

    private fun backupFile(reminders: List<BackupReminder>) =
        BackupFile(
            4,
            "RowTool",
            10,
            listOf(BackupProject("p", "Work", "ROWS", 0, 0, null, null, false, 1, 1)),
            emptyList(),
            emptyList(),
            reminders,
        )
}
