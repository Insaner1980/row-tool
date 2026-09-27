package com.finnvek.rowtool.ui.screens.counter

import android.annotation.SuppressLint
import android.os.Build
import android.view.HapticFeedbackConstants
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.ui.RowToolConfirmationDialog
import com.finnvek.rowtool.ui.screens.note.NoteEditorHost
import com.finnvek.rowtool.ui.screens.projects.ProjectEditorDialog
import com.finnvek.rowtool.ui.screens.projects.ProjectEditorValues
import java.util.UUID

private enum class CounterDialog {
    SET_COUNT,
    EDIT_PROJECT,
    REPEAT,
    RESET,
    ARCHIVE,
    DELETE,
}

private data class CounterDialogActions(
    val onSetCount: (Long) -> Unit,
    val onUpdate: (ProjectEditorValues) -> Unit,
    val onRepeatSave: suspend (String, Int?, Long?) -> Boolean,
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
    onHistory: (String) -> Unit = {},
    openWidgetReminders: Boolean = false,
    onWidgetRemindersOpen: () -> Unit = {},
) {
    val currentOnWidgetRemindersOpen by rememberUpdatedState(onWidgetRemindersOpen)
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val preferences by viewModel.preferences.collectAsStateWithLifecycle()
    val view = LocalView.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val currentHapticsEnabled by rememberUpdatedState(preferences.hapticFeedbackEnabled)
    val currentOnMessage by rememberUpdatedState(onMessage)
    val currentOnProjects by rememberUpdatedState(onProjects)
    var activeDialog by rememberSaveable { mutableStateOf<CounterDialog?>(null) }
    var repeatProjectId by rememberSaveable { mutableStateOf<String?>(null) }
    var additionalDialog by rememberSaveable { mutableStateOf<AdditionalCounterDialog?>(null) }
    var additionalId by rememberSaveable { mutableStateOf<String?>(null) }
    var additionalProjectId by rememberSaveable { mutableStateOf<String?>(null) }
    var reminderProjectId by rememberSaveable { mutableStateOf<String?>(null) }
    LaunchedEffect(openWidgetReminders, state.project?.id) {
        if (openWidgetReminders && state.project != null) {
            reminderProjectId = state.project?.id
            currentOnWidgetRemindersOpen()
        }
    }
    var noteProjectId by rememberSaveable { mutableStateOf<String?>(null) }
    var noteSessionId by rememberSaveable { mutableStateOf("") }
    val openAdditional: (AdditionalCounterDialog, String?) -> Unit = { dialog, id ->
        additionalProjectId = state.project?.id
        additionalId = id
        additionalDialog = dialog
    }
    val shouldKeepScreenOn = preferences.keepScreenAwake && state.project?.isArchived == false

    BackHandler(onBack = onProjects)

    DisposableEffect(view, shouldKeepScreenOn) {
        view.keepScreenOn = shouldKeepScreenOn
        onDispose { view.keepScreenOn = false }
    }

    val mustReturnToProjects = noteProjectId == null && !state.isLoading && (state.project == null || state.project?.isArchived == true)
    LaunchedEffect(viewModel, mustReturnToProjects) {
        if (mustReturnToProjects) currentOnProjects()
    }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            handleCounterEffect(
                effect = effect,
                hapticsEnabled = currentHapticsEnabled,
                onMessage = currentOnMessage,
                onHaptic = { strong ->
                    view.performHapticFeedback(hapticFeedbackConstant(strong))
                },
            )
        }
    }

    LaunchedEffect(viewModel, lifecycle, view) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.haptics.collect { effect ->
                if (lifecycle.currentState == Lifecycle.State.RESUMED && currentHapticsEnabled) {
                    view.performHapticFeedback(hapticFeedbackConstant(effect.strong))
                }
            }
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
                        onHistory = { state.project?.id?.let(onHistory) },
                        onEdit = { activeDialog = CounterDialog.EDIT_PROJECT },
                        onRepeatEdit = {
                            repeatProjectId = state.project?.id
                            activeDialog = CounterDialog.REPEAT
                        },
                        onReminders = { reminderProjectId = state.project?.id },
                        onNote = {
                            noteSessionId = UUID.randomUUID().toString()
                            noteProjectId = state.project?.id
                        },
                        onReset = { activeDialog = CounterDialog.RESET },
                        onArchive = { activeDialog = CounterDialog.ARCHIVE },
                        onDelete = { activeDialog = CounterDialog.DELETE },
                    ),
                additional =
                    AdditionalCounterActions(
                        onAdd = { openAdditional(AdditionalCounterDialog.ADD, null) },
                        onEdit = { openAdditional(AdditionalCounterDialog.EDIT, it) },
                        onSetCount = { openAdditional(AdditionalCounterDialog.COUNT, it) },
                        onReset = { openAdditional(AdditionalCounterDialog.RESET, it) },
                        onDelete = { openAdditional(AdditionalCounterDialog.DELETE, it) },
                        onIncrement = { viewModel.changeAdditionalCount(it, CounterMutation.Increment) },
                        onDecrement = { viewModel.changeAdditionalCount(it, CounterMutation.Decrement) },
                    ),
            ),
    )

    noteProjectId?.let { owner ->
        val session = noteSessionId
        NoteEditorHost(owner, session, viewModel.noteStore, readOnly = state.project?.isArchived == true) {
            if (noteProjectId == owner && noteSessionId == session) noteProjectId = null
        }
    }

    state.project?.let { project ->
        if (reminderProjectId == project.id) {
            key(project.id) {
                ReminderDialogs(
                    project = project,
                    reminders = state.reminders,
                    actions = viewModel.reminderActions,
                    onDismiss = { if (reminderProjectId == project.id) reminderProjectId = null },
                )
            }
        }
        val dialog = additionalDialog
        val counter = state.additionalCounters.firstOrNull { it.id == additionalId }
        val counterAvailable = dialog == AdditionalCounterDialog.ADD || counter != null
        if (dialog != null && additionalProjectId == project.id && counterAvailable) {
            key(project.id, dialog, additionalId) {
                AdditionalCounterDialogs(dialog, counter, viewModel.additionalEditorActions, onDismiss = { additionalDialog = null })
            }
        }
        CounterDialogContent(
            dialog = activeDialog,
            project = project,
            actions =
                CounterDialogActions(
                    onSetCount = viewModel::setCount,
                    onUpdate = viewModel::update,
                    onRepeatSave = viewModel.saveRepeatSettings,
                    onReset = viewModel::reset,
                    onArchive = viewModel::archive,
                    onDelete = viewModel::delete,
                ),
            onDismiss = { activeDialog = null },
            repeatProjectId = repeatProjectId,
        )
    }
}

