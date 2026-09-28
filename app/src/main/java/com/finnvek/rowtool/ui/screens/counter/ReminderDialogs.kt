package com.finnvek.rowtool.ui.screens.counter

import android.database.SQLException
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.Reminder
import com.finnvek.rowtool.domain.model.ReminderRules
import com.finnvek.rowtool.domain.model.ReminderValues
import com.finnvek.rowtool.ui.EditorDialogFrame
import com.finnvek.rowtool.ui.RowToolConfirmationDialog
import com.finnvek.rowtool.ui.ordinaryDialogActionColors
import com.finnvek.rowtool.ui.theme.RowToolDimens
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
internal fun ReminderDialogs(
    project: CounterProject,
    reminders: List<Reminder>,
    actions: ReminderActions,
    onDismiss: () -> Unit,
) {
    var page by rememberSaveable(project.id) { mutableStateOf("list") }
    var selectedId by rememberSaveable(project.id) { mutableStateOf<String?>(null) }
    var creationId by rememberSaveable(project.id) { mutableStateOf(UUID.randomUUID().toString()) }
    var saving by remember { mutableStateOf(false) }
    var failed by rememberSaveable(project.id) { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val selected = reminders.firstOrNull { it.id == selectedId }
    var draftMessage by rememberSaveable(project.id, selectedId) { mutableStateOf(selected?.message.orEmpty()) }
    var draftFirst by rememberSaveable(project.id, selectedId) {
        mutableStateOf((selected?.firstCount ?: (project.count + 1).coerceAtMost(CounterConstants.MAX_COUNT)).toString())
    }
    var draftInterval by rememberSaveable(project.id, selectedId) { mutableStateOf(selected?.intervalCount?.toString().orEmpty()) }
    val operate: (suspend () -> Boolean, () -> Unit) -> Unit = { operation, onSuccess ->
        if (!saving) {
            saving = true
            failed = false
            scope.launch {
                runReminderOperation(operation, onSuccess, { failed = true }, { saving = false })
            }
        }
    }
    val closePage = { page = "list" }
    if (page in listOf("reset", "delete") && selected != null) {
        ReminderConfirmation(page == "delete", saving, failed, closePage) { deleting ->
            operate(
                { changeReminder(actions, project.id, selected, deleting) },
                closePage,
            )
        }
        return
    }
    val listActions =
        ReminderListActions(
            onEdit = { reminder ->
                selectedId = reminder.id
                draftMessage = reminder.message
                draftFirst = reminder.firstCount.toString()
                draftInterval = reminder.intervalCount?.toString().orEmpty()
                failed = false
                page = "editor"
            },
            onToggle = { reminder ->
                operate({
                    actions.onSave(
                        project.id,
                        reminder.id,
                        reminder.revision,
                        ReminderValues(reminder.message, reminder.firstCount, reminder.intervalCount, !reminder.enabled),
                        null,
                    )
                }, {})
            },
            onAcknowledge = { reminder, count ->
                operate({ actions.onAcknowledge(project.id, reminder.id, reminder.revision, count) }, {})
            },
            onConfirm = { reminder, nextPage ->
                selectedId = reminder.id
                failed = false
                page = nextPage
            },
            onAdd = {
                selectedId = null
                creationId = UUID.randomUUID().toString()
                draftMessage = ""
                draftFirst = (project.count + 1).coerceAtMost(CounterConstants.MAX_COUNT).toString()
                draftInterval = ""
                failed = false
                page = "editor"
            },
            onDismiss = onDismiss,
        )
    val showEditor = page == "editor" && (selectedId == null || selected != null)
    val editorFields =
        ReminderEditorFields(
            draftMessage,
            { draftMessage = it },
            draftFirst,
            { draftFirst = it },
            draftInterval,
            { draftInterval = it },
        )
    val save: (String, Long, Long?) -> Unit = { message, first, interval ->
        val values = ReminderValues(message, first, interval, selected?.enabled ?: true)
        operate({
            actions.onSave(
                project.id,
                selected?.id,
                selected?.revision,
                values,
                creationId.takeIf { selected == null },
            )
        }, closePage)
    }
    EditorDialogFrame(onDismissRequest = { if (!saving) onDismiss() }, capWidth = false) {
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(RowToolDimens.Space24),
            verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space16),
        ) {
            if (showEditor) {
                ReminderEditor(project, selected, editorFields, saving, failed, closePage, save)
            } else {
                ReminderList(project, reminders, saving, failed, listActions)
            }
        }
    }
}

