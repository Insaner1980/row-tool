package com.finnvek.rowtool.ui.screens.counter

import com.finnvek.rowtool.domain.model.AdditionalCounter
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.ProjectNote
import com.finnvek.rowtool.domain.model.Reminder

data class CounterUiState(
    val project: CounterProject? = null,
    val canUndo: Boolean = false,
    val isLoading: Boolean = false,
    val additionalCounters: List<AdditionalCounter> = emptyList(),
    val reminders: List<Reminder> = emptyList(),
    val note: ProjectNote? = null,
)
