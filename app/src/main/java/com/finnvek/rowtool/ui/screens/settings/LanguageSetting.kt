package com.finnvek.rowtool.ui.screens.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.finnvek.rowtool.R
import com.finnvek.rowtool.ui.ordinaryDialogActionColors
import com.finnvek.rowtool.ui.theme.RowToolDimens
import com.finnvek.rowtool.widget.requestWidgetUpdate

@Composable
internal fun LanguageSetting() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    var actual by remember { mutableStateOf(AppCompatDelegate.getApplicationLocales().toLanguageTags()) }
    var pending by rememberSaveable { mutableStateOf<String?>(null) }
    var failed by rememberSaveable { mutableStateOf(false) }
    var refreshFailed by rememberSaveable { mutableStateOf(false) }
    LifecycleResumeEffect(Unit) {
        actual = AppCompatDelegate.getApplicationLocales().toLanguageTags()
        onPauseOrDispose { }
    }
    LaunchedEffect(configuration) { actual = AppCompatDelegate.getApplicationLocales().toLanguageTags() }
    val systemLabel = stringResource(R.string.language_system)
    ActionRow(
        title = stringResource(R.string.settings_language),
        summary = if (actual.isEmpty()) systemLabel else AppLanguages.label(actual),
        onClick = {
            actual = AppCompatDelegate.getApplicationLocales().toLanguageTags()
            pending = actual
            failed = false
        },
    )
    if (refreshFailed) Text(stringResource(R.string.language_widget_error), color = MaterialTheme.colorScheme.error)
    pending?.let { choice ->
        LanguageDialog(
            actual = actual,
            pending = choice,
            failed = failed,
            onSelect = {
                pending = it
                failed = false
            },
            onDismiss = { pending = null },
            onApply = {
                // Clear the saved dialog request before a setter can recreate this Activity.
                pending = null
                val changed =
                    try {
                        applyLanguageSelection(
                            choice,
                            { AppCompatDelegate.getApplicationLocales().toLanguageTags() },
                            { AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(it)) },
                        )
                    } catch (_: IllegalStateException) {
                        pending = choice
                        failed = true
                        false
                    } catch (_: IllegalArgumentException) {
                        pending = choice
                        failed = true
                        false
                    }
                actual = AppCompatDelegate.getApplicationLocales().toLanguageTags()
                if (changed) {
                    // A refresh failure does not undo or resubmit an accepted locale request.
                    refreshFailed =
                        try {
                            requestWidgetUpdate(context)
                            false
                        } catch (_: IllegalStateException) {
                            true
                        }
                }
            },
        )
    }
}

@Composable
internal fun LanguageDialog(
    actual: String,
    pending: String,
    failed: Boolean,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit,
    onApply: () -> Unit,
) {
    val choices = (listOf("") + AppLanguages.names.keys + listOf(actual, pending)).distinct()
    AlertDialog(
        modifier = Modifier.testTag("language-dialog"),
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.settings_language)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space8)) {
                if (failed) Text(stringResource(R.string.language_error), color = MaterialTheme.colorScheme.error)
                LazyColumn(Modifier.heightIn(max = 320.dp).selectableGroup()) {
                    items(choices, key = { it }) { tag ->
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .selectable(selected = tag == pending, role = Role.RadioButton, onClick = { onSelect(tag) })
                                    .heightIn(min = RowToolDimens.MinimumTouchSize)
                                    .padding(vertical = RowToolDimens.Space8),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(RowToolDimens.Space12),
                        ) {
                            RadioButton(selected = tag == pending, onClick = null)
                            Text(if (tag.isEmpty()) stringResource(R.string.language_system) else AppLanguages.label(tag))
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onApply,
                modifier = Modifier.heightIn(min = 48.dp).testTag("language-apply"),
                colors = ordinaryDialogActionColors(),
            ) {
                Text(stringResource(R.string.language_apply))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, modifier = Modifier.heightIn(min = 48.dp), colors = ordinaryDialogActionColors()) {
                Text(stringResource(R.string.action_cancel))
            }
        },
    )
}
