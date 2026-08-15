package com.finnvek.rowtool.ui.screens.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finnvek.rowtool.R
import com.finnvek.rowtool.ui.theme.RowToolDimens
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel,
    onBack: () -> Unit,
    onMessage: suspend (Int) -> Unit,
    onImportComplete: (String?) -> Unit,
) {
    val context = LocalContext.current
    val resources = LocalResources.current
    val preferences by viewModel.preferences.collectAsStateWithLifecycle()
    val importPreview by viewModel.importPreview.collectAsStateWithLifecycle()
    val currentOnMessage by rememberUpdatedState(onMessage)
    val currentOnImportComplete by rememberUpdatedState(onImportComplete)
    val versionName =
        context.packageManager
            .getPackageInfo(context.packageName, 0)
            .versionName
            .orEmpty()
    val exportLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.CreateDocument("application/json"),
        ) { uri ->
            uri?.let { viewModel.export(context.contentResolver, it) }
        }
    val importLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.OpenDocument(),
        ) { uri ->
            uri?.let { viewModel.prepareImport(context.contentResolver, it) }
        }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is SettingsEffect.ShowMessage -> currentOnMessage(effect.message)
                is SettingsEffect.ImportComplete -> currentOnImportComplete(effect.lastActiveProjectId)
            }
        }
    }

    SettingsScreenContent(
        preferences = preferences,
        versionName = versionName,
        actions =
            SettingsScreenActions(
                onBack = onBack,
                onThemeMode = viewModel::setThemeMode,
                onHaptics = viewModel::setHaptics,
                onKeepAwake = viewModel::setKeepAwake,
                onExport = {
                    val date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                    exportLauncher.launch(resources.getString(R.string.backup_file_name, date))
                },
                onImport = { importLauncher.launch(arrayOf("application/json", "text/json")) },
            ),
    )

    importPreview?.let { preview ->
        val projectTotal = preview.backup.projects.size
        val projectCount =
            pluralStringResource(
                R.plurals.project_count,
                projectTotal,
                projectTotal,
            )
        val activeProjectCount =
            pluralStringResource(
                R.plurals.active_project_count,
                preview.activeCount,
                preview.activeCount,
            )
        val archivedProjectCount =
            pluralStringResource(
                R.plurals.archived_project_count,
                preview.archivedCount,
                preview.archivedCount,
            )
        val backupDate =
            if (preview.backup.exportedAt > 0) {
                DateFormat
                    .getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
                    .format(Date(preview.backup.exportedAt))
            } else {
                stringResource(R.string.backup_import_unknown_date)
            }
        AlertDialog(
            onDismissRequest = viewModel::dismissImport,
            title = { Text(stringResource(R.string.backup_import_title)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space12)) {
                    Text(
                        stringResource(
                            R.string.backup_import_summary,
                            backupDate,
                            projectCount,
                            activeProjectCount,
                            archivedProjectCount,
                        ),
                    )
                    Text(stringResource(R.string.backup_import_warning))
                }
            },
            confirmButton = {
                TextButton(onClick = viewModel::confirmImport) {
                    Text(stringResource(R.string.backup_import_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::dismissImport) {
                    Text(stringResource(R.string.action_cancel))
                }
            },
        )
    }
}
