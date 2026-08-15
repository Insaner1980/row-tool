package com.finnvek.rowtool.ui.screens.settings

import android.content.ContentResolver
import android.database.SQLException
import android.net.Uri
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finnvek.rowtool.R
import com.finnvek.rowtool.data.preferences.AppPreferences
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.preferences.ThemeMode
import com.finnvek.rowtool.data.repository.BackupDecodeResult
import com.finnvek.rowtool.data.repository.BackupImportResult
import com.finnvek.rowtool.data.repository.BackupRepository
import com.finnvek.rowtool.data.repository.BackupValidationError
import com.finnvek.rowtool.data.repository.ValidatedBackup
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.io.IOException

data class ImportPreview(
    val backup: ValidatedBackup,
    val activeCount: Int,
    val archivedCount: Int,
)

sealed interface SettingsEffect {
    data class ShowMessage(
        @StringRes val message: Int,
    ) : SettingsEffect

    data class ImportComplete(
        val lastActiveProjectId: String?,
    ) : SettingsEffect
}

class SettingsViewModel(
    private val preferencesRepository: PreferencesRepository,
    private val backupRepository: BackupRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    val preferences: StateFlow<AppPreferences> =
        preferencesRepository.preferences.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppPreferences(),
        )

    private val _importPreview = MutableStateFlow<ImportPreview?>(null)
    val importPreview = _importPreview.asStateFlow()

    private val effectChannel = Channel<SettingsEffect>(Channel.BUFFERED)
    val effects = effectChannel.receiveAsFlow()

    fun setThemeMode(mode: ThemeMode) =
        updatePreference {
            preferencesRepository.setThemeMode(mode)
        }

    fun setHaptics(enabled: Boolean) =
        updatePreference {
            preferencesRepository.setHapticFeedbackEnabled(enabled)
        }

    fun setKeepAwake(enabled: Boolean) =
        updatePreference {
            preferencesRepository.setKeepScreenAwake(enabled)
        }

    fun export(
        contentResolver: ContentResolver,
        uri: Uri,
    ) {
        viewModelScope.launch {
            try {
                val json = backupRepository.exportJson()
                withContext(ioDispatcher) {
                    val stream =
                        contentResolver.openOutputStream(uri, "w")
                            ?: throw FileNotFoundException()
                    stream.bufferedWriter(Charsets.UTF_8).use { writer ->
                        writer.write(json)
                    }
                }
                effectChannel.send(SettingsEffect.ShowMessage(R.string.backup_export_success))
            } catch (_: FileNotFoundException) {
                effectChannel.send(SettingsEffect.ShowMessage(R.string.backup_export_failed))
            } catch (_: IOException) {
                effectChannel.send(SettingsEffect.ShowMessage(R.string.backup_export_failed))
            } catch (_: SecurityException) {
                effectChannel.send(SettingsEffect.ShowMessage(R.string.backup_export_failed))
            } catch (_: SQLException) {
                effectChannel.send(SettingsEffect.ShowMessage(R.string.backup_export_failed))
            }
        }
    }

    fun prepareImport(
        contentResolver: ContentResolver,
        uri: Uri,
    ) {
        viewModelScope.launch {
            val result =
                try {
                    withContext(ioDispatcher) {
                        contentResolver.openInputStream(uri)?.use { stream ->
                            backupRepository.prepareImport(stream)
                        } ?: throw FileNotFoundException()
                    }
                } catch (_: FileNotFoundException) {
                    effectChannel.send(SettingsEffect.ShowMessage(R.string.backup_import_failed_open))
                    return@launch
                } catch (_: IOException) {
                    effectChannel.send(SettingsEffect.ShowMessage(R.string.backup_import_failed_open))
                    return@launch
                } catch (_: SecurityException) {
                    effectChannel.send(SettingsEffect.ShowMessage(R.string.backup_import_failed_open))
                    return@launch
                }

            when (result) {
                is BackupDecodeResult.Valid -> {
                    _importPreview.value =
                        ImportPreview(
                            backup = result.backup,
                            activeCount = result.backup.projects.count { !it.isArchived },
                            archivedCount = result.backup.projects.count { it.isArchived },
                        )
                }

                is BackupDecodeResult.Invalid -> {
                    effectChannel.send(
                        SettingsEffect.ShowMessage(result.error.messageResource()),
                    )
                }
            }
        }
    }

    fun dismissImport() {
        _importPreview.value = null
    }

    fun confirmImport() {
        val preview = _importPreview.value ?: return
        viewModelScope.launch {
            when (val result = backupRepository.replaceWith(preview.backup)) {
                is BackupImportResult.Success -> {
                    _importPreview.value = null
                    effectChannel.send(SettingsEffect.ShowMessage(R.string.backup_import_success))
                    effectChannel.send(SettingsEffect.ImportComplete(result.lastActiveProjectId))
                }

                is BackupImportResult.Failure -> {
                    effectChannel.send(
                        SettingsEffect.ShowMessage(R.string.backup_import_failed_write),
                    )
                }
            }
        }
    }

    private fun updatePreference(update: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                update()
            } catch (_: IOException) {
                effectChannel.send(SettingsEffect.ShowMessage(R.string.error_database_write))
            }
        }
    }

    private fun BackupValidationError.messageResource(): Int =
        when (this) {
            BackupValidationError.TOO_LARGE -> R.string.backup_import_too_large

            BackupValidationError.MALFORMED_JSON -> R.string.backup_import_invalid_json

            BackupValidationError.UNSUPPORTED_SCHEMA_VERSION -> R.string.backup_import_unsupported

            BackupValidationError.INVALID_APPLICATION,
            BackupValidationError.TOO_MANY_PROJECTS,
            BackupValidationError.DUPLICATE_PROJECT_ID,
            BackupValidationError.INVALID_PROJECT,
            -> R.string.backup_import_invalid_data
        }

    companion object {
        fun factory(
            preferencesRepository: PreferencesRepository,
            backupRepository: BackupRepository,
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer { SettingsViewModel(preferencesRepository, backupRepository) }
            }
    }
}
