package com.finnvek.rowtool.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.finnvek.rowtool.R

@Composable
internal fun RowToolConfirmationDialog(
    title: String,
    message: String,
    confirmLabel: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    isDestructive: Boolean = false,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors =
                    if (isDestructive) {
                        destructiveDialogActionColors()
                    } else {
                        ordinaryDialogActionColors()
                    },
            ) {
                Text(confirmLabel)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ordinaryDialogActionColors(),
            ) {
                Text(stringResource(R.string.action_cancel))
            }
        },
    )
}
