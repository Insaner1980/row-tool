package com.finnvek.rowtool.ui.screens.note

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finnvek.rowtool.R
import com.finnvek.rowtool.data.repository.ProjectNoteStore
import com.finnvek.rowtool.domain.model.ProjectNoteRules
import com.finnvek.rowtool.ui.RowToolConfirmationDialog
import com.finnvek.rowtool.ui.destructiveDialogActionColors
import com.finnvek.rowtool.ui.ordinaryDialogActionColors
import com.finnvek.rowtool.ui.theme.RowToolDimens
import java.text.NumberFormat

@Composable
internal fun NoteEditorHost(
    projectId: String,
    sessionId: String,
    store: ProjectNoteStore,
    readOnly: Boolean = false,
    viewModel: NoteEditorViewModel =
        viewModel(
            key = "note-$projectId-$sessionId",
            factory = viewModelFactory { initializer { NoteEditorViewModel(projectId, store, createSavedStateHandle()) } },
        ),
    onDismiss: () -> Unit,
) {
    val container =
        (androidx.compose.ui.platform.LocalContext.current.applicationContext as com.finnvek.rowtool.RowToolApplication)
            .container
    androidx.compose.runtime.DisposableEffect(container) {
        container.noteEditorVisible = true
        onDispose { container.noteEditorVisible = false }
    }
    val editorState by viewModel.state.collectAsStateWithLifecycle()
    val state = editorState.copy(readOnly = editorState.readOnly || readOnly)
    val dismiss by rememberUpdatedState(onDismiss)
    LaunchedEffect(state.completed) { if (state.completed) dismiss() }
    Dialog(
        onDismissRequest = viewModel::requestExit,
        properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false),
    ) {
        val view = LocalView.current
        val lightBackground = MaterialTheme.colorScheme.background.luminance() > 0.5f
        SideEffect {
            val window = (view.parent as DialogWindowProvider).window
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = lightBackground
                isAppearanceLightNavigationBars = lightBackground
            }
        }
        BackHandler(onBack = viewModel::requestExit)
        NoteEditorContent(
            state,
            NoteEditorActions(
                onExit = viewModel::requestExit,
                onText = viewModel::editText,
                onAttach = viewModel::attachCount,
                onDelete = { viewModel.requestConfirmation(NoteConfirmation.DELETE) },
                onRetry = viewModel::retryLoad,
                onReload = { viewModel.requestConfirmation(NoteConfirmation.RELOAD) },
                onSave = viewModel::save,
            ),
        )
        NoteConfirmationDialog(state, viewModel::cancelConfirmation, viewModel::confirm)
    }
}

@Composable
private fun NoteEditorContent(
    state: NoteEditorState,
    actions: NoteEditorActions,
) {
    Surface(Modifier.fillMaxSize().testTag("note-editor"), color = MaterialTheme.colorScheme.background) {
        Column(Modifier.safeDrawingPadding().imePadding().padding(horizontal = RowToolDimens.Space16)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = actions.onExit, enabled = !state.saving, modifier = Modifier.testTag("note-back")) {
                    Icon(painterResource(R.drawable.ic_back), stringResource(R.string.action_back))
                }
                Text(stringResource(R.string.note_title), style = MaterialTheme.typography.headlineSmall)
            }
            Column(
                Modifier.weight(1f).verticalScroll(rememberScrollState()).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space12),
            ) {
                NoteEditorStatus(state, actions)
            }
            if (state.ready && !state.readOnly) {
                TextButton(
                    onClick = actions.onSave,
                    enabled = !state.saving,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("note-save"),
                    colors = ordinaryDialogActionColors(),
                ) { Text(stringResource(R.string.action_save)) }
            }
        }
    }
}

@Composable
private fun ColumnScope.NoteEditorStatus(
    state: NoteEditorState,
    actions: NoteEditorActions,
) {
    Text(state.projectName, style = MaterialTheme.typography.titleMedium)
    if (state.loading) CircularProgressIndicator()
    if (state.ready) {
        NoteEditorBody(state, actions.onText, actions.onAttach) {
            actions.onDelete()
        }
    }
    NoteEditorError(state.error)
    if (!state.ready && !state.loading) {
        NoteAction(R.string.note_retry, actions.onRetry)
    }
    if (state.error == NoteError.CONFLICT || state.error == NoteError.UNAVAILABLE) {
        NoteAction(R.string.note_reload) { actions.onReload() }
    }
}

