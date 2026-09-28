package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable

@Composable
internal fun editorTextFieldColors() =
    OutlinedTextFieldDefaults.colors(
        focusedLabelColor = MaterialTheme.colorScheme.secondary,
        focusedBorderColor = MaterialTheme.colorScheme.secondary,
        cursorColor = MaterialTheme.colorScheme.secondary,
        errorContainerColor = MaterialTheme.colorScheme.errorContainer,
        errorTextColor = MaterialTheme.colorScheme.onErrorContainer,
        errorLabelColor = MaterialTheme.colorScheme.onErrorContainer,
        errorSupportingTextColor = MaterialTheme.colorScheme.onErrorContainer,
        errorBorderColor = MaterialTheme.colorScheme.onErrorContainer,
        errorCursorColor = MaterialTheme.colorScheme.onErrorContainer,
    )
