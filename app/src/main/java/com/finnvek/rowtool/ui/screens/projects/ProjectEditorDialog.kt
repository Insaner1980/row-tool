package com.finnvek.rowtool.ui.screens.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.ProjectValidation
import com.finnvek.rowtool.ui.ordinaryDialogActionColors
import com.finnvek.rowtool.ui.theme.RowToolDimens

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun ProjectEditorDialog(
    project: CounterProject?,
    onDismiss: () -> Unit,
    onSave: (ProjectEditorValues) -> Unit,
    copyState: CopySetupState? = null,
) {
    var name by rememberSaveable(project?.id) { mutableStateOf(project?.name.orEmpty()) }
    var counterUnitName by rememberSaveable(project?.id) {
        mutableStateOf((project?.counterUnit ?: CounterUnit.ROWS).name)
    }
    var startValue by rememberSaveable(project?.id) { mutableIntStateOf(project?.startValue ?: 0) }
    var targetEnabled by rememberSaveable(project?.id) { mutableStateOf(project?.targetCount != null) }
    var targetText by rememberSaveable(project?.id) {
        mutableStateOf(project?.targetCount?.toString().orEmpty())
    }
    var repeatEnabled by rememberSaveable(project?.id) { mutableStateOf(project?.repeatLength != null) }
    var repeatText by rememberSaveable(project?.id) {
        mutableStateOf(project?.repeatLength?.toString().orEmpty())
    }
    var repeatStartText by rememberSaveable(project?.id) {
        mutableStateOf((project?.repeatStartCount ?: 1L).toString())
    }

    var confirmDiscard by rememberSaveable { mutableStateOf(false) }
    val dirty =
        name != project?.name.orEmpty() ||
            counterUnitName != (project?.counterUnit ?: CounterUnit.ROWS).name ||
            startValue != (project?.startValue ?: 0) || targetEnabled != (project?.targetCount != null) ||
            targetText != project?.targetCount?.toString().orEmpty() || repeatEnabled != (project?.repeatLength != null) ||
            repeatText != project?.repeatLength?.toString().orEmpty() ||
            repeatStartText != (project?.repeatStartCount ?: 1L).toString()
    val dismiss = {
        if (copyState?.saving != true) {
            if (copyState != null && dirty) confirmDiscard = true else onDismiss()
        }
    }
    if (confirmDiscard) {
        com.finnvek.rowtool.ui.RowToolConfirmationDialog(
            title = stringResource(R.string.note_discard_title),
            message = stringResource(R.string.note_discard_message),
            confirmLabel = stringResource(R.string.note_discard),
            isDestructive = true,
            onDismiss = { confirmDiscard = false },
            onConfirm = onDismiss,
        )
    }

    val validation =
        validateProjectEditorInput(
            name = name,
            targetEnabled = targetEnabled,
            targetText = targetText,
            repeatEnabled = repeatEnabled,
            repeatText = repeatText,
            repeatStartText = repeatStartText,
        )
    val counterUnit = CounterUnit.valueOf(counterUnitName)
    val windowHeight = LocalWindowInfo.current.containerSize.height
    val maxHeight = with(LocalDensity.current) { windowHeight.toDp() * 0.88f }

    Dialog(
        onDismissRequest = dismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier =
                Modifier
                    .fillMaxWidth(0.92f)
                    // Let the width cap apply after fillMaxWidth sets an exact width.
                    .wrapContentWidth()
                    .widthIn(max = 560.dp)
                    .heightIn(max = maxHeight)
                    .imePadding(),
            shape = AlertDialogDefaults.shape,
            color = AlertDialogDefaults.containerColor,
            contentColor = AlertDialogDefaults.textContentColor,
            tonalElevation = AlertDialogDefaults.TonalElevation,
        ) {
            Column(
                modifier =
                    if (copyState ==
                        null
                    ) {
                        Modifier.verticalScroll(rememberScrollState()).padding(RowToolDimens.Space24)
                    } else {
                        Modifier
                    },
                verticalArrangement = Arrangement.spacedBy(if (copyState == null) RowToolDimens.Space16 else 0.dp),
            ) {
                Column(
                    modifier =
                        Modifier
                            .then(
                                if (copyState !=
                                    null
                                ) {
                                    Modifier.weight(1f, fill = false).verticalScroll(rememberScrollState()).padding(RowToolDimens.Space24)
                                } else {
                                    Modifier
                                },
                            ),
                    verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space16),
                ) {
                    Text(
                        text =
                            stringResource(
                                if (copyState !=
                                    null
                                ) {
                                    R.string.copy_title
                                } else if (project ==
                                    null
                                ) {
                                    R.string.project_new_title
                                } else {
                                    R.string.project_edit_title
                                },
                            ),
                        style = MaterialTheme.typography.headlineSmall,
                        color = AlertDialogDefaults.titleContentColor,
                        modifier = Modifier.semantics { heading() },
                    )
                    copyState?.snapshot?.let { snapshot ->
                        Text(stringResource(R.string.copy_source, snapshot.sourceName))
                        Text(stringResource(R.string.copy_excluded), style = MaterialTheme.typography.bodyMedium)
                        if (snapshot.counters.isNotEmpty()) {
                            Text(stringResource(R.string.additional_counters), style = MaterialTheme.typography.titleSmall)
                            snapshot.counters.forEach { counter ->
                                Text(
                                    counter.name + ": " +
                                        stringResource(
                                            if (counter.followsMain) R.string.additional_following else R.string.additional_manual,
                                        ),
                                )
                            }
                        }
                    }
                    copyState?.error?.let { Text(stringResource(it), color = MaterialTheme.colorScheme.error) }
                    ProjectNameField(
                        value = name,
                        onValueChange = { name = it },
                        validation = validation,
                    )
                    ChoiceSection(
                        label = stringResource(R.string.project_counter_type),
                        options =
                            listOf(
                                CounterUnit.ROWS.name to stringResource(R.string.project_rows),
                                CounterUnit.ROUNDS.name to stringResource(R.string.project_rounds),
                            ),
                        selected = counterUnitName,
                        onSelect = { counterUnitName = it },
                    )
                    ChoiceSection(
                        label = stringResource(R.string.project_starting_value),
                        options =
                            listOf(
                                "0" to stringResource(R.string.project_start_zero),
                                "1" to stringResource(R.string.project_start_one),
                            ),
                        selected = startValue.toString(),
                        onSelect = { startValue = it.toInt() },
                    )
                    ToggleNumberField(
                        checked = targetEnabled,
                        onCheckedChange = { targetEnabled = it },
                        value = targetText,
                        onValueChange = { targetText = it },
                        isValid = validation.targetValid,
                        config =
                            ToggleNumberFieldConfig(
                                switchLabel = stringResource(R.string.project_target_enabled),
                                fieldLabel = stringResource(R.string.project_target_label),
                                errorText =
                                    stringResource(
                                        R.string.project_target_error,
                                        ProjectValidation.MIN_TARGET_COUNT,
                                        CounterConstants.MAX_COUNT,
                                    ),
                                imeAction = ImeAction.Next,
                            ),
                    )
                    RepeatSettingsFields(
                        counterUnit = counterUnit,
                        enabled = repeatEnabled,
                        onEnabledChange = { repeatEnabled = it },
                        lengthText = repeatText,
                        onLengthChange = { repeatText = it },
                        startText = repeatStartText,
                        onStartChange = { repeatStartText = it },
                        validation = validateRepeatSettingsInput(repeatEnabled, repeatText, repeatStartText),
                    )
                }
                FlowRow(
                    modifier =
                        Modifier.fillMaxWidth().then(
                            if (copyState == null) {
                                Modifier.padding(top = RowToolDimens.Space8)
                            } else {
                                Modifier.padding(horizontal = RowToolDimens.Space24, vertical = RowToolDimens.Space8)
                            },
                        ),
                    horizontalArrangement = Arrangement.spacedBy(RowToolDimens.Space8, Alignment.End),
                    verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space12),
                ) {
                    TextButton(
                        onClick = dismiss,
                        modifier = if (copyState != null) Modifier.heightIn(min = 48.dp) else Modifier,
                        enabled = copyState?.saving != true,
                        colors = ordinaryDialogActionColors(),
                    ) {
                        Text(stringResource(R.string.action_cancel))
                    }
                    TextButton(
                        modifier = if (copyState != null) Modifier.heightIn(min = 48.dp) else Modifier,
                        enabled = validation.canSave && copyState?.saving != true && copyState?.completedId == null,
                        colors = ordinaryDialogActionColors(),
                        onClick = {
                            onSave(
                                ProjectEditorValues(
                                    name = validation.name,
                                    counterUnit = counterUnit,
                                    startValue = startValue,
                                    targetCount = validation.targetCount,
                                    repeatLength = validation.repeatLength,
                                    repeatStartCount = validation.repeatStartCount,
                                ),
                            )
                        },
                    ) {
                        Text(stringResource(if (copyState != null) R.string.copy_create else R.string.action_save))
                    }
                }
            }
        }
    }
}