private suspend fun runReminderOperation(
    operation: suspend () -> Boolean,
    onSuccess: () -> Unit,
    onFailure: () -> Unit,
    onFinished: () -> Unit,
) {
    try {
        if (operation()) onSuccess() else onFailure()
    } catch (_: SQLException) {
        onFailure()
    } finally {
        onFinished()
    }
}

private suspend fun changeReminder(
    actions: ReminderActions,
    projectId: String,
    reminder: Reminder,
    deleting: Boolean,
): Boolean =
    if (deleting) {
        actions.onDelete(projectId, reminder.id, reminder.revision)
    } else {
        actions.onReset(projectId, reminder.id, reminder.revision)
    }

@Composable
private fun ReminderConfirmation(
    deleting: Boolean,
    saving: Boolean,
    failed: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (Boolean) -> Unit,
) {
    RowToolConfirmationDialog(
        title = stringResource(if (deleting) R.string.reminder_delete_title else R.string.reminder_reset_title),
        message =
            stringResource(if (deleting) R.string.reminder_delete_message else R.string.reminder_reset_message) +
                if (failed) "\n\n" + stringResource(R.string.error_database_write) else "",
        confirmLabel = stringResource(if (deleting) R.string.action_delete else R.string.action_reset),
        isDestructive = deleting,
        enabled = !saving,
        onDismiss = { if (!saving) onDismiss() },
        onConfirm = { onConfirm(deleting) },
    )
}

