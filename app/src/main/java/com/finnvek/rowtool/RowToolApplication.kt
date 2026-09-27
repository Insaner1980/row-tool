package com.finnvek.rowtool

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.repository.BackupRepository
import com.finnvek.rowtool.data.repository.CounterRepository
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val Context.rowToolDataStore by
    preferencesDataStore(
        name = "rowtool_preferences",
        corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
    )

class RowToolApplication : Application() {
    val widgetConfiguration by lazy {
        kotlinx.coroutines.flow.MutableStateFlow(
            android.content.res.Configuration(
                androidx.core.content.ContextCompat
                    .getContextForLanguage(this)
                    .resources.configuration,
            ),
        )
    }

    lateinit var container: AppContainer
        private set

    override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        super.onConfigurationChanged(newConfig)
        com.finnvek.rowtool.widget
            .requestWidgetUpdate(this)
    }

    override fun onCreate() {
        super.onCreate()
        preserveFrameworkLanguage(this)
        container = AppContainer(this)
    }
}

class AppContainer(
    context: Context,
) {
    var copyEditorVisible by androidx.compose.runtime.mutableStateOf(false)
    var noteEditorVisible by androidx.compose.runtime.mutableStateOf(false)

    val database: RowToolDatabase = RowToolDatabase.create(context)
    val counterRepository = CounterRepository(database)
    val preferencesRepository =
        PreferencesRepository(
            dataStore = context.rowToolDataStore,
            projectDao = database.projectDao(),
        )
    val widgetBindings =
        com.finnvek.rowtool.widget
            .WidgetBindings(java.io.File(context.noBackupFilesDir, "widget-bindings.xml"))
    val backupRepository = BackupRepository(database, preferencesRepository, replaceTransaction = widgetBindings::replaceDatabase)
    private val widgetScope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.SupervisorJob() + kotlinx.coroutines.Dispatchers.IO)

    init {
        database.invalidationTracker.addObserver(
            com.finnvek.rowtool.widget
                .WidgetDatabaseObserver(context.applicationContext),
        )
        widgetScope.launch {
            preferencesRepository.preferences.map { it.themeMode }.distinctUntilChanged().collect {
                com.finnvek.rowtool.widget
                    .requestWidgetUpdate(context)
            }
        }
    }
}
