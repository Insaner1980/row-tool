package com.finnvek.rowtool.ui.screens.projects

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.test.InMemoryPreferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class CopySetupViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var db: RowToolDatabase
    private lateinit var repository: CounterRepository
    private lateinit var preferences: PreferencesRepository

    @Before fun setup() {
        Dispatchers.setMain(dispatcher)
        db =
            Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), RowToolDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        repository = CounterRepository(db)
        preferences = PreferencesRepository(InMemoryPreferencesDataStore(), db.projectDao())
    }

    @After fun teardown() {
        db.close()
        Dispatchers.resetMain()
    }

    @Test fun snapshotRestorationDoesNotReloadSourceAndCompletionDoesNotDuplicate() =
        runTest(dispatcher) {
            val source = repository.createProject("Original", CounterUnit.ROUNDS, 1, 120, 8, 11)
            val saved = SavedStateHandle()
            val first = CopySetupViewModel(source.id, repository, preferences, saved)
            val snapshot = first.state.first { it.snapshot != null }.snapshot!!
            repository.updateProject(source.id, "Edited", CounterUnit.ROWS, 0, null, null)
            val restored = CopySetupViewModel(source.id, repository, preferences, saved)
            assertEquals(snapshot, restored.state.value.snapshot)
            restored.create(snapshot.settings.copy(name = "Copy"))
            restored.create(snapshot.settings.copy(name = "Duplicate"))
            restored.state.first { it.completedId != null }
            assertEquals(2, db.projectDao().count())
            val completed = CopySetupViewModel(source.id, repository, preferences, saved)
            assertEquals(restored.state.value.completedId, completed.state.value.completedId)
            completed.create(snapshot.settings.copy(name = "Again"))
            advanceUntilIdle()
            assertEquals(2, db.projectDao().count())
        }

    @Test fun readFailureIsNotAnEmptySuccessfulSetup() =
        runTest(dispatcher) {
            val source = repository.createProject("Source", CounterUnit.ROWS, 0, null, null)
            db.openHelper.writableDatabase.execSQL("DROP TABLE additional_counters")
            val vm = CopySetupViewModel(source.id, repository, preferences, SavedStateHandle())
            val failure = vm.state.first { it.error != null }
            assertNull(failure.snapshot)
            assertFalse(failure.loading)
            assertEquals(com.finnvek.rowtool.R.string.copy_read_error, failure.error)
        }

    @Test fun preferenceFailureAfterCommitKeepsSuccessfulIdentity() =
        runTest(dispatcher) {
            val failingStore =
                object : DataStore<Preferences> {
                    override val data =
                        kotlinx.coroutines.flow.flowOf(
                            androidx.datastore.preferences.core
                                .emptyPreferences(),
                        )

                    override suspend fun updateData(
                        transform: suspend (
                            Preferences,
                        ) -> Preferences,
                    ): Preferences = throw java.io.IOException("Injected preference failure")
                }
            val source = repository.createProject("Source", CounterUnit.ROWS, 0, null, null)
            val vm = CopySetupViewModel(source.id, repository, PreferencesRepository(failingStore, db.projectDao()), SavedStateHandle())
            val snapshot = vm.state.first { it.snapshot != null }.snapshot!!
            vm.create(snapshot.settings.copy(name = "Copy"))
            val complete = vm.state.first { it.completedId != null }
            assertNull(complete.error)
            vm.create(snapshot.settings.copy(name = "Again"))
            assertEquals(2, db.projectDao().count())
        }

    @Test fun cancellationWritesNothingAndWriteFailureRetainsSnapshotForRetry() =
        runTest(dispatcher) {
            val source = repository.createProject("Original", CounterUnit.ROWS, 0, null, null)
            val vm = CopySetupViewModel(source.id, repository, preferences, SavedStateHandle())
            vm.state.first { it.snapshot != null }
            assertEquals(1, db.projectDao().count())
            val snapshot = vm.state.value.snapshot!!
            db.openHelper.writableDatabase.execSQL(
                "CREATE TRIGGER fail_project BEFORE INSERT ON projects " +
                    "BEGIN SELECT RAISE(ABORT, 'Failure'); END",
            )
            vm.create(snapshot.settings.copy(name = "Copy"))
            vm.state.first { it.error != null }
            assertEquals(snapshot, vm.state.value.snapshot)
            assertNotNull(vm.state.value.error)
            assertFalse(vm.state.value.saving)
            assertEquals(1, db.projectDao().count())
            db.openHelper.writableDatabase.execSQL("DROP TRIGGER fail_project")
            vm.create(snapshot.settings.copy(name = "Copy"))
            vm.state.first { it.completedId != null }
            assertNotNull(vm.state.value.completedId)
        }
}
