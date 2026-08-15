package com.finnvek.rowtool.ui.screens.counter

import com.finnvek.rowtool.domain.model.CounterProject

data class CounterUiState(
    val project: CounterProject? = null,
    val canUndo: Boolean = false,
    val isLoading: Boolean = false,
)
