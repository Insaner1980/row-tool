package com.finnvek.rowtool.ui.screens.projects

import com.finnvek.rowtool.domain.model.ProjectValidation

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
    val trimmedName = ProjectValidation.normalizeName(name)
    val target = if (targetEnabled) targetText.toLongOrNull() else null
    val repeat = if (repeatEnabled) repeatText.toIntOrNull() else null
    return ProjectEditorInputValidation(
        name = trimmedName,
        targetCount = target,
        repeatLength = repeat,
        nameValid = ProjectValidation.nameErrors(trimmedName).isEmpty(),
        targetValid = !targetEnabled || (target != null && ProjectValidation.isTargetValid(target)),
        repeatValid = !repeatEnabled || (repeat != null && ProjectValidation.isRepeatValid(repeat)),
    )
}
