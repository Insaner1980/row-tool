package com.finnvek.rowtool.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.finnvek.rowtool.data.local.ProjectDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class PreferencesRepository(
    private val dataStore: DataStore<Preferences>,
    private val projectDao: ProjectDao,
) {
    val preferences: Flow<AppPreferences> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { values ->
                AppPreferences(
                    themeMode =
                        values[THEME_MODE]
                            ?.let { stored -> ThemeMode.entries.firstOrNull { it.name == stored } }
                            ?: ThemeMode.SYSTEM,
                    hapticFeedbackEnabled = values[HAPTIC_FEEDBACK_ENABLED] ?: true,
                    keepScreenAwake = values[KEEP_SCREEN_AWAKE] ?: true,
                    lastActiveProjectId = values[LAST_ACTIVE_PROJECT_ID],
                )
            }

    suspend fun setThemeMode(mode: ThemeMode) {
        dataStore.edit { it[THEME_MODE] = mode.name }
    }

    suspend fun setHapticFeedbackEnabled(enabled: Boolean) {
        dataStore.edit { it[HAPTIC_FEEDBACK_ENABLED] = enabled }
    }

    suspend fun setKeepScreenAwake(enabled: Boolean) {
        dataStore.edit { it[KEEP_SCREEN_AWAKE] = enabled }
    }

    suspend fun setLastActiveProjectId(projectId: String?) {
        dataStore.edit { values ->
            if (projectId == null) {
                values.remove(LAST_ACTIVE_PROJECT_ID)
            } else {
                values[LAST_ACTIVE_PROJECT_ID] = projectId
            }
        }
    }

    suspend fun resolveLastActiveProjectId(): String? {
        val storedId = preferences.first().lastActiveProjectId
        if (storedId != null && projectDao.getById(storedId)?.isArchived == false) {
            return storedId
        }

        val replacement = projectDao.getMostRecentlyUpdatedActive()?.id
        if (replacement != storedId) {
            try {
                setLastActiveProjectId(replacement)
            } catch (_: IOException) {
                // The database result is still safe to use for this session.
            }
        }
        return replacement
    }

    private companion object {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val HAPTIC_FEEDBACK_ENABLED = booleanPreferencesKey("haptic_feedback_enabled")
        val KEEP_SCREEN_AWAKE = booleanPreferencesKey("keep_screen_awake")
        val LAST_ACTIVE_PROJECT_ID = stringPreferencesKey("last_active_project_id")
    }
}
