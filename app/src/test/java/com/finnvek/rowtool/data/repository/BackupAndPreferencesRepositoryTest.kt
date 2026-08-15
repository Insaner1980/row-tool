package com.finnvek.rowtool.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.preferences.AppPreferences
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.preferences.ThemeMode
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.io.IOException
import java.util.UUID
import java.util.concurrent.atomic.AtomicLong

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class BackupAndPreferencesRepositoryTest {
    private lateinit var database: RowToolDatabase
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var dataStoreFile: File
    private lateinit var dataStoreScope: CoroutineScope
    private lateinit var preferencesRepository: PreferencesRepository
    private lateinit var counterRepository: CounterRepository
    private lateinit var backupRepository: BackupRepository
    private val now = AtomicLong(10_000)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            Room
                .inMemoryDatabaseBuilder(context, RowToolDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        dataStoreFile = context.preferencesDataStoreFile("rowtool-test-${UUID.randomUUID()}")
        dataStoreScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        dataStore = PreferenceDataStoreFactory.create(scope = dataStoreScope) { dataStoreFile }
        preferencesRepository = PreferencesRepository(dataStore, database.projectDao())
        counterRepository = CounterRepository(database, clock = { now.incrementAndGet() })
        backupRepository =
            BackupRepository(
                database = database,
                preferencesRepository = preferencesRepository,
                clock = { now.incrementAndGet() },
            )
    }

    @After
    fun tearDown() {
        database.close()
        dataStoreScope.cancel()
        dataStoreFile.delete()
    }

    @Test
    fun preferencesUseRequiredDefaultsAndPersistChanges() =
        runTest {
            assertEquals(AppPreferences(), preferencesRepository.preferences.first())

            preferencesRepository.setThemeMode(ThemeMode.DARK)
            preferencesRepository.setHapticFeedbackEnabled(false)
            preferencesRepository.setKeepScreenAwake(false)
            preferencesRepository.setLastActiveProjectId("project-1")

            assertEquals(
                AppPreferences(
                    themeMode = ThemeMode.DARK,
                    hapticFeedbackEnabled = false,
                    keepScreenAwake = false,
                    lastActiveProjectId = "project-1",
                ),
                preferencesRepository.preferences.first(),
            )
        }

    @Test
    fun validLastActiveProjectIsKept() =
        runTest {
            val project = createProject("Current")
            preferencesRepository.setLastActiveProjectId(project.id)

            assertEquals(project.id, preferencesRepository.resolveLastActiveProjectId())
            assertEquals(project.id, preferencesRepository.preferences.first().lastActiveProjectId)
        }

    @Test
    fun missingLastActiveProjectIsReplacedWithNewestActiveProject() =
        runTest {
            createProject("Older")
            val newest = createProject("Newest")
            preferencesRepository.setLastActiveProjectId("missing")

            assertEquals(newest.id, preferencesRepository.resolveLastActiveProjectId())
            assertEquals(newest.id, preferencesRepository.preferences.first().lastActiveProjectId)
        }

    @Test
    fun archivedLastActiveProjectIsReplacedWithNewestActiveProject() =
        runTest {
            val active = createProject("Active")
            val archived = createProject("Archived")
            counterRepository.setArchived(archived.id, true)
            preferencesRepository.setLastActiveProjectId(archived.id)

            assertEquals(active.id, preferencesRepository.resolveLastActiveProjectId())
        }

    @Test
    fun onlyArchivedProjectsClearLastActiveSelection() =
        runTest {
            val archived = createProject("Archived")
            counterRepository.setArchived(archived.id, true)
            preferencesRepository.setLastActiveProjectId(archived.id)

            assertNull(preferencesRepository.resolveLastActiveProjectId())
            assertNull(preferencesRepository.preferences.first().lastActiveProjectId)
        }

    @Test
    fun preferenceWriteFailureDoesNotBlockLastActiveResolution() =
        runTest {
            val newest = createProject("Newest")
            val failingDataStore = failingDataStore()
            val repository = PreferencesRepository(failingDataStore, database.projectDao())

            assertEquals(newest.id, repository.resolveLastActiveProjectId())
        }

    @Test
    fun failedImportValidationLeavesExistingProjectsUntouched() =
        runTest {
            val existing = createProject("Existing")
            counterRepository.mutate(existing.id, CounterMutation.Increment)
            preferencesRepository.setLastActiveProjectId(existing.id)

            val prepared = backupRepository.prepareImport("not json".encodeToByteArray())

            assertEquals(
                BackupDecodeResult.Invalid(BackupValidationError.MALFORMED_JSON),
                prepared,
            )
            assertEquals(existing.copy(count = 1, updatedAt = now.get()), counterRepository.getProject(existing.id))
            assertEquals(1, database.counterHistoryDao().countAll())
            assertEquals(existing.id, preferencesRepository.preferences.first().lastActiveProjectId)
        }

    @Test
    fun successfulImportAtomicallyReplacesProjectsAndClearsHistory() =
        runTest {
            val old = createProject("Old")
            counterRepository.mutate(old.id, CounterMutation.Increment)
            preferencesRepository.setLastActiveProjectId(old.id)
            val importedOlder = importedProject(id = "imported-1", name = "Older", updatedAt = 200)
            val importedNewest = importedProject(id = "imported-2", name = "Newest", updatedAt = 300)

            val result =
                backupRepository.replaceWith(
                    ValidatedBackup(exportedAt = 400, projects = listOf(importedOlder, importedNewest)),
                )

            assertEquals(BackupImportResult.Success(2, importedNewest.id), result)
            assertNull(counterRepository.getProject(old.id))
            assertEquals(listOf(importedNewest, importedOlder), counterRepository.projects.first())
            assertEquals(0, database.counterHistoryDao().countAll())
            assertEquals(importedNewest.id, preferencesRepository.preferences.first().lastActiveProjectId)
        }

    @Test
    fun importWithOnlyArchivedProjectsClearsLastActiveSelection() =
        runTest {
            val archived =
                importedProject(id = "archived", name = "Archived", updatedAt = 300)
                    .copy(isArchived = true)
            preferencesRepository.setLastActiveProjectId("old")

            val result =
                backupRepository.replaceWith(
                    ValidatedBackup(exportedAt = 400, projects = listOf(archived)),
                )

            assertEquals(BackupImportResult.Success(1, null), result)
            assertNull(preferencesRepository.preferences.first().lastActiveProjectId)
        }

    @Test
    fun databaseFailureRollsBackImportReplacement() =
        runTest {
            val existing = createProject("Existing")
            database.openHelper.writableDatabase.execSQL(
                """
                CREATE TRIGGER reject_blocked_project
                BEFORE INSERT ON projects
                WHEN NEW.name = 'Blocked'
                BEGIN
                    SELECT RAISE(ABORT, 'blocked by test');
                END
                """.trimIndent(),
            )

            val result =
                backupRepository.replaceWith(
                    ValidatedBackup(
                        exportedAt = 400,
                        projects = listOf(importedProject(id = "blocked", name = "Blocked", updatedAt = 500)),
                    ),
                )

            assertEquals(
                BackupImportResult.Failure(BackupImportFailure.DATABASE_WRITE_FAILED),
                result,
            )
            assertEquals(existing, counterRepository.getProject(existing.id))
            assertEquals(1, counterRepository.projects.first().size)
        }

    @Test
    fun preferenceWriteFailureDoesNotTurnCommittedImportIntoFailure() =
        runTest {
            val existing = createProject("Existing")
            val failingDataStore = failingDataStore()
            val repositoryWithFailingPreferences =
                BackupRepository(
                    database = database,
                    preferencesRepository = PreferencesRepository(failingDataStore, database.projectDao()),
                    clock = { now.incrementAndGet() },
                )
            val imported = importedProject(id = "imported", name = "Imported", updatedAt = 500)

            val result =
                repositoryWithFailingPreferences.replaceWith(
                    ValidatedBackup(exportedAt = 400, projects = listOf(imported)),
                )

            assertEquals(BackupImportResult.Success(1, imported.id), result)
            assertNull(counterRepository.getProject(existing.id))
            assertEquals(imported, counterRepository.getProject(imported.id))
        }

    @Test
    fun exportContainsProjectsButNotUndoHistory() =
        runTest {
            val project = createProject("Exported")
            counterRepository.mutate(project.id, CounterMutation.Increment)

            val json = backupRepository.exportJson()
            val decoded = BackupCodec.decode(json.encodeToByteArray())

            assertTrue(decoded is BackupDecodeResult.Valid)
            val exported = (decoded as BackupDecodeResult.Valid).backup.projects.single()
            assertEquals(1L, exported.count)
            assertFalse(json.contains("previousCount"))
            assertFalse(json.contains("changeReason"))
        }

    private suspend fun createProject(name: String) =
        counterRepository.createProject(
            name = name,
            counterUnit = CounterUnit.ROWS,
            startValue = 0,
            targetCount = null,
            repeatLength = null,
        )

    private fun failingDataStore(): DataStore<Preferences> =
        object : DataStore<Preferences> {
            override val data = flowOf(emptyPreferences())

            override suspend fun updateData(transform: suspend (t: Preferences) -> Preferences): Preferences =
                throw IOException("simulated preference write failure")
        }

    private fun importedProject(
        id: String,
        name: String,
        updatedAt: Long,
    ) = CounterProject(
        id = id,
        name = name,
        counterUnit = CounterUnit.ROUNDS,
        count = 12,
        startValue = 1,
        targetCount = 80,
        repeatLength = 6,
        isArchived = false,
        createdAt = 100,
        updatedAt = updatedAt,
    )
}
