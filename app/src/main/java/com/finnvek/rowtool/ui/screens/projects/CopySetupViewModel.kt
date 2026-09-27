package com.finnvek.rowtool.ui.screens.projects

import android.database.SQLException
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finnvek.rowtool.R
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.repository.CopyCounterSetup
import com.finnvek.rowtool.data.repository.CopySetup
import com.finnvek.rowtool.data.repository.CopySourceUnavailableException
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.data.repository.ProjectLimitReachedException
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

data class CopySetupState(
    val snapshot: CopySetup? = null,
    val loading: Boolean = false,
    val saving: Boolean = false,
    val completedId: String? = null,
    val error: Int? = null,
)

class CopySetupViewModel(
    private val sourceId: String,
    private val repository: CounterRepository,
    private val preferences: PreferencesRepository,
    private val saved: SavedStateHandle,
) : ViewModel() {
    private val mutableState =
        MutableStateFlow(
            CopySetupState(snapshot = saved.get<Bundle>("snapshot")?.toSetup(), completedId = saved["completed"]),
        )
    val state = mutableState.asStateFlow()

    init {
        if (state.value.snapshot == null) load()
        saved.get<Bundle>("pending")?.let { create(it.toValues()) }
    }

    fun load() {
        if (state.value.loading || state.value.snapshot != null) return
        mutableState.value = state.value.copy(loading = true, error = null)
        viewModelScope.launch {
            try {
                val snapshot = repository.copySetups.capture(sourceId)
                saved["snapshot"] = snapshot.toBundle()
                mutableState.value = CopySetupState(snapshot = snapshot)
            } catch (_: CopySourceUnavailableException) {
                mutableState.value = CopySetupState(error = R.string.copy_unavailable)
            } catch (_: SQLException) {
                mutableState.value = CopySetupState(error = R.string.copy_read_error)
            } catch (_: IOException) {
                mutableState.value = CopySetupState(error = R.string.copy_read_error)
            }
        }
    }

    fun create(values: ProjectEditorValues) {
        val request = state.value
        val snapshot = request.snapshot ?: return
        if (request.saving || request.completedId != null) return
        saved["pending"] = values.toBundle()
        mutableState.value = request.copy(saving = true, error = null)
        viewModelScope.launch {
            try {
                val project = repository.copySetups.create(snapshot, values)
                // Record commit before any cancellable preference/navigation work.
                saved["completed"] = project.id
                saved.remove<Bundle>("pending")
                mutableState.value = state.value.copy(completedId = project.id, saving = false)
                try {
                    preferences.setLastActiveProjectId(project.id)
                } catch (_: IOException) {
                    // The committed identity remains available for opening; never resubmit creation.
                }
            } catch (_: CopySourceUnavailableException) {
                fail(R.string.copy_unavailable)
            } catch (_: ProjectLimitReachedException) {
                fail(R.string.error_project_limit)
            } catch (_: IllegalArgumentException) {
                fail(R.string.copy_invalid)
            } catch (_: SQLException) {
                fail(R.string.error_database_write)
            }
        }
    }

    private fun fail(message: Int) {
        saved.remove<Bundle>("pending")
        mutableState.value = state.value.copy(saving = false, error = message)
    }
}

private fun ProjectEditorValues.toBundle() =
    Bundle().apply {
        putString("name", name)
        putString("unit", counterUnit.name)
        putInt("start", startValue)
        targetCount?.let { putLong("target", it) }
        repeatLength?.let { putInt("repeat", it) }
        repeatStartCount?.let { putLong("offset", it) }
    }

private fun Bundle.toValues() =
    ProjectEditorValues(
        getString("name").orEmpty(),
        CounterUnit.valueOf(requireNotNull(getString("unit"))),
        getInt("start"),
        if (containsKey("target")) getLong("target") else null,
        if (containsKey("repeat")) getInt("repeat") else null,
        if (containsKey("offset")) getLong("offset") else null,
    )

private fun CopySetup.toBundle() =
    Bundle().apply {
        putString("source", sourceId)
        putString("sourceName", sourceName)
        putString("generation", generation)
        putString("id", projectId)
        putBundle("settings", settings.toBundle())
        putStringArrayList("names", ArrayList(counters.map { it.name }))
        putBooleanArray("modes", counters.map { it.followsMain }.toBooleanArray())
    }

private fun Bundle.toSetup() =
    CopySetup(
        requireNotNull(getString("source")),
        requireNotNull(getString("sourceName")),
        requireNotNull(getString("generation")),
        requireNotNull(getString("id")),
        requireNotNull(getBundle("settings")).toValues(),
        requireNotNull(getStringArrayList("names")).mapIndexed { index, name ->
            CopyCounterSetup(name, requireNotNull(getBooleanArray("modes"))[index])
        },
    )
