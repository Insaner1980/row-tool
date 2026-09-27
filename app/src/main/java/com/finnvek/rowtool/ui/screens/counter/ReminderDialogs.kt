package com.finnvek.rowtool.ui.screens.counter

import android.database.SQLException
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.Reminder
import com.finnvek.rowtool.domain.model.ReminderRules
import com.finnvek.rowtool.ui.RowToolConfirmationDialog
import com.finnvek.rowtool.ui.ordinaryDialogActionColors
import com.finnvek.rowtool.ui.theme.RowToolDimens
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalLayoutApi::class)
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
                try {
                    if (operation()) onSuccess() else failed = true
                } catch (_: SQLException) {
                    failed = true
                } finally {
                    saving = false
                }
            }
        }
    }
    if (page in listOf("reset", "delete") && selected != null) {
        val deleting = page == "delete"
        RowToolConfirmationDialog(
            title = stringResource(if (deleting) R.string.reminder_delete_title else R.string.reminder_reset_title),
            message =
                stringResource(if (deleting) R.string.reminder_delete_message else R.string.reminder_reset_message) +
                    if (failed) "\n\n" + stringResource(R.string.error_database_write) else "",
            confirmLabel = stringResource(if (deleting) R.string.action_delete else R.string.action_reset),
            isDestructive = deleting,
            enabled = !saving,
            onDismiss = { if (!saving) page = "list" },
            onConfirm = {
                operate(
                    {
                        if (deleting) {
                            actions.onDelete(project.id, selected.id, selected.revision)
                        } else {
                            actions.onReset(project.id, selected.id, selected.revision)
                        }
                    },
                    { page = "list" },
                )
            },
        )
        return
    }

    val windowHeight = LocalWindowInfo.current.containerSize.height
    val maxHeight = with(LocalDensity.current) { windowHeight.toDp() * 0.88f }
    Dialog(onDismissRequest = { if (!saving) onDismiss() }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(
            modifier =
                Modifier
                    .fillMaxWidth(0.92f)
                    .widthIn(max = 560.dp)
                    .heightIn(max = maxHeight)
                    .imePadding(),
            shape = AlertDialogDefaults.shape,
            color = AlertDialogDefaults.containerColor,
            contentColor = AlertDialogDefaults.textContentColor,
            tonalElevation = AlertDialogDefaults.TonalElevation,
        ) {
            Column(
                Modifier.verticalScroll(rememberScrollState()).padding(RowToolDimens.Space24),
                verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space16),
            ) {
                if (page == "editor" && (selectedId == null || selected != null)) {
                    ReminderEditor(
                        project = project,
                        reminder = selected,
                        message = draftMessage,
                        onMessageChange = { draftMessage = it },
                        firstText = draftFirst,
                        onFirstChange = { draftFirst = it },
                        intervalText = draftInterval,
                        onIntervalChange = { draftInterval = it },
                        saving = saving,
                        failed = failed,
                        onCancel = { page = "list" },
                    ) { message, first, interval ->
                        operate(
                            {
                                actions.onSave(
                                    project.id,
                                    selected?.id,
                                    selected?.revision,
                                    message,
                                    first,
                                    interval,
                                    selected?.enabled ?: true,
                                    if (selected == null) creationId else null,
                                )
                            },
                            { page = "list" },
                        )
                    }
                } else {
                    Text(stringResource(R.string.reminder_title), style = MaterialTheme.typography.headlineSmall)
                    if (reminders.isEmpty()) Text(stringResource(R.string.reminder_empty))
                    reminders.forEach { reminder ->
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
                            Text(
                                pluralStringResource(
                                    R.plurals.reminder_earlier,
                                    status.earlierUnacknowledged.toInt(),
                                    status.earlierUnacknowledged,
                                ),
                            )
                        }
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(RowToolDimens.Space8)) {
                            if (status.dueCount != null) {
                                TextButton(
                                    onClick = {
                                        operate(
                                            { actions.onAcknowledge(project.id, reminder.id, reminder.revision, status.dueCount) },
                                            {},
                                        )
                                    },
                                    enabled = !saving,
                                    modifier = Modifier.heightIn(min = 48.dp),
                                    colors = ordinaryDialogActionColors(),
                                ) {
                                    Text(
                                        stringResource(
                                            if (reminder.intervalCount ==
                                                null
                                            ) {
                                                R.string.reminder_ack
                                            } else {
                                                R.string.reminder_ack_through
                                            },
                                        ),
                                    )
                                }
                            }
                            ReminderAction(R.string.action_edit, !saving) {
                                selectedId = reminder.id
                                draftMessage = reminder.message
                                draftFirst = reminder.firstCount.toString()
                                draftInterval = reminder.intervalCount?.toString().orEmpty()
                                failed = false
                                page = "editor"
                            }
                            ReminderAction(if (reminder.enabled) R.string.reminder_disable else R.string.reminder_enable, !saving) {
                                operate(
                                    {
                                        actions.onSave(
                                            project.id,
                                            reminder.id,
                                            reminder.revision,
                                            reminder.message,
                                            reminder.firstCount,
                                            reminder.intervalCount,
                                            !reminder.enabled,
                                            null,
                                        )
                                    },
                                    {},
                                )
                            }
                            ReminderAction(R.string.action_reset, !saving) {
                                selectedId = reminder.id
                                failed = false
                                page = "reset"
                            }
                            ReminderAction(R.string.action_delete, !saving) {
                                selectedId = reminder.id
                                failed = false
                                page = "delete"
                            }
                        }
                    }
                    if (failed) Text(stringResource(R.string.error_database_write), color = MaterialTheme.colorScheme.error)
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(RowToolDimens.Space8)) {
                        ReminderAction(R.string.action_cancel, !saving, onDismiss)
                        ReminderAction(R.string.reminder_add, !saving) {
                            selectedId = null
                            creationId = UUID.randomUUID().toString()
                            draftMessage = ""
                            draftFirst = (project.count + 1).coerceAtMost(CounterConstants.MAX_COUNT).toString()
                            draftInterval = ""
                            failed = false
                            page = "editor"
                        }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun ReminderEditor(
    project: CounterProject,
    reminder: Reminder?,
    message: String,
    onMessageChange: (String) -> Unit,
    firstText: String,
    onFirstChange: (String) -> Unit,
    intervalText: String,
    onIntervalChange: (String) -> Unit,
    saving: Boolean,
    failed: Boolean,
    onCancel: () -> Unit,
    onSave: (String, Long, Long?) -> Unit,
) {
    val first = firstText.toLongOrNull()
    val interval = if (intervalText.isBlank()) null else intervalText.toLongOrNull()
    val valid = first != null && (intervalText.isBlank() || interval != null) && ReminderRules.validate(message, first, interval)
    val scheduleChanged = reminder != null && (reminder.firstCount != first || reminder.intervalCount != interval)

    val titleRes = if (reminder == null) R.string.reminder_add else R.string.reminder_edit
    val firstLabelRes = if (project.counterUnit == CounterUnit.ROWS) R.string.reminder_first_row else R.string.reminder_first_round
    Column(verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space16)) {
        Text(stringResource(titleRes), style = MaterialTheme.typography.headlineSmall)
        Text(stringResource(R.string.reminder_explanation), style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(
            value = message,
            onValueChange = onMessageChange,
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
            enabled = !saving,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = firstText,
            onValueChange = onFirstChange,
            label = { Text(stringResource(firstLabelRes)) },
            supportingText = {
                if (first == null || first !in 1..CounterConstants.MAX_COUNT) {
                    Text(stringResource(R.string.reminder_count_error, CounterConstants.MAX_COUNT))
                }
            },
            isError = firstText.isNotEmpty() && (first == null || first !in 1..CounterConstants.MAX_COUNT),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = !saving,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = intervalText,
            onValueChange = onIntervalChange,
            label = { Text(stringResource(R.string.reminder_interval)) },
            supportingText = {
                if (intervalText.isNotBlank() && (interval == null || interval !in 1..CounterConstants.MAX_COUNT)) {
                    Text(stringResource(R.string.reminder_count_error, CounterConstants.MAX_COUNT))
                }
            },
            isError = intervalText.isNotBlank() && (interval == null || interval !in 1..CounterConstants.MAX_COUNT),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = !saving,
            modifier = Modifier.fillMaxWidth(),
        )
        if (scheduleChanged && reminder.acknowledgedThrough != null) {
            Text(stringResource(R.string.reminder_schedule_reset), style = MaterialTheme.typography.bodySmall)
        }
        if (failed) Text(stringResource(R.string.error_database_write), color = MaterialTheme.colorScheme.error)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(RowToolDimens.Space8)) {
            ReminderAction(R.string.action_cancel, !saving, onCancel)
            ReminderAction(R.string.action_save, valid && !saving) { onSave(message, first!!, interval) }
        }
    }
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