internal fun handleCounterEffect(
    effect: CounterEffect,
    hapticsEnabled: Boolean,
    onMessage: (Int) -> Unit,
    onHaptic: (Boolean) -> Unit,
) {
    when (effect) {
        is CounterEffect.ShowMessage -> {
            onMessage(effect.message)
        }

        is CounterEffect.Haptic -> {
            if (hapticsEnabled) onHaptic(effect.strong)
        }
    }
}

@Composable
private fun CounterDialogContent(
    dialog: CounterDialog?,
    project: CounterProject,
    actions: CounterDialogActions,
    onDismiss: () -> Unit,
    repeatProjectId: String?,
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

        CounterDialog.REPEAT -> {
            if (repeatProjectId == project.id) {
                RepeatSettingsDialog(project = project, onDismiss = onDismiss, onSave = actions.onRepeatSave)
            }
        }

        CounterDialog.RESET -> {
            RowToolConfirmationDialog(
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
            RowToolConfirmationDialog(
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
            RowToolConfirmationDialog(
                title = stringResource(R.string.counter_delete_title),
                message = stringResource(R.string.counter_delete_message, project.name),
                confirmLabel = stringResource(R.string.action_delete),
                isDestructive = true,
                onDismiss = onDismiss,
                onConfirm = {
                    onDismiss()
                    actions.onDelete()
                },
            )
        }
    }
}
