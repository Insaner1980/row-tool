package com.finnvek.rowtool.data.repository

import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.ProjectValidation
import com.finnvek.rowtool.domain.model.ProjectValidationResult
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream

object BackupCodec {
    private const val SUPPORTED_SCHEMA_VERSION = 1
    private const val APPLICATION_NAME = "RowTool"

    private val json =
        Json {
            ignoreUnknownKeys = true
            explicitNulls = true
        }

    fun encode(backup: BackupFile): String = json.encodeToString(backup)

    fun decode(input: InputStream): BackupDecodeResult {
        val limitProbe = ByteArray(CounterConstants.MAX_BACKUP_BYTES + 1)
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
        if (bytes.size > CounterConstants.MAX_BACKUP_BYTES) {
            BackupDecodeResult.Invalid(BackupValidationError.TOO_LARGE)
        } else {
            decodeFile(bytes)
        }

    private fun decodeFile(bytes: ByteArray): BackupDecodeResult {
        val file = parseFile(bytes)
        val fileError = file?.let(::validateFile)
        val projects = file?.takeIf { fileError == null }?.let { validateProjects(it.projects) }
        return when {
            file == null -> BackupDecodeResult.Invalid(BackupValidationError.MALFORMED_JSON)
            fileError != null -> BackupDecodeResult.Invalid(fileError)
            projects == null -> BackupDecodeResult.Invalid(BackupValidationError.INVALID_PROJECT)
            else -> BackupDecodeResult.Valid(ValidatedBackup(file.exportedAt, projects))
        }
    }

    private fun parseFile(bytes: ByteArray): BackupFile? =
        try {
            json.decodeFromString<BackupFile>(bytes.decodeToString())
        } catch (_: SerializationException) {
            null
        } catch (_: IllegalArgumentException) {
            null
        }

    private fun validateFile(file: BackupFile): BackupValidationError? =
        when {
            file.schemaVersion != SUPPORTED_SCHEMA_VERSION -> BackupValidationError.UNSUPPORTED_SCHEMA_VERSION

            file.application != APPLICATION_NAME -> BackupValidationError.INVALID_APPLICATION

            file.projects.size > CounterConstants.MAX_PROJECTS_IN_BACKUP -> BackupValidationError.TOO_MANY_PROJECTS

            file.projects
                .map { it.id }
                .toSet()
                .size != file.projects.size -> BackupValidationError.DUPLICATE_PROJECT_ID

            else -> null
        }

    private fun validateProjects(projects: List<BackupProject>): List<CounterProject>? {
        val validatedProjects = ArrayList<CounterProject>(projects.size)
        for (project in projects) {
            val validatedProject = validateProject(project) ?: return null
            validatedProjects += validatedProject
        }
        return validatedProjects
    }

    private fun validateProject(project: BackupProject): CounterProject? {
        val counterUnit = CounterUnit.entries.firstOrNull { it.name == project.counterUnit }
        if (counterUnit == null || project.id.isBlank()) {
            return null
        }
        val validation =
            ProjectValidation.validate(
                name = project.name,
                counterUnit = counterUnit,
                count = project.count,
                startValue = project.startValue,
                targetCount = project.targetCount,
                repeatLength = project.repeatLength,
            ) as? ProjectValidationResult.Valid
        return validation?.let {
            CounterProject(
                id = project.id,
                name = it.value.name,
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
}
