package com.finnvek.rowtool

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.repository.BackupRepository
import com.finnvek.rowtool.data.repository.CounterRepository

private val Context.rowToolDataStore by preferencesDataStore(name = "rowtool_preferences")

class RowToolApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}

class AppContainer(
    context: Context,
) {
    val database: RowToolDatabase = RowToolDatabase.create(context)
    val counterRepository = CounterRepository(database)
    val preferencesRepository =
        PreferencesRepository(
            dataStore = context.rowToolDataStore,
            projectDao = database.projectDao(),
        )
    val backupRepository = BackupRepository(database, preferencesRepository)
}
