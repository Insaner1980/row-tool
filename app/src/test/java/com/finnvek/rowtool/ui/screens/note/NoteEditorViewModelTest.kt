package com.finnvek.rowtool.ui.screens.note

import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.data.repository.ProjectNoteStore
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
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

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class NoteEditorViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var db: RowToolDatabase
    private lateinit var repository: CounterRepository

    @Before fun setUp() {
        Dispatchers.setMain(dispatcher)
        db =
            Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), RowToolDatabase::class.java)
                .setQueryCoroutineContext(dispatcher)
                .allowMainThreadQueries()
                .build()
        repository = CounterRepository(db)
    }

    @After fun tearDown() {
        db.close()
        Dispatchers.resetMain()
    }

    @Test fun restoredDraftStillRequiresDiscardConfirmationAfterExternalArchive() =
        runTest(dispatcher) {
            val id = repository.createProject("A", CounterUnit.ROWS, 0, null, null).id
            val handle = SavedStateHandle()
            val first = NoteEditorViewModel(id, repository.notes, handle)
            advanceUntilIdle()
            first.editText("Unsaved work")
            repository.setArchived(id, true)
            val restored = NoteEditorViewModel(id, repository.notes, SavedStateHandle(handle.keys().associateWith { handle.get<Any?>(it) }))
            advanceUntilIdle()
            assertTrue(restored.state.value.readOnly)
            restored.requestExit()
            assertEquals(NoteConfirmation.DISCARD, restored.state.value.confirmation)
            assertFalse(restored.state.value.completed)
            restored.cancelConfirmation()
            assertEquals("Unsaved work", restored.state.value.text)
        }

    @Test fun loadingAndReadFailureNeverBecomeAnEmptyEditableNote() =
        runTest(dispatcher) {
            val id = repository.createProject("A", CounterUnit.ROWS, 0, null, null).id
            repository.notes.save(id, null, "Existing", true)
            db.openHelper.writableDatabase.execSQL("ALTER TABLE project_notes RENAME TO held_notes")
            val vm = NoteEditorViewModel(id, repository.notes, SavedStateHandle())
            vm.editText("Premature")
            assertEquals("", vm.state.value.text)
            advanceUntilIdle()
            assertEquals(NoteError.READ, vm.state.value.error)
            assertFalse(vm.state.value.ready)
            vm.save()
            db.openHelper.writableDatabase.execSQL("ALTER TABLE held_notes RENAME TO project_notes")
            vm.retryLoad()
            advanceUntilIdle()
            assertEquals("Existing", vm.state.value.text)
            vm.editText("🧶".repeat(5001))
            vm.save()
            assertEquals(NoteError.LENGTH, vm.state.value.error)
            assertEquals("🧶".repeat(5001), vm.state.value.text)
            assertEquals(
                "Existing",
                repository.notes
                    .load(id)
                    .note!!
                    .text,
            )
        }

    @Test fun pendingWriteUsesTransactionCountAndCannotChangeAnotherEditor() =
        runTest(dispatcher) {
            val a = repository.createProject("A", CounterUnit.ROWS, 0, null, null).id
            val b = repository.createProject("B", CounterUnit.ROWS, 0, null, null).id
            val gate = Mutex(locked = true)
            val first = NoteEditorViewModel(a, ProjectNoteStore(db, gate) { 123L }, SavedStateHandle())
            val second = NoteEditorViewModel(b, repository.notes, SavedStateHandle())
            advanceUntilIdle()
            first.editText("A draft")
            second.editText("B draft")
            first.save()
            first.save()
            assertTrue(first.state.value.saving)
            repository.mutate(a, CounterMutation.ManualSet(74))
            gate.unlock()
            advanceUntilIdle()
            assertTrue(first.state.value.completed)
            assertFalse(second.state.value.completed)
            assertEquals("B draft", second.state.value.text)
            assertEquals(
                74L,
                repository.notes
                    .load(a)
                    .note!!
                    .savedCount,
            )
            assertNull(repository.notes.load(b).note)
            assertEquals(1, db.projectNoteDao().getAll().size)
        }

    @Test fun failedSaveAndRecreationKeepDraftAndSuccessfulSaveClearsIt() =
        runTest(dispatcher) {
            val id = repository.createProject("A", CounterUnit.ROWS, 0, null, null).id
            val handle = SavedStateHandle()
            val vm = NoteEditorViewModel(id, repository.notes, handle)
            advanceUntilIdle()
            vm.editText("  Draft\n    🧶")
            vm.attachCount(false)
            db.openHelper.writableDatabase.execSQL(
                "CREATE TRIGGER fail_note BEFORE INSERT ON project_notes " +
                    "BEGIN SELECT RAISE(ABORT, 'test'); END",
            )
            vm.save()
            vm.save()
            advanceUntilIdle()
            assertEquals(NoteError.WRITE, vm.state.value.error)
            assertEquals("  Draft\n    🧶", vm.state.value.text)
            assertFalse(vm.state.value.saving)
            val restored = NoteEditorViewModel(id, repository.notes, SavedStateHandle(handle.keys().associateWith { handle.get<Any?>(it) }))
            advanceUntilIdle()
            assertEquals(vm.state.value.text, restored.state.value.text)
            assertFalse(restored.state.value.attachCount)
            db.openHelper.writableDatabase.execSQL("DROP TRIGGER fail_note")
            vm.save()
            vm.save()
            advanceUntilIdle()
            assertTrue(vm.state.value.completed)
            val afterSave =
                NoteEditorViewModel(id, repository.notes, SavedStateHandle(handle.keys().associateWith { handle.get<Any?>(it) }))
            advanceUntilIdle()
            assertTrue(afterSave.state.value.completed)
            assertEquals("", afterSave.state.value.text)
            assertEquals(1, db.projectNoteDao().getAll().size)
        }

    @Test fun conflictsRequireConfirmedReloadAndExitCanBeCancelled() =
        runTest(dispatcher) {
            val id = repository.createProject("A", CounterUnit.ROWS, 0, null, null).id
            repository.notes.save(id, null, "Original", true)
            val vm = NoteEditorViewModel(id, repository.notes, SavedStateHandle())
            advanceUntilIdle()
            vm.editText("Draft")
            vm.requestExit()
            assertEquals(NoteConfirmation.DISCARD, vm.state.value.confirmation)
            vm.cancelConfirmation()
            assertFalse(vm.state.value.completed)
            val note = repository.notes.load(id).note!!
            repository.notes.save(id, note.version, "Other editor", true)
            vm.save()
            advanceUntilIdle()
            assertEquals(NoteError.CONFLICT, vm.state.value.error)
            assertEquals("Draft", vm.state.value.text)
            vm.requestConfirmation(NoteConfirmation.RELOAD)
            vm.cancelConfirmation()
            assertEquals("Draft", vm.state.value.text)
            vm.requestConfirmation(NoteConfirmation.RELOAD)
            vm.confirm()
            advanceUntilIdle()
            assertEquals("Other editor", vm.state.value.text)
            vm.editText(" \n")
            vm.save()
            assertEquals(NoteConfirmation.DELETE, vm.state.value.confirmation)
            vm.cancelConfirmation()
            assertEquals(
                "Other editor",
                repository.notes
                    .load(id)
                    .note!!
                    .text,
            )
            vm.save()
            vm.confirm()
            advanceUntilIdle()
            assertNull(repository.notes.load(id).note)
            assertTrue(vm.state.value.completed)
        }
}
