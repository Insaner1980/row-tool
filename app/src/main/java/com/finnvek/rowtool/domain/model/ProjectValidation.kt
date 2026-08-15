package com.finnvek.rowtool.domain.model

enum class ProjectValidationError {
    NAME_BLANK,
    NAME_TOO_LONG,
    INVALID_COUNT,
    INVALID_START_VALUE,
    INVALID_TARGET,
    INVALID_REPEAT_LENGTH,
}

data class ValidatedProjectValues(
    val name: String,
    val counterUnit: CounterUnit,
    val count: Long,
    val startValue: Int,
    val targetCount: Long?,
    val repeatLength: Int?,
)

sealed interface ProjectValidationResult {
    data class Valid(
        val value: ValidatedProjectValues,
    ) : ProjectValidationResult

    data class Invalid(
        val errors: Set<ProjectValidationError>,
    ) : ProjectValidationResult
}

object ProjectValidation {
    private const val MAX_NAME_CODE_POINTS = 60
    private const val MAX_REPEAT_LENGTH = 999

    fun validate(
        name: String,
        counterUnit: CounterUnit,
        count: Long,
        startValue: Int,
        targetCount: Long?,
        repeatLength: Int?,
    ): ProjectValidationResult {
        val trimmedName = name.trim()
        val errors =
            buildSet {
                if (trimmedName.isBlank()) add(ProjectValidationError.NAME_BLANK)
                if (trimmedName.codePointCount(0, trimmedName.length) > MAX_NAME_CODE_POINTS) {
                    add(ProjectValidationError.NAME_TOO_LONG)
                }
                if (count !in 0..CounterConstants.MAX_COUNT) {
                    add(ProjectValidationError.INVALID_COUNT)
                }
                if (startValue != 0 && startValue != 1) {
                    add(ProjectValidationError.INVALID_START_VALUE)
                }
                addAll(optionalValueErrors(targetCount, repeatLength))
            }

        return if (errors.isEmpty()) {
            ProjectValidationResult.Valid(
                ValidatedProjectValues(
                    name = trimmedName,
                    counterUnit = counterUnit,
                    count = count,
                    startValue = startValue,
                    targetCount = targetCount,
                    repeatLength = repeatLength,
                ),
            )
        } else {
            ProjectValidationResult.Invalid(errors)
        }
    }

    private fun optionalValueErrors(
        targetCount: Long?,
        repeatLength: Int?,
    ): Set<ProjectValidationError> =
        buildSet {
            if (targetCount != null && targetCount !in 1..CounterConstants.MAX_COUNT) {
                add(ProjectValidationError.INVALID_TARGET)
            }
            if (repeatLength != null && repeatLength !in 2..MAX_REPEAT_LENGTH) {
                add(ProjectValidationError.INVALID_REPEAT_LENGTH)
            }
        }
}
