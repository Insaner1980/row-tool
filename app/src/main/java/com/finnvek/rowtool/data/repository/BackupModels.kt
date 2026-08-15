package com.finnvek.rowtool.data.repository

import com.finnvek.rowtool.domain.model.CounterProject
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

data class ValidatedBackup(
    val exportedAt: Long,
    val projects: List<CounterProject>,
)

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
