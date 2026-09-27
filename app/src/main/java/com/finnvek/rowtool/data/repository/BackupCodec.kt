package com.finnvek.rowtool.data.repository

import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import java.io.InputStream
import java.nio.charset.CharacterCodingException

internal object BackupFormat {
    const val REMINDERS_SCHEMA_VERSION = 4
    const val CURRENT_SCHEMA_VERSION = 5
    const val NOTES_SCHEMA_VERSION = 5
    const val APPLICATION_ID = "RowTool"
}

object BackupCodec {
    internal const val MAX_BACKUP_MIB: Int = 5
    private const val MAX_BACKUP_BYTES: Int = MAX_BACKUP_MIB * 1024 * 1024

    private val json =
        Json {
            ignoreUnknownKeys = true
            explicitNulls = true
            encodeDefaults = true
        }

    fun encode(backup: BackupFile): String = json.encodeToString(backup)

    fun decode(input: InputStream): BackupDecodeResult {
        val limitProbe = ByteArray(MAX_BACKUP_BYTES + 1)
        var totalRead = 0
        while (totalRead < limitProbe.size) {
            val read = input.read(limitProbe, totalRead, limitProbe.size - totalRead)
            if (read < 0) {
                return decode(limitProbe.copyOf(totalRead))
            }
            if (read > 0) {
                totalRead += read
            }
        }
        return decode(limitProbe.copyOf(totalRead))
    }

    fun decode(bytes: ByteArray): BackupDecodeResult =
        if (bytes.size > MAX_BACKUP_BYTES) {
            BackupDecodeResult.Invalid(BackupValidationError.TOO_LARGE)
        } else {
            decodeFile(bytes)
        }

    private fun decodeFile(bytes: ByteArray): BackupDecodeResult {
        val file = parseFile(bytes)
        val fileError = file?.let(::validateFile)
        val missingV3Start = file?.schemaVersion?.let { it >= 3 } == true && !hasV3StartFields(bytes)
        val projects = file?.takeIf { fileError == null }?.let { parseProjects(it.projects, it.schemaVersion) }
        return when {
            file == null -> {
                BackupDecodeResult.Invalid(BackupValidationError.MALFORMED_JSON)
            }

            fileError != null -> {
                BackupDecodeResult.Invalid(fileError)
            }

            missingV3Start -> {
                BackupDecodeResult.Invalid(BackupValidationError.INVALID_PROJECT)
            }

            projects == null -> {
                BackupDecodeResult.Invalid(
                    // Preserve collection-error precedence even when a unit cannot be parsed.
                    validateBackupProjectIds(file.projects.map { it.id }) ?: BackupValidationError.INVALID_PROJECT,
                )
            }

            else -> {
                ValidatedBackup.create(
                    file.exportedAt,
                    projects,
                    file.counters.orEmpty(),
                    file.history.orEmpty(),
                    file.reminders.orEmpty(),
                    file.notes.orEmpty(),
                )
            }
        }
    }

    private fun parseFile(bytes: ByteArray): BackupFile? =
        try {
            json.decodeFromString<BackupFile>(bytes.decodeToString(throwOnInvalidSequence = true))
        } catch (_: SerializationException) {
            null
        } catch (_: CharacterCodingException) {
            null
        } catch (_: IllegalArgumentException) {
            null
        }

    private fun hasV3StartFields(bytes: ByteArray): Boolean =
        try {
            val root = json.parseToJsonElement(bytes.decodeToString()).jsonObject
            (root["projects"] as? JsonArray)?.all { (it as? JsonObject)?.containsKey("repeatStartCount") == true } == true
        } catch (_: IllegalArgumentException) {
            false
        }

    private fun validateFile(file: BackupFile): BackupValidationError? =
        validateCoreFile(file) ?: validateReminderFields(file) ?: when {
            file.schemaVersion >= BackupFormat.NOTES_SCHEMA_VERSION && file.notes == null -> BackupValidationError.INVALID_NOTE
            file.schemaVersion < BackupFormat.NOTES_SCHEMA_VERSION && !file.notes.isNullOrEmpty() -> BackupValidationError.INVALID_NOTE
            else -> null
        }

    private fun validateReminderFields(file: BackupFile): BackupValidationError? =
        when {
            file.schemaVersion >= BackupFormat.REMINDERS_SCHEMA_VERSION && file.reminders == null -> {
                BackupValidationError.INVALID_REMINDER
            }

            file.schemaVersion < BackupFormat.REMINDERS_SCHEMA_VERSION && !file.reminders.isNullOrEmpty() -> {
                BackupValidationError.INVALID_REMINDER
            }

            else -> {
                null
            }
        }

    private fun validateCoreFile(file: BackupFile): BackupValidationError? =
        when {
            file.schemaVersion !in 1..BackupFormat.CURRENT_SCHEMA_VERSION -> {
                BackupValidationError.UNSUPPORTED_SCHEMA_VERSION
            }

            file.application != BackupFormat.APPLICATION_ID -> {
                BackupValidationError.INVALID_APPLICATION
            }

            file.schemaVersion >= 2 && (file.counters == null || file.history == null) -> {
                BackupValidationError.INVALID_HISTORY
            }

            file.schemaVersion <= 2 && file.projects.any { it.repeatStartCount != null } -> {
                BackupValidationError.INVALID_PROJECT
            }

            file.schemaVersion == 1 && (!file.counters.isNullOrEmpty() || !file.history.isNullOrEmpty()) -> {
                BackupValidationError.INVALID_HISTORY
            }

            else -> {
                null
            }
        }

    private fun parseProjects(
        projects: List<BackupProject>,
        schemaVersion: Int,
    ): List<CounterProject>? {
        val parsedProjects = ArrayList<CounterProject>(projects.size)
        for (project in projects) {
            val parsedProject = parseProject(project, schemaVersion) ?: return null
            parsedProjects += parsedProject
        }
        return parsedProjects
    }

    private fun parseProject(
        project: BackupProject,
        schemaVersion: Int,
    ): CounterProject? {
        val counterUnit = CounterUnit.entries.firstOrNull { it.name == project.counterUnit } ?: return null
        return CounterProject(
            id = project.id,
            name = project.name,
            counterUnit = counterUnit,
            count = project.count,
            startValue = project.startValue,
            targetCount = project.targetCount,
            repeatLength = project.repeatLength,
            isArchived = project.isArchived,
            createdAt = project.createdAt,
            updatedAt = project.updatedAt,
            repeatStartCount =
                if (schemaVersion <= 2) {
                    if (project.repeatLength != null) 1L else null
                } else {
                    project.repeatStartCount
                },
        )
    }
}
