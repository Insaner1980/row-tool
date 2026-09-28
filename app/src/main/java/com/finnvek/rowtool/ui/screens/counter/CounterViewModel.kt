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
import com.finnvek.rowtool.domain.counter.RepeatProgressCalculator
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterMutationResult
import com.finnvek.rowtool.ui.screens.projects.updateProjectFromEditor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
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
}

class CounterViewModel(
    private val projectId: String,
    private val counterRepository: CounterRepository,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {
    private val effectChannel = Channel<CounterEffect>(Channel.BUFFERED)
    val effects = effectChannel.receiveAsFlow()

    // Transient feedback is lost when no resumed route is collecting; never replay it.
    private val hapticFlow = MutableSharedFlow<CounterEffect.Haptic>()
    val haptics = hapticFlow.asSharedFlow()
    private val routeResolved = AtomicBoolean()
    private val unavailableFeedbackHandled = AtomicBoolean()
    internal val noteStore = counterRepository.notes

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
                            reportUnavailableProject(R.string.counter_project_missing)
                        }

                        project.isArchived -> {
                            reportUnavailableProject(R.string.error_archived_project)
                        }

                        else -> {
                            try {
                                preferencesRepository.setLastActiveProjectId(project.id)
                            } catch (_: IOException) {
                                // Startup falls back to the most recently updated active project.
                            }
                        }
                    }
                } else {
                    when {
                        project == null -> reportUnavailableProject()
                        project.isArchived -> reportUnavailableProject(R.string.error_archived_project)
                    }
                }
            }

    val uiState: StateFlow<CounterUiState> =
        combine(
            projectFlow,
            counterRepository.observeCanUndo(projectId),
            counterRepository.additionalCounters.observe(projectId),
            counterRepository.reminders.observe(projectId),
            noteStore.observe(projectId),
        ) { project, canUndo, counters, reminders, note ->
            CounterUiState(
                project = project,
                canUndo = canUndo,
                isLoading = false,
                additionalCounters = counters,
                reminders = reminders,
                note = note,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CounterUiState(isLoading = true),
        )

    fun increment() = mutate(CounterMutation.Increment, emitHaptic = true)

    fun decrement() = mutate(CounterMutation.Decrement, emitHaptic = true)

    fun setCount(count: Long) = mutate(CounterMutation.ManualSet(count), emitHaptic = false)

    fun reset() = mutate(CounterMutation.Reset, emitHaptic = false)

    val additionalEditorActions =
        AdditionalCounterEditorActions(
            onSave = { id, name, followsMain -> counterRepository.additionalCounters.save(projectId, id, name, followsMain) },
            onDelete = { id -> counterRepository.additionalCounters.delete(projectId, id) },
            onSetCount = { id, mutation ->
                when (counterRepository.mutate(projectId, mutation, id)) {
                    is CounterMutationResult.Changed, is CounterMutationResult.NoOp -> true
                    else -> false
                }
            },
        )

    internal val reminderActions =
        ReminderActions(
            onSave = { owner, id, revision, values, creationId ->
                owner == projectId &&
                    counterRepository.reminders.save(owner, id, revision, values, creationId) != null
            },
            onAcknowledge = { owner, id, revision, target ->
                owner == projectId && counterRepository.reminders.acknowledge(owner, id, revision, target)
            },
            onReset = { owner, id, revision ->
                owner == projectId && counterRepository.reminders.resetAcknowledgements(owner, id, revision)
            },
            onDelete = { owner, id, revision ->
                owner == projectId && counterRepository.reminders.delete(owner, id, revision)
            },
        )

    val changeAdditionalCount: (String, CounterMutation) -> Unit = { id, mutation ->
        viewModelScope.launch {
            try {
                handleMutationResult(counterRepository.mutate(projectId, mutation, id), emitHaptic = true)
            } catch (_: SQLException) {
                effectChannel.send(CounterEffect.ShowMessage(R.string.error_database_write))
            }
        }
    }

    fun undo() {
        viewModelScope.launch {
            try {
                handleMutationResult(counterRepository.undo(projectId), emitHaptic = true)
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

    val saveRepeatSettings: suspend (String, Int?, Long?) -> Boolean = { editorProjectId, repeatLength, repeatStartCount ->
        if (editorProjectId != projectId) {
            false
        } else {
            try {
                counterRepository.repeatSettings.save(editorProjectId, repeatLength, repeatStartCount) != null
            } catch (_: SQLException) {
                false
            }
        }
    }

    fun archive() {
        viewModelScope.launch {
            try {
                if (counterRepository.setArchived(projectId, true)) {
                    preferencesRepository.clearLastActiveProjectIdIfMatching(projectId)
                    reportUnavailableProject()
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
                preferencesRepository.clearLastActiveProjectIdIfMatching(projectId)
                reportUnavailableProject()
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
            try {
                handleMutationResult(
                    result = counterRepository.mutate(projectId, mutation),
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
        emitHaptic: Boolean,
        mutation: CounterMutation? = null,
    ) {
        when (result) {
            is CounterMutationResult.Changed -> {
                if (emitHaptic) {
                    hapticFlow.emit(
                        CounterEffect.Haptic(
                            shouldUseStrongHaptic(mutation, result),
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
                reportUnavailableProject(R.string.counter_project_missing)
            }

            CounterMutationResult.ProjectArchived -> {
                reportUnavailableProject(R.string.error_archived_project)
            }

            is CounterMutationResult.Invalid -> {
                effectChannel.send(
                    CounterEffect.ShowMessage(R.string.counter_set_error),
                )
            }

            CounterMutationResult.CounterMissing -> {
                effectChannel.send(CounterEffect.ShowMessage(R.string.error_database_write))
            }
        }
    }

    private fun reportUnavailableProject(
        @StringRes message: Int? = null,
    ) {
        // Feedback must never delay publication of the project state used for navigation.
        if (unavailableFeedbackHandled.compareAndSet(false, true) && message != null) {
            effectChannel.trySend(CounterEffect.ShowMessage(message))
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
    result: CounterMutationResult.Changed,
): Boolean {
    if (mutation != CounterMutation.Increment) {
        return false
    }
    val repeat = RepeatProgressCalculator.calculate(result.newCount, result.repeatLength, result.repeatStartCount)
    val completedRepeat = repeat != null && repeat.currentStep == repeat.repeatLength
    val reachedTarget = result.targetCount?.let { result.previousCount < it && result.newCount == it } == true
    return completedRepeat || reachedTarget || result.reminderReached
}