@Composable
internal fun RepeatSettingsFields(
    counterUnit: CounterUnit,
    enabled: Boolean,
    onEnabledChange: (Boolean) -> Unit,
    lengthText: String,
    onLengthChange: (String) -> Unit,
    startText: String,
    onStartChange: (String) -> Unit,
    validation: RepeatSettingsInputValidation,
) {
    ToggleNumberField(
        checked = enabled,
        onCheckedChange = onEnabledChange,
        value = lengthText,
        onValueChange = onLengthChange,
        isValid = validation.repeatValid,
        config =
            ToggleNumberFieldConfig(
                switchLabel = stringResource(R.string.project_repeat_enabled),
                fieldLabel = stringResource(R.string.project_repeat_label),
                errorText =
                    stringResource(
                        R.string.project_repeat_error,
                        ProjectValidation.MIN_REPEAT_LENGTH,
                        ProjectValidation.MAX_REPEAT_LENGTH,
                    ),
                imeAction = ImeAction.Next,
            ),
    )
    if (enabled) {
        TextField(
            value = startText,
            onValueChange = onStartChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    stringResource(
                        if (counterUnit == CounterUnit.ROWS) R.string.repeat_start_row else R.string.repeat_start_round,
                    ),
                )
            },
            singleLine = true,
            isError = !validation.repeatStartValid,
            shape = MaterialTheme.shapes.large,
            colors = projectEditorTextFieldColors(),
            supportingText = {
                Text(
                    if (validation.repeatStartValid) {
                        stringResource(R.string.repeat_start_explanation)
                    } else {
                        stringResource(R.string.repeat_start_error, CounterConstants.MAX_COUNT)
                    },
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        )
    }
}

