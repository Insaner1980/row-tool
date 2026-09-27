package com.finnvek.rowtool.ui.screens.note

import android.database.SQLException
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finnvek.rowtool.data.repository.NoteSnapshot
import com.finnvek.rowtool.data.repository.NoteWriteResult
import com.finnvek.rowtool.data.repository.ProjectNoteStore
import com.finnvek.rowtool.domain.model.ProjectNoteRules
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

internal enum class NoteError { READ, WRITE, CONFLICT, UNAVAILABLE, LENGTH }

internal enum class NoteConfirmation { DISCARD, DELETE, RELOAD }

internal data class NoteEditorState(
    val projectName: String = "",
    val readOnly: Boolean = false,
    val loading: Boolean = true,
    val ready: Boolean = false,
    val text: String = "",
    val attachCount: Boolean = true,
    val baseText: String = "",
    val baseAttachCount: Boolean = true,
    val version: String? = null,
    val savedAt: Long? = null,
    val savedCount: Long? = null,
    val saving: Boolean = false,
    val completed: Boolean = false,
    val error: NoteError? = null,
    val confirmation: NoteConfirmation? = null,
) {
    val dirty: Boolean get() = ProjectNoteRules.normalize(text) != baseText || attachCount != baseAttachCount
}

internal class NoteEditorViewModel(
    private val projectId: String,
    private val store: ProjectNoteStore,
    private val savedState: SavedStateHandle,
) : ViewModel() {
    private val mutableState = MutableStateFlow(NoteEditorState(completed = savedState["completed"] ?: false))
    val state = mutableState.asStateFlow()

    init {
        if (!state.value.completed) load(false)
    }

    fun editText(text: String) {
        if (!state.value.editable) return
        mutableState.value = state.value.copy(text = text, error = null)
        savedState.persistDraft(state.value)
    }

    fun attachCount(attach: Boolean) {
        if (!state.value.editable) return
        mutableState.value = state.value.copy(attachCount = attach, error = null)
        savedState.persistDraft(state.value)
    }

    fun requestExit() {
        if (state.value.saving) return
        if (state.value.dirty) {
            mutableState.value = state.value.copy(confirmation = NoteConfirmation.DISCARD)
        } else {
            complete()
        }
    }

    fun requestConfirmation(confirmation: NoteConfirmation) {
        if (state.value.saving) return
        if (confirmation == NoteConfirmation.DELETE && (!state.value.editable || state.value.version == null)) return
        mutableState.value = state.value.copy(confirmation = confirmation)
    }

    fun cancelConfirmation() {
        if (!state.value.saving) mutableState.value = state.value.copy(confirmation = null)
    }

    fun confirm() {
        if (state.value.saving) return
        when (state.value.confirmation) {
            NoteConfirmation.DISCARD -> complete()
            NoteConfirmation.RELOAD -> load(true)
            NoteConfirmation.DELETE -> write(true)
            null -> Unit
        }
    }

    fun retryLoad() {
        if (!state.value.loading && !state.value.ready) load(false)
    }

    fun save() {
        if (!state.value.editable) return
        if (!ProjectNoteRules.withinLimit(state.value.text)) {
            mutableState.value = state.value.copy(error = NoteError.LENGTH)
        } else if (state.value.text.isBlank() && state.value.version != null) {
            requestConfirmation(NoteConfirmation.DELETE)
        } else {
            write(false)
        }
    }

    private fun load(discard: Boolean) {
        mutableState.value = state.value.copy(loading = true, ready = false, error = null, confirmation = null)
        viewModelScope.launch {
            try {
                val snapshot = store.load(projectId)
                val project = snapshot.project
                if (project == null) {
                    mutableState.value = state.value.copy(loading = false, error = NoteError.UNAVAILABLE)
                    return@launch
                }
                val restore = !discard && savedState.get<Boolean>("draft") == true
                mutableState.value = snapshot.editorState().let { if (restore) savedState.restoreDraft(it) else it }
                savedState.persistDraft(state.value)
            } catch (
                _: SQLException,
            ) {
                mutableState.value = state.value.copy(loading = false, error = NoteError.READ)
            } catch (
                _: IOException,
            ) {
                mutableState.value = state.value.copy(loading = false, error = NoteError.READ)
            }
        }
    }

    private fun write(delete: Boolean) {
        val request = state.value
        mutableState.value = request.copy(saving = true, error = null, confirmation = null)
        viewModelScope.launch {
            try {
                val result =
                    if (delete) {
                        store.delete(projectId, requireNotNull(request.version))
                    } else {
                        store.save(projectId, request.version, request.text, request.attachCount)
                    }
                when (result) {
                    is NoteWriteResult.Success -> complete()
                    NoteWriteResult.Conflict -> mutableState.value = state.value.copy(error = NoteError.CONFLICT)
                    NoteWriteResult.Unavailable -> mutableState.value = state.value.copy(error = NoteError.UNAVAILABLE)
                    NoteWriteResult.Invalid -> mutableState.value = state.value.copy(error = NoteError.LENGTH)
                    NoteWriteResult.DeletionRequired -> mutableState.value = state.value.copy(confirmation = NoteConfirmation.DELETE)
                }
            } catch (
                _: SQLException,
            ) {
                mutableState.value = state.value.copy(error = NoteError.WRITE)
            } catch (
                _: IOException,
            ) {
                mutableState.value = state.value.copy(error = NoteError.WRITE)
            } finally {
                mutableState.value = state.value.copy(saving = false)
            }
        }
    }

    private fun complete() {
        savedState.keys().toList().forEach { savedState.remove<Any?>(it) }
        savedState["completed"] = true
        mutableState.value = NoteEditorState(loading = false, completed = true)
    }
}

private val NoteEditorState.editable: Boolean get() = ready && !readOnly && !saving && !completed

private fun NoteSnapshot.editorState(): NoteEditorState =
    NoteEditorState(
        projectName = requireNotNull(project).name,
        readOnly = project.isArchived,
        loading = false,
        ready = true,
        text = note?.text.orEmpty(),
        baseText = note?.text.orEmpty(),
        attachCount = note == null || note.savedCount != null,
        baseAttachCount = note == null || note.savedCount != null,
        version = note?.version,
        savedAt = note?.savedAt,
        savedCount = note?.savedCount,
    )

private fun SavedStateHandle.restoreDraft(base: NoteEditorState): NoteEditorState =
    base.copy(
        text = get<String>("text").orEmpty(),
        attachCount = get<Boolean>("attach") ?: true,
        baseText = get<String>("baseText").orEmpty(),
        baseAttachCount = get<Boolean>("baseAttach") ?: true,
        version = get("version"),
        savedAt = get("savedAt"),
        savedCount = get("savedCount"),
    )

private fun SavedStateHandle.persistDraft(value: NoteEditorState) {
    this["draft"] = true
    this["text"] = value.text
    this["attach"] = value.attachCount
    this["baseText"] = value.baseText
    this["baseAttach"] = value.baseAttachCount
    this["version"] = value.version
    this["savedAt"] = value.savedAt
    this["savedCount"] = value.savedCount
}
