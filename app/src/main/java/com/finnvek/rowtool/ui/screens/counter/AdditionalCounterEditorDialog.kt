package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.AdditionalCounter
import com.finnvek.rowtool.domain.model.ProjectValidation
import com.finnvek.rowtool.domain.model.ProjectValidationError
import com.finnvek.rowtool.ui.ordinaryDialogActionColors
import com.finnvek.rowtool.ui.theme.RowToolDimens

@Composable
internal fun AdditionalCounterEditorDialog(
    counter: AdditionalCounter?,
    isSaving: Boolean,
    saveFailed: Boolean,
    onDismiss: () -> Unit,
    onSave: (String, Boolean) -> Unit,
) {
    var name by rememberSaveable(counter?.id) { mutableStateOf(counter?.name.orEmpty()) }
    var followsMain by rememberSaveable(counter?.id) { mutableStateOf(counter?.followsMain ?: false) }
    val errors = ProjectValidation.nameErrors(name)
    AlertDialog(
        modifier = Modifier.imePadding(),
        onDismissRequest = { if (!isSaving) onDismiss() },
        title = { Text(stringResource(if (counter == null) R.string.additional_add else R.string.additional_edit)) },
        text = {
            Column(
                Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space16),
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    enabled = !isSaving,
                    label = { Text(stringResource(R.string.additional_name)) },
                    isError = errors.isNotEmpty(),
                    colors = editorTextFieldColors(),
                    supportingText = {
                        when {
                            ProjectValidationError.NAME_TOO_LONG in errors -> {
                                Text(stringResource(R.string.project_name_too_long, ProjectValidation.MAX_NAME_CODE_POINTS))
                            }

                            ProjectValidationError.NAME_BLANK in errors -> {
                                Text(stringResource(R.string.additional_name_required))
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .heightIn(min = RowToolDimens.MinimumTouchSize)
                            .toggleable(
                                value = followsMain,
                                enabled = !isSaving,
                                role = Role.Switch,
                                onValueChange = { followsMain = it },
                            ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        stringResource(R.string.additional_follow),
                        modifier = Modifier.weight(1f).padding(end = RowToolDimens.Space8),
                    )
                    Switch(checked = followsMain, onCheckedChange = null, enabled = !isSaving)
                }
                if (saveFailed) Text(stringResource(R.string.error_database_write))
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(ProjectValidation.normalizeName(name), followsMain) },
                enabled = errors.isEmpty() && !isSaving,
                colors = ordinaryDialogActionColors(),
            ) { Text(stringResource(R.string.action_save)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !isSaving, colors = ordinaryDialogActionColors()) {
                Text(stringResource(R.string.action_cancel))
            }
        },
    )
}
