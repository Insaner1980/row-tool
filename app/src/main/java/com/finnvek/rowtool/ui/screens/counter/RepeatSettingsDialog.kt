package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.ui.EditorDialogFrame
import com.finnvek.rowtool.ui.ordinaryDialogActionColors
import com.finnvek.rowtool.ui.screens.projects.RepeatSettingsFields
import com.finnvek.rowtool.ui.screens.projects.validateRepeatSettingsInput
import com.finnvek.rowtool.ui.theme.RowToolDimens
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun RepeatSettingsDialog(
    project: CounterProject,
    onDismiss: () -> Unit,
    onSave: suspend (String, Int?, Long?) -> Boolean,
) {
    var enabled by rememberSaveable(project.id) { mutableStateOf(project.repeatLength != null) }
    var lengthText by rememberSaveable(project.id) { mutableStateOf(project.repeatLength?.toString().orEmpty()) }
    var startText by rememberSaveable(project.id) { mutableStateOf((project.repeatStartCount ?: 1L).toString()) }
    var saving by remember { mutableStateOf(false) }
    var saveFailed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val validation = validateRepeatSettingsInput(enabled, lengthText, startText)
    val save: () -> Unit = {
        saving = true
        saveFailed = false
        scope.launch {
            if (onSave(project.id, validation.repeatLength, validation.repeatStartCount)) {
                onDismiss()
            } else {
                saveFailed = true
                saving = false
            }
        }
    }
    EditorDialogFrame(onDismissRequest = { if (!saving) onDismiss() }) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()).padding(RowToolDimens.Space24),
            verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space16),
        ) {
            Text(stringResource(R.string.repeat_editor_title), style = MaterialTheme.typography.headlineSmall)
            RepeatSettingsFields(
                counterUnit = project.counterUnit,
                enabled = enabled,
                onEnabledChange = { enabled = it },
                lengthText = lengthText,
                onLengthChange = { lengthText = it },
                startText = startText,
                onStartChange = { startText = it },
            )
            RepeatNextCount(project, enabled, saving) { startText = (project.count + 1).toString() }
            if (saveFailed) {
                Text(stringResource(R.string.error_database_write), color = MaterialTheme.colorScheme.error)
            }
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RowToolDimens.Space8),
                verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space8),
            ) {
                TextButton(
                    onClick = onDismiss,
                    enabled = !saving,
                    colors = ordinaryDialogActionColors(),
                    modifier = Modifier.heightIn(min = 48.dp),
                ) {
                    Text(stringResource(R.string.action_cancel))
                }
                TextButton(
                    onClick = save,
                    enabled = validation.canSave && !saving,
                    colors = ordinaryDialogActionColors(),
                    modifier = Modifier.heightIn(min = 48.dp),
                ) {
                    Text(stringResource(R.string.action_save))
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.RepeatNextCount(
    project: CounterProject,
    enabled: Boolean,
    saving: Boolean,
    onNext: () -> Unit,
) {
    if (enabled) {
        TextButton(
            onClick = { onNext() },
            enabled = !saving && project.count < CounterConstants.MAX_COUNT,
            colors = ordinaryDialogActionColors(),
            modifier = Modifier.heightIn(min = 48.dp),
        ) {
            Text(stringResource(R.string.repeat_next_count))
        }
        if (project.count == CounterConstants.MAX_COUNT) {
            Text(stringResource(R.string.repeat_next_unavailable), style = MaterialTheme.typography.bodySmall)
        }
    }
}
