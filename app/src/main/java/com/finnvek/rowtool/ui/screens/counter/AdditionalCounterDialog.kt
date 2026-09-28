package com.finnvek.rowtool.ui.screens.counter

import android.database.SQLException
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.AdditionalCounter
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.ui.RowToolConfirmationDialog
import kotlinx.coroutines.launch

internal enum class AdditionalCounterDialog { ADD, EDIT, COUNT, RESET, DELETE }

@Composable
internal fun AdditionalCounterDialogs(
    dialog: AdditionalCounterDialog,
    counter: AdditionalCounter?,
    actions: AdditionalCounterEditorActions,
    onDismiss: () -> Unit,
) {
    var isSaving by remember { mutableStateOf(false) }
    var saveFailed by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val save: (suspend () -> Boolean) -> Unit = { operation ->
        if (!isSaving) {
            isSaving = true
            saveFailed = false
            scope.launch {
                try {
                    if (operation()) onDismiss() else saveFailed = true
                } catch (_: SQLException) {
                    saveFailed = true
                } finally {
                    isSaving = false
                }
            }
        }
    }
    when (dialog) {
        AdditionalCounterDialog.ADD, AdditionalCounterDialog.EDIT -> {
            AdditionalCounterEditorDialog(
                counter = counter,
                isSaving = isSaving,
                saveFailed = saveFailed,
                onDismiss = onDismiss,
                onSave = { name, followsMain -> save { actions.onSave(counter?.id, name, followsMain) } },
            )
        }

        AdditionalCounterDialog.COUNT -> {
            if (counter != null) {
                CountEditorDialog(
                    currentCount = counter.count,
                    isSaving = isSaving,
                    saveFailed = saveFailed,
                    onDismiss = onDismiss,
                    onSave = { count -> save { actions.onSetCount(counter.id, CounterMutation.ManualSet(count)) } },
                )
            }
        }

        AdditionalCounterDialog.RESET, AdditionalCounterDialog.DELETE -> {
            if (counter != null) {
                AdditionalCounterConfirmation(dialog, counter, actions, isSaving, saveFailed, onDismiss, save)
            }
        }
    }
}

@Composable
private fun AdditionalCounterConfirmation(
    dialog: AdditionalCounterDialog,
    counter: AdditionalCounter,
    actions: AdditionalCounterEditorActions,
    isSaving: Boolean,
    saveFailed: Boolean,
    onDismiss: () -> Unit,
    save: (suspend () -> Boolean) -> Unit,
) {
    val delete = dialog == AdditionalCounterDialog.DELETE
    val message =
        stringResource(
            if (delete) R.string.additional_delete_message else R.string.additional_reset_message,
            counter.name,
        )
    RowToolConfirmationDialog(
        title = stringResource(if (delete) R.string.additional_delete_title else R.string.counter_reset_title),
        message = if (saveFailed) message + "\n\n" + stringResource(R.string.error_database_write) else message,
        confirmLabel = stringResource(if (delete) R.string.action_delete else R.string.action_reset),
        isDestructive = delete,
        enabled = !isSaving,
        onDismiss = onDismiss,
        onConfirm = {
            save {
                if (delete) {
                    actions.onDelete(counter.id)
                } else {
                    actions.onSetCount(counter.id, CounterMutation.Reset)
                }
            }
        },
    )
}
