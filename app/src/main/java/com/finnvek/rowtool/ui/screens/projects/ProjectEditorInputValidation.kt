package com.finnvek.rowtool.ui.screens.projects

import com.finnvek.rowtool.domain.model.CounterConstants

private const val MAX_NAME_CODE_POINTS = 60
private const val MAX_REPEAT_LENGTH = 999

internal data class ProjectEditorInputValidation(
    val name: String,
    val targetCount: Long?,
    val repeatLength: Int?,
    val nameValid: Boolean,
    val targetValid: Boolean,
    val repeatValid: Boolean,
) {
    val canSave: Boolean = nameValid && targetValid && repeatValid
}

internal fun validateProjectEditorInput(
    name: String,
    targetEnabled: Boolean,
    targetText: String,
    repeatEnabled: Boolean,
    repeatText: String,
): ProjectEditorInputValidation {
    val trimmedName = name.trim()
    val target = if (targetEnabled) targetText.toLongOrNull() else null
    val repeat = if (repeatEnabled) repeatText.toIntOrNull() else null
    return ProjectEditorInputValidation(
        name = trimmedName,
        targetCount = target,
        repeatLength = repeat,
        nameValid =
            trimmedName.isNotEmpty() &&
                trimmedName.codePointCount(0, trimmedName.length) <= MAX_NAME_CODE_POINTS,
        targetValid = !targetEnabled || target in 1L..CounterConstants.MAX_COUNT,
        repeatValid = !repeatEnabled || repeat in 2..MAX_REPEAT_LENGTH,
    )
}
