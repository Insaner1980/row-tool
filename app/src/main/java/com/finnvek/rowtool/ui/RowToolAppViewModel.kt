package com.finnvek.rowtool.ui

import android.database.SQLException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finnvek.rowtool.data.preferences.AppPreferences
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

data class StartupState(
    val isResolved: Boolean = false,
    val projectId: String? = null,
)

class RowToolAppViewModel(
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {
    val preferences: StateFlow<AppPreferences> =
        preferencesRepository.preferences.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = AppPreferences(),
        )

    private val _startupState = MutableStateFlow(StartupState())
    val startupState = _startupState.asStateFlow()

    init {
        viewModelScope.launch {
            val projectId =
                try {
                    preferencesRepository.resolveLastActiveProjectId()
                } catch (_: IOException) {
                    null
                } catch (_: SQLException) {
                    null
                }
            _startupState.value =
                StartupState(
                    isResolved = true,
                    projectId = projectId,
                )
        }
    }

    companion object {
        fun factory(repository: PreferencesRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer { RowToolAppViewModel(repository) }
            }
    }
}
