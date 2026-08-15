package com.finnvek.rowtool.ui.screens.projects

import android.database.SQLException
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finnvek.rowtool.R
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.domain.model.CounterProject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

data class ProjectsUiState(
    val activeProjects: List<CounterProject> = emptyList(),
    val archivedProjects: List<CounterProject> = emptyList(),
)

sealed interface ProjectsEffect {
    data class OpenProject(
        val projectId: String,
    ) : ProjectsEffect

    data class ShowMessage(
        @StringRes val message: Int,
    ) : ProjectsEffect
}

class ProjectsViewModel(
    private val counterRepository: CounterRepository,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {
    val uiState: StateFlow<ProjectsUiState> =
        counterRepository.projects
            .map { projects ->
                ProjectsUiState(
                    activeProjects = projects.filterNot(CounterProject::isArchived),
                    archivedProjects = projects.filter(CounterProject::isArchived),
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ProjectsUiState(),
            )

    private val effectChannel = Channel<ProjectsEffect>(Channel.BUFFERED)
    val effects = effectChannel.receiveAsFlow()

    fun create(values: ProjectEditorValues) {
        viewModelScope.launch {
            try {
                val project =
                    counterRepository.createProject(
                        name = values.name,
                        counterUnit = values.counterUnit,
                        startValue = values.startValue,
                        targetCount = values.targetCount,
                        repeatLength = values.repeatLength,
                    )
                try {
                    preferencesRepository.setLastActiveProjectId(project.id)
                } catch (_: IOException) {
                    // The persisted project remains usable and startup can resolve it from Room.
                }
                effectChannel.send(ProjectsEffect.OpenProject(project.id))
            } catch (_: SQLException) {
                effectChannel.send(ProjectsEffect.ShowMessage(R.string.error_database_write))
            }
        }
    }

    fun update(
        projectId: String,
        values: ProjectEditorValues,
    ) {
        viewModelScope.launch {
            try {
                counterRepository.updateProjectFromEditor(projectId, values)
            } catch (_: SQLException) {
                effectChannel.send(ProjectsEffect.ShowMessage(R.string.error_database_write))
            }
        }
    }

    fun setArchived(
        project: CounterProject,
        archived: Boolean,
    ) {
        viewModelScope.launch {
            try {
                if (counterRepository.setArchived(project.id, archived) && archived) {
                    clearLastActiveIfMatching(project.id)
                }
            } catch (_: SQLException) {
                effectChannel.send(ProjectsEffect.ShowMessage(R.string.error_database_write))
            }
        }
    }

    fun delete(project: CounterProject) {
        viewModelScope.launch {
            try {
                counterRepository.deleteProject(project.id)
                clearLastActiveIfMatching(project.id)
            } catch (_: SQLException) {
                effectChannel.send(ProjectsEffect.ShowMessage(R.string.error_database_write))
            }
        }
    }

    private suspend fun clearLastActiveIfMatching(projectId: String) {
        if (preferencesRepository.preferences.first().lastActiveProjectId == projectId) {
            try {
                preferencesRepository.setLastActiveProjectId(null)
            } catch (_: IOException) {
                // A stale preference is repaired by the startup resolver.
            }
        }
    }

    companion object {
        fun factory(
            counterRepository: CounterRepository,
            preferencesRepository: PreferencesRepository,
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer { ProjectsViewModel(counterRepository, preferencesRepository) }
            }
    }
}
