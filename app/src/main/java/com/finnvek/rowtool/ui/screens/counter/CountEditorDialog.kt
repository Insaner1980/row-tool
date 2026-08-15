package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.foundation.text.KeyboardOptions
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

@Composable
fun CountEditorDialog(
    currentCount: Long,
    onDismiss: () -> Unit,
    onSave: (Long) -> Unit,
) {
    var value by rememberSaveable(currentCount, stateSaver = TextFieldValue.Saver) {
        val text = currentCount.toString()
        mutableStateOf(TextFieldValue(text, selection = TextRange(0, text.length)))
    }
    val focusRequester = androidx.compose.runtime.remember { FocusRequester() }
    val parsed = value.text.toLongOrNull()
    val valid = parsed != null && parsed in 0..CounterConstants.MAX_COUNT

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.counter_set_title)) },
        text = {
            OutlinedTextField(
                value = value,
                onValueChange = { newValue ->
                    if (newValue.text.all(Char::isDigit) && newValue.text.length <= 6) {
                        value = newValue
                    }
                },
                label = { Text(stringResource(R.string.counter_set_label)) },
                singleLine = true,
                isError = !valid,
                supportingText = {
                    if (!valid) Text(stringResource(R.string.counter_set_error))
                },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                modifier = Modifier.focusRequester(focusRequester),
            )
        },
        confirmButton = {
            TextButton(
                enabled = valid,
                onClick = { parsed?.let(onSave) },
            ) {
                Text(stringResource(R.string.action_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.action_cancel))
            }
        },
    )
}
