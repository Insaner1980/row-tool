package com.finnvek.rowtool.data.repository

import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.ProjectNoteRules
import com.finnvek.rowtool.domain.model.ProjectValidation
import com.finnvek.rowtool.domain.model.ProjectValidationResult
import com.finnvek.rowtool.domain.model.ReminderRules
import kotlinx.serialization.Serializable

@Serializable
data class BackupFile(
    val schemaVersion: Int,
    val application: String,
    val exportedAt: Long,
    val projects: List<BackupProject>,
    val counters: List<BackupCounter>? = null,
    val history: List<BackupHistory>? = null,
    val reminders: List<BackupReminder>? = null,
    val notes: List<BackupNote>? = null,
)

@Serializable
data class BackupNote(
    val projectId: String,
    val text: String,
    val savedAt: Long,
    val savedCount: Long?,
)

@Serializable
data class BackupReminder(
    val id: String,
    val projectId: String,
    val message: String,
    val firstCount: Long,
    val intervalCount: Long?,
    val enabled: Boolean,
    val acknowledgedThrough: Long?,
    val revision: Long,
)

@Serializable
data class BackupCounter(
    val id: String,
    val projectId: String,
    val name: String,
    val count: Long,
    val followsMain: Boolean,
    val isDeleted: Boolean,
)

@Serializable
data class BackupHistory(
    val id: Long,
    val projectId: String,
    val previousCount: Long,
    val newCount: Long,
    val changeReason: String,
    val createdAt: Long,
    val effects: List<BackupCounterEffect>,
)

@Serializable
data class BackupCounterEffect(
    val counterId: String,
    val previousCount: Long,
    val newCount: Long,
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
    val repeatStartCount: Long? = null,
)
// CPD-ON

@ConsistentCopyVisibility
data class ValidatedBackup private constructor(
    val exportedAt: Long,
    val projects: List<CounterProject>,
    val counters: List<BackupCounter>,
    val history: List<BackupHistory>,
    val reminders: List<BackupReminder>,
    val notes: List<BackupNote>,
) {
    companion object {
        fun create(
            exportedAt: Long,
            projects: List<CounterProject>,
            counters: List<BackupCounter> = emptyList(),
            history: List<BackupHistory> = emptyList(),
            reminders: List<BackupReminder> = emptyList(),
            notes: List<BackupNote> = emptyList(),
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
                val counterError = validateBackupCounters(validatedProjects, counters)
                val historyError = validateBackupHistory(validatedProjects, counters, history)
                val reminderError = validateBackupReminders(validatedProjects, reminders)
                val error = counterError ?: historyError ?: reminderError ?: validateBackupNotes(validatedProjects, notes)
                if (error != null) {
                    BackupDecodeResult.Invalid(error)
                } else {
                    BackupDecodeResult.Valid(
                        ValidatedBackup(
                            exportedAt,
                            validatedProjects,
                            counters.map { it.copy(name = ProjectValidation.normalizeName(it.name)) },
                            history.map { it.copy(effects = it.effects.toList()) },
                            reminders.map { it.copy(message = ReminderRules.normalizeMessage(it.message)) },
                            notes.map { it.copy(text = ProjectNoteRules.normalize(it.text)) },
                        ),
                    )
                }
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
                    repeatStartCount = project.repeatStartCount,
                ) as? ProjectValidationResult.Valid
            return validation?.value?.let {
                project.copy(
                    name = it.name,
                    counterUnit = it.counterUnit,
                    count = it.count,
                    startValue = it.startValue,
                    targetCount = it.targetCount,
                    repeatLength = it.repeatLength,
                    repeatStartCount = it.repeatStartCount,
                )
            }
        }
    }
}

private fun validateBackupReminders(
    projects: List<CounterProject>,
    reminders: List<BackupReminder>,
): BackupValidationError? {
    val projectIds = projects.map { it.id }.toSet()
    val duplicateIds = reminders.map { it.id }.toSet().size != reminders.size
    val invalidReminder =
        reminders.any {
            val invalidIdentity = it.id.isBlank() || it.projectId !in projectIds || it.revision < 1
            val invalidContent = !ReminderRules.validate(it.message, it.firstCount, it.intervalCount)
            val invalidAcknowledgement = !ReminderRules.validAcknowledgement(it.firstCount, it.intervalCount, it.acknowledgedThrough)
            invalidIdentity || invalidContent || invalidAcknowledgement
        }
    return BackupValidationError.INVALID_REMINDER.takeIf { duplicateIds || invalidReminder }
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
    INVALID_COUNTER,
    INVALID_HISTORY,
    INVALID_REMINDER,
    INVALID_NOTE,
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
