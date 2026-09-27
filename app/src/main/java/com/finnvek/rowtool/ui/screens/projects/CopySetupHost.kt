package com.finnvek.rowtool.ui.screens.projects

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finnvek.rowtool.R
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.ui.ordinaryDialogActionColors

@Composable
internal fun CopySetupHost(
    sourceId: String,
    session: String,
    repository: CounterRepository,
    preferences: PreferencesRepository,
    onDismiss: () -> Unit,
    onComplete: (String) -> Unit,
    model: CopySetupViewModel =
        viewModel(
            key = "copy-$sourceId-$session",
            factory = viewModelFactory { initializer { CopySetupViewModel(sourceId, repository, preferences, createSavedStateHandle()) } },
        ),
) {
    val state by model.state.collectAsStateWithLifecycle()
    val complete by rememberUpdatedState(onComplete)
    val lifecycle by LocalLifecycleOwner.current.lifecycle.currentStateFlow
        .collectAsStateWithLifecycle()
    val container = (LocalContext.current.applicationContext as RowToolApplication).container
    DisposableEffect(container) {
        container.copyEditorVisible = true
        onDispose { container.copyEditorVisible = false }
    }
    LaunchedEffect(state.completedId, lifecycle) {
        if (lifecycle == Lifecycle.State.RESUMED) state.completedId?.let { complete(it) }
    }
    val snapshot = state.snapshot
    if (snapshot != null) {
        val settings = snapshot.settings
        ProjectEditorDialog(
            project =
                CounterProject(
                    snapshot.projectId,
                    "",
                    settings.counterUnit,
                    settings.startValue.toLong(),
                    settings.startValue,
                    settings.targetCount,
                    settings.repeatLength,
                    false,
                    0,
                    0,
                    settings.repeatStartCount,
                ),
            onDismiss = onDismiss,
            onSave = model::create,
            copyState = state,
        )
    } else {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(stringResource(R.string.copy_title)) },
            text = { if (state.loading) CircularProgressIndicator() else Text(stringResource(state.error ?: R.string.copy_read_error)) },
            confirmButton = {
                if (!state.loading) {
                    TextButton(onClick = model::load, colors = ordinaryDialogActionColors()) {
                        Text(stringResource(R.string.note_retry))
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss, colors = ordinaryDialogActionColors()) { Text(stringResource(R.string.action_cancel)) }
            },
        )
    }
}
