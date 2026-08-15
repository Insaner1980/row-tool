package com.finnvek.rowtool.ui.screens.projects

import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.domain.model.CounterUnit

data class ProjectEditorValues(
    val name: String,
    val counterUnit: CounterUnit,
    val startValue: Int,
    val targetCount: Long?,
    val repeatLength: Int?,
)

internal suspend fun CounterRepository.updateProjectFromEditor(
    projectId: String,
    values: ProjectEditorValues,
) {
    updateProject(
        id = projectId,
        name = values.name,
        counterUnit = values.counterUnit,
        startValue = values.startValue,
        targetCount = values.targetCount,
        repeatLength = values.repeatLength,
    )
}
