package com.finnvek.rowtool.ui.screens.counter

import android.annotation.SuppressLint
import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View
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
import kotlinx.coroutines.flow.Flow

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
    onMessage: suspend (Int) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val preferences by viewModel.preferences.collectAsStateWithLifecycle()
    val view = LocalView.current
    var activeDialog by rememberSaveable { mutableStateOf<CounterDialog?>(null) }

    BackHandler(onBack = onProjects)

    DisposableEffect(view, preferences.keepScreenAwake, state.project?.id) {
        view.keepScreenOn = preferences.keepScreenAwake && state.project != null
        onDispose { view.keepScreenOn = false }
    }

    CollectCounterEffects(
        effects = viewModel.effects,
        view = view,
        hapticsEnabled = preferences.hapticFeedbackEnabled,
        onMessage = onMessage,
        onProjects = onProjects,
    )

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

@Composable
private fun CollectCounterEffects(
    effects: Flow<CounterEffect>,
    view: View,
    hapticsEnabled: Boolean,
    onMessage: suspend (Int) -> Unit,
    onProjects: () -> Unit,
) {
    val currentHapticsEnabled by rememberUpdatedState(hapticsEnabled)
    val currentOnMessage by rememberUpdatedState(onMessage)
    val currentOnProjects by rememberUpdatedState(onProjects)
    LaunchedEffect(effects) {
        effects.collect { effect ->
            when (effect) {
                is CounterEffect.ShowMessage -> {
                    currentOnMessage(effect.message)
                }

                is CounterEffect.Haptic -> {
                    if (currentHapticsEnabled) {
                        view.performHapticFeedback(hapticFeedbackConstant(effect.strong))
                    }
                }

                is CounterEffect.ReturnToProjects -> {
                    effect.message?.let { currentOnMessage(it) }
                    currentOnProjects()
                }
            }
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
