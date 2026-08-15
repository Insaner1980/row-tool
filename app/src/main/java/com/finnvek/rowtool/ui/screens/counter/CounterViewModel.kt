package com.finnvek.rowtool.ui.screens.counter

import android.database.SQLException
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finnvek.rowtool.R
import com.finnvek.rowtool.data.preferences.AppPreferences
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterMutationResult
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.ui.screens.projects.updateProjectFromEditor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

sealed interface CounterEffect {
    data class ShowMessage(
        @StringRes val message: Int,
    ) : CounterEffect

    data class Haptic(
        val strong: Boolean,
    ) : CounterEffect

    data class ReturnToProjects(
        @StringRes val message: Int? = null,
    ) : CounterEffect
}

class CounterViewModel(
    private val projectId: String,
    private val counterRepository: CounterRepository,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {
    private val effectChannel = Channel<CounterEffect>(Channel.BUFFERED)
    val effects = effectChannel.receiveAsFlow()
    private val routeResolved = AtomicBoolean()

    val preferences: StateFlow<AppPreferences> =
        preferencesRepository.preferences.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppPreferences(),
        )

    private val projectFlow =
        counterRepository
            .observeProject(projectId)
            .onEach { project ->
                if (routeResolved.compareAndSet(false, true)) {
                    when {
                        project == null -> {
                            effectChannel.send(
                                CounterEffect.ReturnToProjects(R.string.counter_project_missing),
                            )
                        }

                        project.isArchived -> {
                            effectChannel.send(
                                CounterEffect.ReturnToProjects(R.string.error_archived_project),
                            )
                        }

                        else -> {
                            try {
                                preferencesRepository.setLastActiveProjectId(project.id)
                            } catch (_: IOException) {
                                // Startup falls back to the most recently updated active project.
                            }
                        }
                    }
                } else if (project == null) {
                    effectChannel.send(CounterEffect.ReturnToProjects())
                }
            }

    val uiState: StateFlow<CounterUiState> =
        combine(
            projectFlow,
            counterRepository.observeCanUndo(projectId),
        ) { project, canUndo ->
            CounterUiState(project = project, canUndo = canUndo, isLoading = false)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CounterUiState(isLoading = true),
        )

    fun increment() = mutate(CounterMutation.Increment, emitHaptic = true)

    fun decrement() = mutate(CounterMutation.Decrement, emitHaptic = true)

    fun setCount(count: Long) = mutate(CounterMutation.ManualSet(count), emitHaptic = false)

    fun reset() = mutate(CounterMutation.Reset, emitHaptic = false)

    fun undo() {
        viewModelScope.launch {
            try {
                handleMutationResult(counterRepository.undo(projectId), null, emitHaptic = true)
            } catch (_: SQLException) {
                effectChannel.send(CounterEffect.ShowMessage(R.string.error_database_write))
            }
        }
    }

    fun update(values: com.finnvek.rowtool.ui.screens.projects.ProjectEditorValues) {
        viewModelScope.launch {
            try {
                counterRepository.updateProjectFromEditor(projectId, values)
            } catch (_: SQLException) {
                effectChannel.send(CounterEffect.ShowMessage(R.string.error_database_write))
            }
        }
    }

    fun archive() {
        viewModelScope.launch {
            try {
                if (counterRepository.setArchived(projectId, true)) {
                    clearLastActive()
                    effectChannel.send(CounterEffect.ReturnToProjects())
                }
            } catch (_: SQLException) {
                effectChannel.send(CounterEffect.ShowMessage(R.string.error_database_write))
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            try {
                counterRepository.deleteProject(projectId)
                clearLastActive()
                effectChannel.send(CounterEffect.ReturnToProjects())
            } catch (_: SQLException) {
                effectChannel.send(CounterEffect.ShowMessage(R.string.error_database_write))
            }
        }
    }

    private fun mutate(
        mutation: CounterMutation,
        emitHaptic: Boolean,
    ) {
        viewModelScope.launch {
            val project = uiState.value.project
            try {
                handleMutationResult(
                    result = counterRepository.mutate(projectId, mutation),
                    project = project,
                    emitHaptic = emitHaptic,
                    mutation = mutation,
                )
            } catch (_: SQLException) {
                effectChannel.send(CounterEffect.ShowMessage(R.string.error_database_write))
            }
        }
    }

    private suspend fun handleMutationResult(
        result: CounterMutationResult,
        project: CounterProject?,
        emitHaptic: Boolean,
        mutation: CounterMutation? = null,
    ) {
        when (result) {
            is CounterMutationResult.Changed -> {
                if (emitHaptic) {
                    effectChannel.send(
                        CounterEffect.Haptic(
                            shouldUseStrongHaptic(mutation, project, result),
                        ),
                    )
                }
            }

            is CounterMutationResult.NoOp -> {
                if (mutation == CounterMutation.Increment) {
                    effectChannel.send(CounterEffect.ShowMessage(R.string.counter_max_reached))
                }
            }

            CounterMutationResult.ProjectMissing -> {
                effectChannel.send(
                    CounterEffect.ReturnToProjects(R.string.counter_project_missing),
                )
            }

            CounterMutationResult.ProjectArchived -> {
                effectChannel.send(
                    CounterEffect.ReturnToProjects(R.string.error_archived_project),
                )
            }

            is CounterMutationResult.Invalid -> {
                effectChannel.send(
                    CounterEffect.ShowMessage(R.string.counter_set_error),
                )
            }
        }
    }

    private suspend fun clearLastActive() {
        if (preferencesRepository.preferences.first().lastActiveProjectId == projectId) {
            try {
                preferencesRepository.setLastActiveProjectId(null)
            } catch (_: IOException) {
                // Startup repairs stale last-active state against Room.
            }
        }
    }

    companion object {
        fun factory(
            projectId: String,
            counterRepository: CounterRepository,
            preferencesRepository: PreferencesRepository,
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    CounterViewModel(projectId, counterRepository, preferencesRepository)
                }
            }
    }
}

private fun shouldUseStrongHaptic(
    mutation: CounterMutation?,
    project: CounterProject?,
    result: CounterMutationResult.Changed,
): Boolean {
    if (mutation != CounterMutation.Increment || project == null) {
        return false
    }
    val completedRepeat = project.repeatLength?.let { result.newCount > 0 && result.newCount % it == 0L } == true
    val reachedTarget = project.targetCount?.let { result.previousCount < it && result.newCount == it } == true
    return completedRepeat || reachedTarget
}
