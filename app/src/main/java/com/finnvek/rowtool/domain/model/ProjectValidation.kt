package com.finnvek.rowtool.domain.model

enum class ProjectValidationError {
    NAME_BLANK,
    NAME_TOO_LONG,
    INVALID_COUNT,
    INVALID_START_VALUE,
    INVALID_TARGET,
    INVALID_REPEAT_LENGTH,
    INVALID_REPEAT_START,
}

data class ValidatedProjectValues(
    val name: String,
    val counterUnit: CounterUnit,
    val count: Long,
    val startValue: Int,
    val targetCount: Long?,
    val repeatLength: Int?,
    val repeatStartCount: Long?,
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
    internal const val MAX_NAME_CODE_POINTS = 60
    internal const val MAX_REPEAT_LENGTH = 999
    internal const val MIN_TARGET_COUNT = 1L
    internal const val MIN_REPEAT_LENGTH = 2

    internal fun normalizeName(name: String): String = name.trim()

    internal fun nameErrors(name: String): Set<ProjectValidationError> {
        val trimmedName = normalizeName(name)
        return buildSet {
            if (trimmedName.isBlank()) add(ProjectValidationError.NAME_BLANK)
            if (trimmedName.codePointCount(0, trimmedName.length) > MAX_NAME_CODE_POINTS) {
                add(ProjectValidationError.NAME_TOO_LONG)
            }
        }
    }

    internal fun isTargetValid(targetCount: Long?): Boolean =
        targetCount == null || targetCount in MIN_TARGET_COUNT..CounterConstants.MAX_COUNT

    internal fun isRepeatValid(repeatLength: Int?): Boolean = repeatLength == null || repeatLength in MIN_REPEAT_LENGTH..MAX_REPEAT_LENGTH

    internal fun isRepeatStartValid(
        repeatLength: Int?,
        repeatStartCount: Long?,
    ): Boolean =
        if (repeatLength == null) {
            repeatStartCount == null
        } else {
            repeatStartCount != null && repeatStartCount in 1L..CounterConstants.MAX_COUNT
        }

    fun validate(
        name: String,
        counterUnit: CounterUnit,
        count: Long,
        startValue: Int,
        targetCount: Long?,
        repeatLength: Int?,
        repeatStartCount: Long? = if (repeatLength != null) 1L else null,
    ): ProjectValidationResult {
        val trimmedName = normalizeName(name)
        val errors =
            buildSet {
                addAll(nameErrors(trimmedName))
                if (count !in CounterConstants.MIN_COUNT..CounterConstants.MAX_COUNT) {
                    add(ProjectValidationError.INVALID_COUNT)
                }
                if (startValue != 0 && startValue != 1) {
                    add(ProjectValidationError.INVALID_START_VALUE)
                }
                addAll(optionalValueErrors(targetCount, repeatLength))
                if (!isRepeatStartValid(repeatLength, repeatStartCount)) add(ProjectValidationError.INVALID_REPEAT_START)
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
                    repeatStartCount = repeatStartCount,
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
            if (!isTargetValid(targetCount)) {
                add(ProjectValidationError.INVALID_TARGET)
            }
            if (!isRepeatValid(repeatLength)) {
                add(ProjectValidationError.INVALID_REPEAT_LENGTH)
            }
        }
}
