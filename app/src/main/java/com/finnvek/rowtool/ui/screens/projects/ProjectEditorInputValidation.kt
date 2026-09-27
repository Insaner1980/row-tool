package com.finnvek.rowtool.ui.screens.projects

import com.finnvek.rowtool.domain.model.ProjectValidation

internal data class ProjectEditorInputValidation(
    val name: String,
    val targetCount: Long?,
    val repeatLength: Int?,
    val repeatStartCount: Long?,
    val nameValid: Boolean,
    val targetValid: Boolean,
    val repeatValid: Boolean,
    val repeatStartValid: Boolean,
) {
    val canSave: Boolean = nameValid && targetValid && repeatValid && repeatStartValid
}

internal data class RepeatSettingsInputValidation(
    val repeatLength: Int?,
    val repeatStartCount: Long?,
    val repeatValid: Boolean,
    val repeatStartValid: Boolean,
) {
    val canSave: Boolean = repeatValid && repeatStartValid
}

internal fun validateRepeatSettingsInput(
    repeatEnabled: Boolean,
    repeatText: String,
    repeatStartText: String,
): RepeatSettingsInputValidation {
    val repeat = if (repeatEnabled) repeatText.toIntOrNull() else null
    val start = if (repeatEnabled) repeatStartText.toLongOrNull() else null
    return RepeatSettingsInputValidation(
        repeatLength = repeat,
        repeatStartCount = start,
        repeatValid = !repeatEnabled || (repeat != null && ProjectValidation.isRepeatValid(repeat)),
        repeatStartValid = !repeatEnabled || ProjectValidation.isRepeatStartValid(repeat, start),
    )
}

internal fun validateProjectEditorInput(
    name: String,
    targetEnabled: Boolean,
    targetText: String,
    repeatEnabled: Boolean,
    repeatText: String,
    repeatStartText: String = "1",
): ProjectEditorInputValidation {
    val trimmedName = ProjectValidation.normalizeName(name)
    val target = if (targetEnabled) targetText.toLongOrNull() else null
    val repeat = validateRepeatSettingsInput(repeatEnabled, repeatText, repeatStartText)
    return ProjectEditorInputValidation(
        name = trimmedName,
        targetCount = target,
        repeatLength = repeat.repeatLength,
        repeatStartCount = repeat.repeatStartCount,
        nameValid = ProjectValidation.nameErrors(trimmedName).isEmpty(),
        targetValid = !targetEnabled || (target != null && ProjectValidation.isTargetValid(target)),
        repeatValid = repeat.repeatValid,
        repeatStartValid = repeat.repeatStartValid,
    )
}