@Composable
private fun ProjectNameField(
    value: String,
    onValueChange: (String) -> Unit,
    validation: ProjectEditorInputValidation,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.project_name_label)) },
        singleLine = true,
        isError = !validation.nameValid,
        shape = MaterialTheme.shapes.large,
        colors = projectEditorTextFieldColors(),
        supportingText = {
            if (validation.name.isEmpty()) {
                Text(stringResource(R.string.project_name_required))
            } else if (!validation.nameValid) {
                Text(stringResource(R.string.project_name_too_long, ProjectValidation.MAX_NAME_CODE_POINTS))
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun ChoiceSection(
    label: String,
    options: List<Pair<String, String>>,
    selected: String,
    onSelect: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space8)) {
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(RowToolDimens.Space8),
            verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space8),
        ) {
            options.forEach { (value, optionLabel) ->
                FilterChip(
                    selected = selected == value,
                    onClick = { onSelect(value) },
                    label = { Text(optionLabel) },
                )
            }
        }
    }
}

@Composable
private fun ToggleNumberField(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    isValid: Boolean,
    config: ToggleNumberFieldConfig,
) {
    Column(verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space8)) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .toggleable(
                        value = checked,
                        role = Role.Switch,
                        onValueChange = onCheckedChange,
                    ).padding(vertical = RowToolDimens.Space8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(RowToolDimens.Space12),
        ) {
            Text(
                text = config.switchLabel,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
            )
            Switch(checked = checked, onCheckedChange = null)
        }
        if (checked) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(config.fieldLabel) },
                singleLine = true,
                isError = !isValid,
                shape = MaterialTheme.shapes.large,
                colors = projectEditorTextFieldColors(),
                supportingText = {
                    if (!isValid) Text(config.errorText)
                },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = config.imeAction,
                    ),
            )
        }
    }
}

@Composable
private fun projectEditorTextFieldColors() =
    TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        // Primary text has insufficient contrast on this dialog in both themes.
        focusedLabelColor = MaterialTheme.colorScheme.secondary,
        cursorColor = MaterialTheme.colorScheme.secondary,
        // Keep the error indicator and use the error surface's readable foreground.
        errorContainerColor = MaterialTheme.colorScheme.errorContainer,
        errorTextColor = MaterialTheme.colorScheme.onErrorContainer,
        errorLabelColor = MaterialTheme.colorScheme.onErrorContainer,
        errorSupportingTextColor = MaterialTheme.colorScheme.onErrorContainer,
        errorCursorColor = MaterialTheme.colorScheme.onErrorContainer,
    )

private data class ToggleNumberFieldConfig(
    val switchLabel: String,
    val fieldLabel: String,
    val errorText: String,
    val imeAction: ImeAction,
)
