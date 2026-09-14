package com.finnvek.rowtool.data.repository

import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.nio.charset.CharacterCodingException

internal object BackupFormat {
    const val CURRENT_SCHEMA_VERSION = 1
    const val APPLICATION_ID = "RowTool"
}

object BackupCodec {
    internal const val MAX_BACKUP_MIB: Int = 5
    private const val MAX_BACKUP_BYTES: Int = MAX_BACKUP_MIB * 1024 * 1024

    private val json =
        Json {
            ignoreUnknownKeys = true
            explicitNulls = true
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
        val projects = file?.takeIf { fileError == null }?.let { parseProjects(it.projects) }
        return when {
            file == null -> {
                BackupDecodeResult.Invalid(BackupValidationError.MALFORMED_JSON)
            }

            fileError != null -> {
                BackupDecodeResult.Invalid(fileError)
            }

            projects == null -> {
                BackupDecodeResult.Invalid(
                    // Preserve collection-error precedence even when a unit cannot be parsed.
                    validateBackupProjectIds(file.projects.map { it.id }) ?: BackupValidationError.INVALID_PROJECT,
                )
            }

            else -> {
                ValidatedBackup.create(file.exportedAt, projects)
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

    private fun validateFile(file: BackupFile): BackupValidationError? =
        when {
            file.schemaVersion != BackupFormat.CURRENT_SCHEMA_VERSION -> BackupValidationError.UNSUPPORTED_SCHEMA_VERSION
            file.application != BackupFormat.APPLICATION_ID -> BackupValidationError.INVALID_APPLICATION
            else -> null
        }

    private fun parseProjects(projects: List<BackupProject>): List<CounterProject>? {
        val parsedProjects = ArrayList<CounterProject>(projects.size)
        for (project in projects) {
            val parsedProject = parseProject(project) ?: return null
            parsedProjects += parsedProject
        }
        return parsedProjects
    }

    private fun parseProject(project: BackupProject): CounterProject? {
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
        )
    }
}
