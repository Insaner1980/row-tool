package com.finnvek.rowtool.ui.screens.projects

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.CounterProject

@Composable
fun ProjectsRoute(
    viewModel: ProjectsViewModel,
    onOpenProject: (String) -> Unit,
    onSettings: () -> Unit,
    onMessage: suspend (Int) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val currentOnOpenProject by rememberUpdatedState(onOpenProject)
    val currentOnMessage by rememberUpdatedState(onMessage)
    var archivedExpanded by rememberSaveable { mutableStateOf(false) }
    var createProject by rememberSaveable { mutableStateOf(false) }
    var editingProject by rememberSaveable { mutableStateOf<CounterProject?>(null) }
    var deleteProject by rememberSaveable { mutableStateOf<CounterProject?>(null) }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is ProjectsEffect.OpenProject -> currentOnOpenProject(effect.projectId)
                is ProjectsEffect.ShowMessage -> currentOnMessage(effect.message)
            }
        }
    }

    ProjectsScreenContent(
        state =
            ProjectsScreenState(
                activeProjects = state.activeProjects,
                archivedProjects = state.archivedProjects,
                archivedExpanded = archivedExpanded,
            ),
        actions =
            ProjectsScreenActions(
                onArchivedExpandedChange = { archivedExpanded = it },
                onNewProject = { createProject = true },
                onSettings = onSettings,
                project =
                    ProjectCardActions(
                        onOpen = { onOpenProject(it.id) },
                        onEdit = { editingProject = it },
                        onArchive = { viewModel.setArchived(it, true) },
                        onRestore = { viewModel.setArchived(it, false) },
                        onDelete = { deleteProject = it },
                    ),
            ),
    )

    if (createProject) {
        ProjectEditorDialog(
            project = null,
            onDismiss = { createProject = false },
            onSave = { values ->
                createProject = false
                viewModel.create(values)
            },
        )
    }

    editingProject?.let { project ->
        ProjectEditorDialog(
            project = project,
            onDismiss = { editingProject = null },
            onSave = { values ->
                editingProject = null
                viewModel.update(project.id, values)
            },
        )
    }

    deleteProject?.let { project ->
        AlertDialog(
            onDismissRequest = { deleteProject = null },
            title = { Text(stringResource(R.string.counter_delete_title)) },
            text = { Text(stringResource(R.string.counter_delete_message, project.name)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        deleteProject = null
                        viewModel.delete(project)
                    },
                ) {
                    Text(stringResource(R.string.action_delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { deleteProject = null }) {
                    Text(stringResource(R.string.action_cancel))
                }
            },
        )
    }
}