private data class ReminderListActions(
    val onEdit: (Reminder) -> Unit,
    val onToggle: (Reminder) -> Unit,
    val onAcknowledge: (Reminder, Long) -> Unit,
    val onConfirm: (Reminder, String) -> Unit,
    val onAdd: () -> Unit,
    val onDismiss: () -> Unit,
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColumnScope.ReminderList(
    project: CounterProject,
    reminders: List<Reminder>,
    saving: Boolean,
    failed: Boolean,
    actions: ReminderListActions,
) {
    Text(stringResource(R.string.reminder_title), style = MaterialTheme.typography.headlineSmall)
    if (reminders.isEmpty()) Text(stringResource(R.string.reminder_empty))
    reminders.forEach { reminder -> ReminderRow(project, reminder, saving, actions) }
    if (failed) Text(stringResource(R.string.error_database_write), color = MaterialTheme.colorScheme.error)
    FlowRow(horizontalArrangement = Arrangement.spacedBy(RowToolDimens.Space8)) {
        ReminderAction(R.string.action_cancel, !saving, actions.onDismiss)
        ReminderAction(R.string.reminder_add, !saving, actions.onAdd)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColumnScope.ReminderRow(
    project: CounterProject,
    reminder: Reminder,
    saving: Boolean,
    actions: ReminderListActions,
) {
    val status = ReminderRules.status(reminder, project.count)
    HorizontalDivider()
    Text(reminder.message, style = MaterialTheme.typography.bodyLarge)
    Text(
        when {
            !reminder.enabled -> stringResource(R.string.reminder_disabled)
            status.dueCount != null -> reminderCountLabel(project.counterUnit, status.dueCount, true)
            status.nextCount != null -> reminderCountLabel(project.counterUnit, status.nextCount, false)
            else -> stringResource(R.string.reminder_acknowledged)
        },
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    if (status.earlierUnacknowledged > 0) {
        Text(pluralStringResource(R.plurals.reminder_earlier, status.earlierUnacknowledged.toInt(), status.earlierUnacknowledged))
    }
    FlowRow(horizontalArrangement = Arrangement.spacedBy(RowToolDimens.Space8)) {
        if (status.dueCount != null) {
            ReminderAction(if (reminder.intervalCount == null) R.string.reminder_ack else R.string.reminder_ack_through, !saving) {
                actions.onAcknowledge(reminder, status.dueCount)
            }
        }
        ReminderAction(R.string.action_edit, !saving) { actions.onEdit(reminder) }
        ReminderAction(
            if (reminder.enabled) R.string.reminder_disable else R.string.reminder_enable,
            !saving,
        ) { actions.onToggle(reminder) }
        ReminderAction(R.string.action_reset, !saving) { actions.onConfirm(reminder, "reset") }
        ReminderAction(R.string.action_delete, !saving) { actions.onConfirm(reminder, "delete") }
    }
}

private data class ReminderEditorFields(
    val message: String,
    val onMessageChange: (String) -> Unit,
    val firstText: String,
    val onFirstChange: (String) -> Unit,
    val intervalText: String,
    val onIntervalChange: (String) -> Unit,
)

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun ReminderEditor(
    project: CounterProject,
    reminder: Reminder?,
    fields: ReminderEditorFields,
    saving: Boolean,
    failed: Boolean,
    onCancel: () -> Unit,
    onSave: (String, Long, Long?) -> Unit,
) {
    val first = fields.firstText.toLongOrNull()
    val interval = fields.intervalText.takeUnless(String::isBlank)?.toLongOrNull()
    val valid =
        first != null && (fields.intervalText.isBlank() || interval != null) && ReminderRules.validate(fields.message, first, interval)
    val scheduleChanged = reminder != null && (reminder.firstCount != first || reminder.intervalCount != interval)
    val firstLabel = if (project.counterUnit == CounterUnit.ROWS) R.string.reminder_first_row else R.string.reminder_first_round
    Column(verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space16)) {
        Text(
            stringResource(
                if (reminder ==
                    null
                ) {
                    R.string.reminder_add
                } else {
                    R.string.reminder_edit
                },
            ),
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(stringResource(R.string.reminder_explanation), style = MaterialTheme.typography.bodyMedium)
        ReminderMessageField(fields.message, fields.onMessageChange, !saving)
        ReminderCountField(fields.firstText, fields.onFirstChange, firstLabel, !saving, optional = false)
        ReminderCountField(fields.intervalText, fields.onIntervalChange, R.string.reminder_interval, !saving, optional = true)
        if (scheduleChanged && reminder.acknowledgedThrough != null) {
            Text(stringResource(R.string.reminder_schedule_reset), style = MaterialTheme.typography.bodySmall)
        }
        if (failed) Text(stringResource(R.string.error_database_write), color = MaterialTheme.colorScheme.error)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(RowToolDimens.Space8)) {
            ReminderAction(R.string.action_cancel, !saving, onCancel)
            ReminderAction(R.string.action_save, valid && !saving) { onSave(fields.message, first!!, interval) }
        }
    }
}

@Composable
private fun ReminderMessageField(
    message: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
) {
    OutlinedTextField(
        value = message,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.reminder_message)) },
        supportingText = {
            val normalized = ReminderRules.normalizeMessage(message)
            when {
                normalized.isEmpty() -> {
                    Text(stringResource(R.string.reminder_message_required))
                }

                normalized.codePointCount(0, normalized.length) > ReminderRules.MAX_MESSAGE_CODE_POINTS -> {
                    Text(stringResource(R.string.reminder_message_length))
                }
            }
        },
        isError = message.isNotEmpty() && !ReminderRules.validate(message, 1, null),
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun ReminderCountField(
    text: String,
    onValueChange: (String) -> Unit,
    label: Int,
    enabled: Boolean,
    optional: Boolean,
) {
    val number = text.toLongOrNull()
    val invalid = number == null || number !in 1..CounterConstants.MAX_COUNT
    val showError = if (optional) text.isNotBlank() && invalid else invalid
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        supportingText = { if (showError) Text(stringResource(R.string.reminder_count_error, CounterConstants.MAX_COUNT)) },
        isError = text.isNotEmpty() && showError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun ReminderAction(
    label: Int,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.heightIn(min = 48.dp),
        colors = ordinaryDialogActionColors(),
    ) { Text(stringResource(label)) }
}

@Composable
internal fun reminderCountLabel(
    unit: CounterUnit,
    count: Long,
    due: Boolean,
): String =
    stringResource(
        when {
            due && unit == CounterUnit.ROWS -> R.string.reminder_due_row
            due -> R.string.reminder_due_round
            unit == CounterUnit.ROWS -> R.string.reminder_next_row
            else -> R.string.reminder_next_round
        },
        count,
    )
