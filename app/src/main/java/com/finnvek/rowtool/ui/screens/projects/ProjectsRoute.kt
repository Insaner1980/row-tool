package com.finnvek.rowtool.ui.screens.projects

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
import com.finnvek.rowtool.ui.RowToolConfirmationDialog
import com.finnvek.rowtool.ui.screens.note.NoteEditorHost
import java.util.UUID

@Composable
fun ProjectsRoute(
    viewModel: ProjectsViewModel,
    onOpenProject: (String) -> Unit,
    onSettings: () -> Unit,
    onMessage: (Int) -> Unit,
    onHistory: (String) -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val currentOnOpenProject by rememberUpdatedState(onOpenProject)
    val currentOnMessage by rememberUpdatedState(onMessage)
    var copySourceId by rememberSaveable { mutableStateOf<String?>(null) }
    var copySession by rememberSaveable { mutableStateOf("") }
    var archivedExpanded by rememberSaveable { mutableStateOf(false) }
    var createProject by rememberSaveable { mutableStateOf(false) }
    var editingProjectId by rememberSaveable { mutableStateOf<String?>(null) }
    var deleteProjectId by rememberSaveable { mutableStateOf<String?>(null) }
    var noteProjectId by rememberSaveable { mutableStateOf<String?>(null) }
    var noteSessionId by rememberSaveable { mutableStateOf("") }
    val editingProject =
        editingProjectId?.let { id ->
            state.activeProjects.firstOrNull { it.id == id }
                ?: state.archivedProjects.firstOrNull { it.id == id }
        }
    val deleteProject =
        deleteProjectId?.let { id ->
            state.activeProjects.firstOrNull { it.id == id }
                ?: state.archivedProjects.firstOrNull { it.id == id }
        }

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
                isLoading = state.isLoading,
            ),
        actions =
            ProjectsScreenActions(
                onArchivedExpandedChange = { archivedExpanded = it },
                onNewProject = { createProject = true },
                onSettings = onSettings,
                project =
                    ProjectCardActions(
                        onCopySetup = {
                            copySession = UUID.randomUUID().toString()
                            copySourceId = it.id
                        },
                        onHistory = { onHistory(it.id) },
                        onOpen = { onOpenProject(it.id) },
                        onEdit = { editingProjectId = it.id },
                        onArchive = { viewModel.setArchived(it, true) },
                        onRestore = { viewModel.setArchived(it, false) },
                        onDelete = { deleteProjectId = it.id },
                        canOpenNote = { it.id in state.noteProjectIds },
                        onNote = {
                            noteSessionId = UUID.randomUUID().toString()
                            noteProjectId = it.id
                        },
                    ),
            ),
    )

    copySourceId?.let { owner ->
        val session = copySession
        CopySetupHost(
            owner,
            session,
            viewModel.counterRepository,
            viewModel.preferencesRepository,
            onDismiss = { if (copySourceId == owner && copySession == session) copySourceId = null },
            onComplete = { id ->
                if (copySourceId == owner && copySession == session) {
                    onOpenProject(id)
                    copySourceId = null
                }
            },
        )
    }

    noteProjectId?.let { owner ->
        val session = noteSessionId
        NoteEditorHost(owner, session, viewModel.noteStore) {
            if (noteProjectId == owner && noteSessionId == session) noteProjectId = null
        }
    }

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
            onDismiss = { editingProjectId = null },
            onSave = { values ->
                editingProjectId = null
                viewModel.update(project.id, values)
            },
        )
    }

    deleteProject?.let { project ->
        RowToolConfirmationDialog(
            title = stringResource(R.string.counter_delete_title),
            message = stringResource(R.string.counter_delete_message, project.name),
            confirmLabel = stringResource(R.string.action_delete),
            isDestructive = true,
            onDismiss = { deleteProjectId = null },
            onConfirm = {
                deleteProjectId = null
                viewModel.delete(project)
            },
        )
    }
}
