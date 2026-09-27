package com.finnvek.rowtool.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finnvek.rowtool.domain.model.RecentHistory
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

internal sealed interface HistoryUiState {
    data object Loading : HistoryUiState

    data object Error : HistoryUiState

    data object Missing : HistoryUiState

    data class Content(
        val history: RecentHistory,
    ) : HistoryUiState
}

internal class HistoryViewModel(
    private val observe: () -> Flow<RecentHistory?>,
) : ViewModel() {
    private val mutableState = MutableStateFlow<HistoryUiState>(HistoryUiState.Loading)
    val state = mutableState.asStateFlow()
    private var reader: Job? = null

    init {
        retry()
    }

    fun retry() {
        reader?.cancel()
        mutableState.value = HistoryUiState.Loading
        reader =
            viewModelScope.launch {
                observe().catch { mutableState.value = HistoryUiState.Error }.collect { history ->
                    mutableState.value = history?.let(HistoryUiState::Content) ?: HistoryUiState.Missing
                }
            }
    }
}
