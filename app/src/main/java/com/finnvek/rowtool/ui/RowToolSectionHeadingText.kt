package com.finnvek.rowtool.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

@Composable
internal fun RowToolSectionHeadingText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text.uppercase(LocalConfiguration.current.locales[0]),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier,
    )
}
