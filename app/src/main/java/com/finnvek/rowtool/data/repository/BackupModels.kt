package com.finnvek.rowtool.data.repository

import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.ProjectValidation
import com.finnvek.rowtool.domain.model.ProjectValidationResult
import kotlinx.serialization.Serializable

@Serializable
data class BackupFile(
    val schemaVersion: Int,
    val application: String,
    val exportedAt: Long,
    val projects: List<BackupProject>,
)

// CPD-OFF: The serialized backup contract intentionally mirrors persisted project fields.
@Serializable
data class BackupProject(
    val id: String,
    val name: String,
    val counterUnit: String,
    val count: Long,
    val startValue: Int,
    val targetCount: Long?,
    val repeatLength: Int?,
    val isArchived: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
)
// CPD-ON

@ConsistentCopyVisibility
data class ValidatedBackup private constructor(
    val exportedAt: Long,
    val projects: List<CounterProject>,
) {
    companion object {
        fun create(
            exportedAt: Long,
            projects: List<CounterProject>,
        ): BackupDecodeResult {
            val identifierError = validateBackupProjectIds(projects.map { it.id })
            return if (identifierError != null) {
                BackupDecodeResult.Invalid(identifierError)
            } else {
                val validatedProjects =
                    projects.map { project ->
                        validateProject(project)
                            ?: return BackupDecodeResult.Invalid(BackupValidationError.INVALID_PROJECT)
                    }
                BackupDecodeResult.Valid(ValidatedBackup(exportedAt, validatedProjects))
            }
        }

        private fun validateProject(project: CounterProject): CounterProject? {
            if (project.id.isBlank()) return null
            val validation =
                ProjectValidation.validate(
                    name = project.name,
                    counterUnit = project.counterUnit,
                    count = project.count,
                    startValue = project.startValue,
                    targetCount = project.targetCount,
                    repeatLength = project.repeatLength,
                ) as? ProjectValidationResult.Valid
            return validation?.value?.let {
                project.copy(
                    name = it.name,
                    counterUnit = it.counterUnit,
                    count = it.count,
                    startValue = it.startValue,
                    targetCount = it.targetCount,
                    repeatLength = it.repeatLength,
                )
            }
        }
    }
}

internal fun validateBackupProjectIds(ids: List<String>): BackupValidationError? =
    when {
        ids.size > CounterConstants.MAX_PROJECTS_IN_BACKUP -> BackupValidationError.TOO_MANY_PROJECTS
        ids.toSet().size != ids.size -> BackupValidationError.DUPLICATE_PROJECT_ID
        else -> null
    }

enum class BackupValidationError {
    TOO_LARGE,
    MALFORMED_JSON,
    UNSUPPORTED_SCHEMA_VERSION,
    INVALID_APPLICATION,
    TOO_MANY_PROJECTS,
    DUPLICATE_PROJECT_ID,
    INVALID_PROJECT,
}

sealed interface BackupDecodeResult {
    data class Valid(
        val backup: ValidatedBackup,
    ) : BackupDecodeResult

    data class Invalid(
        val error: BackupValidationError,
    ) : BackupDecodeResult
}

sealed interface BackupImportResult {
    data class Success(
        val projectCount: Int,
        val lastActiveProjectId: String?,
    ) : BackupImportResult

    data class Failure(
        val cause: BackupImportFailure,
    ) : BackupImportResult
}

enum class BackupImportFailure {
    DATABASE_WRITE_FAILED,
}
