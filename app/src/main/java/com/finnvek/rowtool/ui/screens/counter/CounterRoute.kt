package com.finnvek.rowtool.ui.screens.counter

import android.annotation.SuppressLint
import android.os.Build
import android.view.HapticFeedbackConstants
import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.ui.screens.projects.ProjectEditorDialog
import com.finnvek.rowtool.ui.screens.projects.ProjectEditorValues

private enum class CounterDialog {
    SET_COUNT,
    EDIT_PROJECT,
    RESET,
    ARCHIVE,
    DELETE,
}

private data class CounterDialogActions(
    val onSetCount: (Long) -> Unit,
    val onUpdate: (ProjectEditorValues) -> Unit,
    val onReset: () -> Unit,
    val onArchive: () -> Unit,
    val onDelete: () -> Unit,
)

@SuppressLint("InlinedApi")
internal fun hapticFeedbackConstant(
    strong: Boolean,
    sdkInt: Int = Build.VERSION.SDK_INT,
): Int =
    when {
        !strong -> HapticFeedbackConstants.CLOCK_TICK
        sdkInt >= Build.VERSION_CODES.R -> HapticFeedbackConstants.CONFIRM
        else -> HapticFeedbackConstants.LONG_PRESS
    }

@Composable
fun CounterRoute(
    viewModel: CounterViewModel,
    onProjects: () -> Unit,
    onSettings: () -> Unit,
    onMessage: (Int) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val preferences by viewModel.preferences.collectAsStateWithLifecycle()
    val view = LocalView.current
    val currentHapticsEnabled by rememberUpdatedState(preferences.hapticFeedbackEnabled)
    val currentOnMessage by rememberUpdatedState(onMessage)
    val currentOnProjects by rememberUpdatedState(onProjects)
    var activeDialog by rememberSaveable { mutableStateOf<CounterDialog?>(null) }
    val shouldKeepScreenOn = preferences.keepScreenAwake && state.project?.isArchived == false

    BackHandler(onBack = onProjects)

    DisposableEffect(view, shouldKeepScreenOn) {
        view.keepScreenOn = shouldKeepScreenOn
        onDispose { view.keepScreenOn = false }
    }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            handleCounterEffect(
                effect = effect,
                hapticsEnabled = currentHapticsEnabled,
                onMessage = currentOnMessage,
                onProjects = currentOnProjects,
                onHaptic = { strong ->
                    view.performHapticFeedback(hapticFeedbackConstant(strong))
                },
            )
        }
    }

    CounterScreenContent(
        state = state,
        actions =
            CounterScreenActions(
                navigation = CounterNavigationActions(onBack = onProjects, onSettings = onSettings),
                value =
                    CounterValueActions(
                        onIncrement = viewModel::increment,
                        onDecrement = viewModel::decrement,
                        onUndo = viewModel::undo,
                        onSetCount = { activeDialog = CounterDialog.SET_COUNT },
                    ),
                project =
                    CounterProjectActions(
                        onEdit = { activeDialog = CounterDialog.EDIT_PROJECT },
                        onReset = { activeDialog = CounterDialog.RESET },
                        onArchive = { activeDialog = CounterDialog.ARCHIVE },
                        onDelete = { activeDialog = CounterDialog.DELETE },
                    ),
            ),
    )

    state.project?.let { project ->
        CounterDialogContent(
            dialog = activeDialog,
            project = project,
            actions =
                CounterDialogActions(
                    onSetCount = viewModel::setCount,
                    onUpdate = viewModel::update,
                    onReset = viewModel::reset,
                    onArchive = viewModel::archive,
                    onDelete = viewModel::delete,
                ),
            onDismiss = { activeDialog = null },
        )
    }
}

internal fun handleCounterEffect(
    effect: CounterEffect,
    hapticsEnabled: Boolean,
    onMessage: (Int) -> Unit,
    onProjects: () -> Unit,
    onHaptic: (Boolean) -> Unit,
) {
    when (effect) {
        is CounterEffect.ShowMessage -> {
            onMessage(effect.message)
        }

        is CounterEffect.Haptic -> {
            if (hapticsEnabled) onHaptic(effect.strong)
        }

        is CounterEffect.ReturnToProjects -> {
            onProjects()
            effect.message?.let(onMessage)
        }
    }
}

@Composable
private fun CounterDialogContent(
    dialog: CounterDialog?,
    project: CounterProject,
    actions: CounterDialogActions,
    onDismiss: () -> Unit,
) {
    if (dialog == null) return
    when (dialog) {
        CounterDialog.SET_COUNT -> {
            CountEditorDialog(
                currentCount = project.count,
                onDismiss = onDismiss,
                onSave = { count ->
                    onDismiss()
                    actions.onSetCount(count)
                },
            )
        }

        CounterDialog.EDIT_PROJECT -> {
            ProjectEditorDialog(
                project = project,
                onDismiss = onDismiss,
                onSave = { values ->
                    onDismiss()
                    actions.onUpdate(values)
                },
            )
        }

        CounterDialog.RESET -> {
            ConfirmationDialog(
                title = stringResource(R.string.counter_reset_title),
                message = stringResource(R.string.counter_reset_message, project.startValue),
                confirmLabel = stringResource(R.string.action_reset),
                onDismiss = onDismiss,
                onConfirm = {
                    onDismiss()
                    actions.onReset()
                },
            )
        }

        CounterDialog.ARCHIVE -> {
            ConfirmationDialog(
                title = stringResource(R.string.counter_archive_title),
                message = stringResource(R.string.counter_archive_message, project.name),
                confirmLabel = stringResource(R.string.action_archive),
                onDismiss = onDismiss,
                onConfirm = {
                    onDismiss()
                    actions.onArchive()
                },
            )
        }

        CounterDialog.DELETE -> {
            ConfirmationDialog(
                title = stringResource(R.string.counter_delete_title),
                message = stringResource(R.string.counter_delete_message, project.name),
                confirmLabel = stringResource(R.string.action_delete),
                onDismiss = onDismiss,
                onConfirm = {
                    onDismiss()
                    actions.onDelete()
                },
            )
        }
    }
}

@Composable
private fun ConfirmationDialog(
    title: String,
    message: String,
    confirmLabel: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmLabel)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.action_cancel))
            }
        },
    )
}
