package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.ui.ordinaryDialogActionColors

@Composable
fun CountEditorDialog(
    currentCount: Long,
    onDismiss: () -> Unit,
    onSave: (Long) -> Unit,
    isSaving: Boolean = false,
    saveFailed: Boolean = false,
) {
    var value by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        val text = currentCount.toString()
        mutableStateOf(TextFieldValue(text, selection = TextRange(0, text.length)))
    }
    val focusRequester = androidx.compose.runtime.remember { FocusRequester() }
    val parsed = value.text.toLongOrNull()
    val valid = parsed != null && parsed in CounterConstants.MIN_COUNT..CounterConstants.MAX_COUNT

    AlertDialog(
        modifier = Modifier.imePadding(),
        onDismissRequest = { if (!isSaving) onDismiss() },
        title = { Text(stringResource(R.string.counter_set_title)) },
        text = {
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
            Column(Modifier.verticalScroll(rememberScrollState())) {
                OutlinedTextField(
                    value = value,
                    enabled = !isSaving,
                    onValueChange = { value = it },
                    label = { Text(stringResource(R.string.counter_set_label)) },
                    singleLine = true,
                    isError = !valid,
                    colors = editorTextFieldColors(),
                    supportingText = {
                        if (!valid) Text(stringResource(R.string.counter_set_error, CounterConstants.MIN_COUNT, CounterConstants.MAX_COUNT))
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        ),
                    modifier = Modifier.focusRequester(focusRequester),
                )
                if (saveFailed) Text(stringResource(R.string.error_database_write))
            }
        },
        confirmButton = {
            TextButton(
                enabled = valid && !isSaving,
                colors = ordinaryDialogActionColors(),
                onClick = { parsed?.let(onSave) },
            ) {
                Text(stringResource(R.string.action_save))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isSaving,
                colors = ordinaryDialogActionColors(),
            ) {
                Text(stringResource(R.string.action_cancel))
            }
        },
    )
}
