package com.finnvek.rowtool.data.repository

import androidx.room.withTransaction
import com.finnvek.rowtool.data.local.ProjectNoteEntity
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.local.toDomain
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.ProjectNote
import com.finnvek.rowtool.domain.model.ProjectNoteRules
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID

data class NoteSnapshot(
    val project: CounterProject?,
    val note: ProjectNote?,
)

sealed interface NoteWriteResult {
    data class Success(
        val note: ProjectNote?,
    ) : NoteWriteResult

    data object Conflict : NoteWriteResult

    data object Unavailable : NoteWriteResult

    data object Invalid : NoteWriteResult

    data object DeletionRequired : NoteWriteResult
}

class ProjectNoteStore(
    private val database: RowToolDatabase,
    private val mutationMutex: Mutex,
    private val clock: () -> Long,
) {
    private val dao = database.projectNoteDao()
    val projectIds = dao.observeProjectIds()

    fun observe(projectId: String) = dao.observe(projectId).map { it?.toDomain() }

    suspend fun load(projectId: String): NoteSnapshot =
        database.withTransaction {
            NoteSnapshot(database.projectDao().getById(projectId)?.toDomain(), dao.get(projectId)?.toDomain())
        }

    suspend fun save(
        projectId: String,
        expectedVersion: String?,
        text: String,
        attachCount: Boolean,
    ): NoteWriteResult {
        if (!ProjectNoteRules.withinLimit(text)) return NoteWriteResult.Invalid
        val normalized = ProjectNoteRules.normalize(text)
        return mutationMutex.withLock {
            database.withTransaction {
                saveInTransaction(projectId, expectedVersion, normalized, attachCount)
            }
        }
    }

    private suspend fun saveInTransaction(
        projectId: String,
        expectedVersion: String?,
        normalized: String,
        attachCount: Boolean,
    ): NoteWriteResult {
        val project = database.projectDao().getById(projectId)
        if (project == null || project.isArchived) return NoteWriteResult.Unavailable
        val current = dao.get(projectId)
        return when {
            current?.version != expectedVersion -> {
                NoteWriteResult.Conflict
            }

            normalized.isBlank() -> {
                if (current == null) NoteWriteResult.Success(null) else NoteWriteResult.DeletionRequired
            }

            current?.text == normalized && (current.savedCount != null) == attachCount -> {
                NoteWriteResult.Success(current.toDomain())
            }

            else -> {
                val saved =
                    ProjectNoteEntity(projectId, UUID.randomUUID().toString(), normalized, clock(), project.count.takeIf { attachCount })
                if (current == null) dao.insert(saved) else dao.update(saved)
                NoteWriteResult.Success(saved.toDomain())
            }
        }
    }

    suspend fun delete(
        projectId: String,
        expectedVersion: String,
    ): NoteWriteResult =
        mutationMutex.withLock {
            database.withTransaction {
                if (database.projectDao().getById(projectId)?.isArchived != false) return@withTransaction NoteWriteResult.Unavailable
                if (dao.get(projectId)?.version != expectedVersion) return@withTransaction NoteWriteResult.Conflict
                dao.delete(projectId)
                NoteWriteResult.Success(null)
            }
        }
}