@Composable
private fun ColumnScope.NoteEditorBody(
    state: NoteEditorState,
    onText: (String) -> Unit,
    onAttach: (Boolean) -> Unit,
    onDelete: () -> Unit,
) {
    if (state.readOnly) Text(stringResource(R.string.note_readonly))
    NoteSavedDetails(state.savedAt, state.savedCount)
    OutlinedTextField(
        value = state.text,
        onValueChange = onText,
        modifier = Modifier.fillMaxWidth().testTag("note-text"),
        label = { Text(stringResource(R.string.note_text)) },
        readOnly = state.readOnly || state.saving,
        minLines = 8,
        isError = !ProjectNoteRules.withinLimit(state.text),
    )
    if (!state.readOnly) {
        Row(
            Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("note-attach").toggleable(
                value = state.attachCount,
                enabled = !state.saving,
                role = Role.Checkbox,
                onValueChange = onAttach,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(checked = state.attachCount, onCheckedChange = null, enabled = !state.saving)
            Text(stringResource(R.string.note_attach), Modifier.padding(start = RowToolDimens.Space8))
        }
        Text(stringResource(R.string.note_attach_help), style = MaterialTheme.typography.bodySmall)
        if (state.version != null) {
            TextButton(
                onClick = onDelete,
                enabled = !state.saving,
                modifier = Modifier.heightIn(min = 48.dp).testTag("note-delete"),
                colors = destructiveDialogActionColors(),
            ) { Text(stringResource(R.string.action_delete)) }
        }
    }
}

@Composable
internal fun NoteSavedDetails(
    savedAt: Long?,
    savedCount: Long?,
) {
    val configuration = LocalConfiguration.current
    val locale = configuration.locales[0]
    val numberFormat = remember(locale) { NumberFormat.getIntegerInstance(locale) }
    val use24Hour =
        android.text.format.DateFormat
            .is24HourFormat(androidx.compose.ui.platform.LocalContext.current)
    val savedTime =
        com.finnvek.rowtool.ui.screens.history
            .historyTime(savedAt, locale, use24Hour, java.util.TimeZone.getDefault())
    Column {
        if (savedCount !=
            null
        ) {
            Text(stringResource(R.string.note_count, numberFormat.format(savedCount)), style = MaterialTheme.typography.bodySmall)
        }
        if (savedAt !=
            null
        ) {
            Text(stringResource(R.string.note_saved_at, savedTime.orEmpty()), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun NoteEditorError(error: NoteError?) {
    val resource =
        when (error) {
            NoteError.READ -> R.string.note_read_error
            NoteError.WRITE -> R.string.error_database_write
            NoteError.CONFLICT -> R.string.note_conflict
            NoteError.UNAVAILABLE -> R.string.note_unavailable
            NoteError.LENGTH -> R.string.note_length
            null -> return
        }
    val locale = LocalConfiguration.current.locales[0]
    Text(
        stringResource(resource, NumberFormat.getIntegerInstance(locale).format(ProjectNoteRules.MAX_CODE_POINTS)),
        color = MaterialTheme.colorScheme.error,
    )
}

@Composable
private fun NoteAction(
    label: Int,
    onClick: () -> Unit,
) {
    TextButton(onClick, modifier = Modifier.heightIn(min = 48.dp), colors = ordinaryDialogActionColors()) { Text(stringResource(label)) }
}

@Composable
private fun NoteConfirmationDialog(
    state: NoteEditorState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    val confirmation = state.confirmation ?: return
    val title =
        when (confirmation) {
            NoteConfirmation.DELETE -> R.string.note_delete_title
            NoteConfirmation.DISCARD -> R.string.note_discard_title
            NoteConfirmation.RELOAD -> R.string.note_reload
        }
    val message =
        when (confirmation) {
            NoteConfirmation.DELETE -> R.string.note_delete_message
            NoteConfirmation.DISCARD -> R.string.note_discard_message
            NoteConfirmation.RELOAD -> R.string.note_reload_message
        }
    val action =
        when (confirmation) {
            NoteConfirmation.DELETE -> R.string.action_delete
            NoteConfirmation.DISCARD -> R.string.note_discard
            NoteConfirmation.RELOAD -> R.string.note_reload
        }
    RowToolConfirmationDialog(
        title = stringResource(title),
        message = stringResource(message),
        confirmLabel = stringResource(action),
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        isDestructive = confirmation != NoteConfirmation.RELOAD,
        enabled = !state.saving,
    )
}

private data class NoteEditorActions(
    val onExit: () -> Unit,
    val onText: (String) -> Unit,
    val onAttach: (Boolean) -> Unit,
    val onDelete: () -> Unit,
    val onRetry: () -> Unit,
    val onReload: () -> Unit,
    val onSave: () -> Unit,
)
