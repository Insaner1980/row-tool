package com.finnvek.rowtool.ui

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
internal fun ordinaryDialogActionColors() = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)

@Composable
internal fun destructiveDialogActionColors() =
    ButtonDefaults.textButtonColors(
        containerColor = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.onErrorContainer,
    )
