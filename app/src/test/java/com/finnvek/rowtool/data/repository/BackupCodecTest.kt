package com.finnvek.rowtool.data.repository

import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterUnit
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.InputStream

class BackupCodecTest {
    @Test
    fun validBackupRoundTrips() {
        val backup =
            BackupFile(
                schemaVersion = 1,
                application = "RowTool",
                exportedAt = 1_234_567_890L,
                projects =
                    listOf(
                        BackupProject(
                            id = "project-1",
                            name = "Cowl",
                            counterUnit = "ROUNDS",
                            count = 17,
                            startValue = 1,
                            targetCount = 80,
                            repeatLength = 6,
                            isArchived = true,
                            createdAt = 100,
                            updatedAt = 200,
                        ),
                    ),
            )

        val decoded = BackupCodec.decode(BackupCodec.encode(backup).encodeToByteArray())

        assertTrue(decoded is BackupDecodeResult.Valid)
        assertEquals(
            ValidatedBackup(
                exportedAt = 1_234_567_890L,
                projects =
                    listOf(
                        com.finnvek.rowtool.domain.model.CounterProject(
                            id = "project-1",
                            name = "Cowl",
                            counterUnit = CounterUnit.ROUNDS,
                            count = 17,
                            startValue = 1,
                            targetCount = 80,
                            repeatLength = 6,
                            isArchived = true,
                            createdAt = 100,
                            updatedAt = 200,
                        ),
                    ),
            ),
            (decoded as BackupDecodeResult.Valid).backup,
        )
    }

    @Test
    fun unknownOptionalKeysAreIgnored() {
        val decoded =
            BackupCodec.decode(
                backupJson(
                    projectJson = validProjectJson(id = "p1", extra = ",\"futureProjectKey\":true"),
                    topLevelExtra = ",\"futureTopLevelKey\":{\"value\":1}",
                ).encodeToByteArray(),
            )

        assertTrue(decoded is BackupDecodeResult.Valid)
    }

    @Test
    fun missingRequiredFieldIsRejected() {
        val projectWithoutName =
            validProjectJson(id = "p1")
                .replace("\"name\":\"Project\",", "")

        assertInvalid(
            BackupValidationError.MALFORMED_JSON,
            BackupCodec.decode(backupJson(projectWithoutName).encodeToByteArray()),
        )
    }

    @Test
    fun unsupportedSchemaVersionIsRejected() {
        val json = backupJson(validProjectJson(id = "p1"), schemaVersion = 2)

        assertInvalid(
            BackupValidationError.UNSUPPORTED_SCHEMA_VERSION,
            BackupCodec.decode(json.encodeToByteArray()),
        )
    }

    @Test
    fun wrongApplicationIdentityIsRejected() {
        val json = backupJson(validProjectJson(id = "p1")).replace("\"RowTool\"", "\"Other\"")

        assertInvalid(
            BackupValidationError.INVALID_APPLICATION,
            BackupCodec.decode(json.encodeToByteArray()),
        )
    }

    @Test
    fun invalidCountIsRejected() {
        val json = backupJson(validProjectJson(id = "p1").replace("\"count\":0", "\"count\":1000000"))

        assertInvalid(BackupValidationError.INVALID_PROJECT, BackupCodec.decode(json.encodeToByteArray()))
    }

    @Test
    fun invalidTargetIsRejected() {
        val json = backupJson(validProjectJson(id = "p1").replace("\"targetCount\":null", "\"targetCount\":0"))

        assertInvalid(BackupValidationError.INVALID_PROJECT, BackupCodec.decode(json.encodeToByteArray()))
    }

    @Test
    fun invalidRepeatIsRejected() {
        val json = backupJson(validProjectJson(id = "p1").replace("\"repeatLength\":null", "\"repeatLength\":1"))

        assertInvalid(BackupValidationError.INVALID_PROJECT, BackupCodec.decode(json.encodeToByteArray()))
    }

    @Test
    fun unknownCounterUnitIsRejectedRatherThanSilentlyChanged() {
        val json = backupJson(validProjectJson(id = "p1").replace("\"ROWS\"", "\"STITCHES\""))

        assertInvalid(BackupValidationError.INVALID_PROJECT, BackupCodec.decode(json.encodeToByteArray()))
    }

    @Test
    fun duplicateProjectIdsAreRejected() {
        val json =
            backupJson(
                projectJson = validProjectJson(id = "same") + "," + validProjectJson(id = "same"),
            )

        assertInvalid(
            BackupValidationError.DUPLICATE_PROJECT_ID,
            BackupCodec.decode(json.encodeToByteArray()),
        )
    }

    @Test
    fun moreThanOneThousandProjectsAreRejected() {
        val projects = (1..1_001).joinToString(",") { validProjectJson(id = "p$it") }

        assertInvalid(
            BackupValidationError.TOO_MANY_PROJECTS,
            BackupCodec.decode(backupJson(projects).encodeToByteArray()),
        )
    }

    @Test
    fun malformedJsonIsRejected() {
        assertInvalid(
            BackupValidationError.MALFORMED_JSON,
            BackupCodec.decode("{ definitely not json".encodeToByteArray()),
        )
    }

    @Test
    fun oversizedInputIsRejectedBeforeParsing() {
        val bytes = ByteArray(CounterConstants.MAX_BACKUP_BYTES + 1) { 'x'.code.toByte() }

        assertInvalid(BackupValidationError.TOO_LARGE, BackupCodec.decode(bytes))
    }

    @Test
    fun oversizedStreamStopsAfterTheSizeLimitIsProven() {
        val stream = CountingEndlessInputStream()

        assertInvalid(BackupValidationError.TOO_LARGE, BackupCodec.decode(stream))
        assertEquals(CounterConstants.MAX_BACKUP_BYTES + 1, stream.bytesRead)
    }

    @Test
    fun importedNamesAreTrimmed() {
        val json = backupJson(validProjectJson(id = "p1").replace("\"Project\"", "\"  Project  \""))

        val decoded = BackupCodec.decode(json.encodeToByteArray())

        assertTrue(decoded is BackupDecodeResult.Valid)
        assertEquals(
            "Project",
            (decoded as BackupDecodeResult.Valid)
                .backup.projects
                .single()
                .name,
        )
    }

    private fun assertInvalid(
        expected: BackupValidationError,
        actual: BackupDecodeResult,
    ) {
        assertTrue(actual is BackupDecodeResult.Invalid)
        assertEquals(expected, (actual as BackupDecodeResult.Invalid).error)
    }

    private fun backupJson(
        projectJson: String,
        schemaVersion: Int = 1,
        topLevelExtra: String = "",
    ): String =
        """
        {
          "schemaVersion":$schemaVersion,
          "application":"RowTool",
          "exportedAt":1234567890,
          "projects":[$projectJson]
          $topLevelExtra
        }
        """.trimIndent()

    private fun validProjectJson(
        id: String,
        extra: String = "",
    ): String =
        """
        {
          "id":"$id",
          "name":"Project",
          "counterUnit":"ROWS",
          "count":0,
          "startValue":0,
          "targetCount":null,
          "repeatLength":null,
          "isArchived":false,
          "createdAt":100,
          "updatedAt":200
          $extra
        }
        """.trimIndent()

    private class CountingEndlessInputStream : InputStream() {
        var bytesRead: Int = 0
            private set

        override fun read(): Int {
            bytesRead += 1
            return 'x'.code
        }
    }
}
