package com.finnvek.rowtool.domain.model

object CounterConstants {
    const val MAX_COUNT: Long = 999_999L
    const val MAX_HISTORY_ENTRIES: Int = 100
    const val MAX_PROJECTS_IN_BACKUP: Int = 1_000
    const val MAX_BACKUP_BYTES: Int = 5 * 1024 * 1024
}

enum class CounterUnit {
    ROWS,
    ROUNDS,
    ;

    companion object {
        fun fromPersisted(value: String): CounterUnit =
            entries
                .firstOrNull { it.name == value }
                ?: ROWS
    }
}

data class CounterProject(
    val id: String,
    val name: String,
    val counterUnit: CounterUnit,
    val count: Long,
    val startValue: Int,
    val targetCount: Long?,
    val repeatLength: Int?,
    val isArchived: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
)

enum class HistoryChangeReason {
    INCREMENT,
    DECREMENT,
    MANUAL_SET,
    RESET,
}

sealed interface CounterMutation {
    data object Increment : CounterMutation

    data object Decrement : CounterMutation

    data object Reset : CounterMutation

    data class ManualSet(
        val count: Long,
    ) : CounterMutation
}

sealed interface CounterMutationResult {
    data class Changed(
        val previousCount: Long,
        val newCount: Long,
        val reason: HistoryChangeReason,
    ) : CounterMutationResult

    data class NoOp(
        val currentCount: Long,
    ) : CounterMutationResult

    data object ProjectMissing : CounterMutationResult

    data object ProjectArchived : CounterMutationResult

    data class Invalid(
        val errors: Set<ProjectValidationError>,
    ) : CounterMutationResult
}

data class RepeatProgress(
    val currentStep: Int,
    val repeatLength: Int,
    val completedRepeats: Long,
)

data class TargetProgress(
    val count: Long,
    val targetCount: Long,
    val fraction: Float,
    val isReached: Boolean,
)
