package com.finnvek.rowtool.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import com.finnvek.rowtool.R
import com.finnvek.rowtool.data.preferences.AppPreferences
import com.finnvek.rowtool.data.preferences.ThemeMode
import com.finnvek.rowtool.ui.theme.RowToolDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    preferences: AppPreferences,
    versionName: String,
    actions: SettingsScreenActions,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = actions.onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = stringResource(R.string.action_back),
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
            )
        },
    ) { contentPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
            contentAlignment = Alignment.TopCenter,
        ) {
            LazyColumn(
                modifier =
                    Modifier
                        .widthIn(max = RowToolDimens.MaxContentWidth)
                        .fillMaxWidth(),
                contentPadding =
                    PaddingValues(
                        start = RowToolDimens.PhoneHorizontalPadding,
                        end = RowToolDimens.PhoneHorizontalPadding,
                        top = RowToolDimens.Space12,
                        bottom = RowToolDimens.Space32,
                    ),
                verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space4),
            ) {
                item { SectionTitle(stringResource(R.string.settings_appearance)) }
                item {
                    ThemeOption(
                        label = stringResource(R.string.settings_theme_system),
                        selected = preferences.themeMode == ThemeMode.SYSTEM,
                        onClick = { actions.onThemeMode(ThemeMode.SYSTEM) },
                    )
                }
                item {
                    ThemeOption(
                        label = stringResource(R.string.settings_theme_light),
                        selected = preferences.themeMode == ThemeMode.LIGHT,
                        onClick = { actions.onThemeMode(ThemeMode.LIGHT) },
                    )
                }
                item {
                    ThemeOption(
                        label = stringResource(R.string.settings_theme_dark),
                        selected = preferences.themeMode == ThemeMode.DARK,
                        onClick = { actions.onThemeMode(ThemeMode.DARK) },
                    )
                }
                item { SectionDivider() }
                item { SectionTitle(stringResource(R.string.settings_counter)) }
                item {
                    ToggleRow(
                        title = stringResource(R.string.settings_haptics),
                        summary = stringResource(R.string.settings_haptics_summary),
                        checked = preferences.hapticFeedbackEnabled,
                        onCheckedChange = actions.onHaptics,
                    )
                }
                item {
                    ToggleRow(
                        title = stringResource(R.string.settings_keep_awake),
                        summary = stringResource(R.string.settings_keep_awake_summary),
                        checked = preferences.keepScreenAwake,
                        onCheckedChange = actions.onKeepAwake,
                    )
                }
                item { SectionDivider() }
                item { SectionTitle(stringResource(R.string.settings_data)) }
                item {
                    ActionRow(
                        title = stringResource(R.string.action_export),
                        summary = stringResource(R.string.settings_export_summary),
                        onClick = actions.onExport,
                    )
                }
                item {
                    ActionRow(
                        title = stringResource(R.string.action_import),
                        summary = stringResource(R.string.settings_import_summary),
                        onClick = actions.onImport,
                    )
                }
                item { SectionDivider() }
                item { SectionTitle(stringResource(R.string.settings_about)) }
                item {
                    Column(
                        modifier =
                            Modifier.padding(
                                horizontal = RowToolDimens.Space16,
                                vertical = RowToolDimens.Space8,
                            ),
                        verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space8),
                    ) {
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            text = stringResource(R.string.settings_version, versionName),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            text = stringResource(R.string.settings_finnvek),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            text = stringResource(R.string.settings_privacy),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            text = stringResource(R.string.settings_business_model),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.secondary,
        modifier =
            Modifier.padding(
                start = RowToolDimens.Space16,
                end = RowToolDimens.Space16,
                top = RowToolDimens.Space12,
                bottom = RowToolDimens.Space4,
            ),
    )
}

@Composable
private fun ThemeOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .selectable(
                    selected = selected,
                    role = Role.RadioButton,
                    onClick = onClick,
                ).padding(horizontal = RowToolDimens.Space12, vertical = RowToolDimens.Space8),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = selected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = RowToolDimens.Space12),
        )
    }
}

@Composable
private fun ToggleRow(
    title: String,
    summary: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .toggleable(
                    value = checked,
                    role = Role.Switch,
                    onValueChange = onCheckedChange,
                ).padding(horizontal = RowToolDimens.Space16, vertical = RowToolDimens.Space12),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space4),
        ) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = summary,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Switch(checked = checked, onCheckedChange = null)
    }
}

@Composable
private fun ActionRow(
    title: String,
    summary: String,
    onClick: () -> Unit,
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = { Text(summary) },
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier.clickable(role = Role.Button, onClick = onClick),
    )
}

@Composable
private fun SectionDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = RowToolDimens.Space8),
        color = MaterialTheme.colorScheme.outlineVariant,
    )
}
