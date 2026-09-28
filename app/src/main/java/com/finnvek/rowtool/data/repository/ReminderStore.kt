package com.finnvek.rowtool.data.repository

import androidx.room.withTransaction
import com.finnvek.rowtool.data.local.ReminderEntity
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.local.toDomain
import com.finnvek.rowtool.domain.model.Reminder
import com.finnvek.rowtool.domain.model.ReminderRules
import com.finnvek.rowtool.domain.model.ReminderValues
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID

class ReminderStore(
    private val database: RowToolDatabase,
    private val mutationMutex: Mutex,
    private val idGenerator: () -> String = { UUID.randomUUID().toString() },
) {
    private val dao = database.reminderDao()
    private val projects = database.projectDao()

    fun observe(projectId: String): Flow<List<Reminder>> = dao.observe(projectId).map { rows -> rows.map { it.toDomain() } }

    suspend fun save(
        projectId: String,
        id: String?,
        expectedRevision: Long?,
        values: ReminderValues,
        creationId: String? = null,
    ): Reminder? {
        require(ReminderRules.validate(values.message, values.firstCount, values.intervalCount))
        val normalized = values.copy(message = ReminderRules.normalizeMessage(values.message))
        return mutationMutex.withLock {
            database.withTransaction {
                if (projects.getById(projectId)?.isArchived != false) return@withTransaction null
                if (id == null) {
                    create(projectId, creationId ?: idGenerator(), normalized)
                } else {
                    update(projectId, id, expectedRevision, normalized)
                }
            }
        }
    }

    private suspend fun create(
        projectId: String,
        id: String,
        values: ReminderValues,
    ): Reminder? {
        require(id.isNotBlank())
        val existing = dao.getById(projectId, id)
        if (existing != null) {
            return existing
                .takeIf {
                    it.message == values.message && it.firstCount == values.firstCount &&
                        it.intervalCount == values.intervalCount && it.enabled == values.enabled
                }?.toDomain()
        }
        val created = ReminderEntity(id, projectId, values.message, values.firstCount, values.intervalCount, values.enabled, null, 1)
        dao.insert(created)
        return created.toDomain()
    }

    private suspend fun update(
        projectId: String,
        id: String,
        expectedRevision: Long?,
        values: ReminderValues,
    ): Reminder? {
        val current = dao.getById(projectId, id)?.takeIf { it.revision == expectedRevision } ?: return null
        val changedSchedule = current.firstCount != values.firstCount || current.intervalCount != values.intervalCount
        val candidate =
            current.copy(
                message = values.message,
                firstCount = values.firstCount,
                intervalCount = values.intervalCount,
                enabled = values.enabled,
                acknowledgedThrough = if (changedSchedule) null else current.acknowledgedThrough,
            )
        return if (candidate == current) {
            current.toDomain()
        } else {
            val updated = candidate.copy(revision = current.revision + 1)
            dao.update(updated)
            updated.toDomain()
        }
    }

    suspend fun acknowledge(
        projectId: String,
        id: String,
        expectedRevision: Long,
        targetCount: Long,
    ): Boolean =
        mutationMutex.withLock {
            database.withTransaction {
                val project = projects.getById(projectId) ?: return@withTransaction false
                if (project.isArchived) return@withTransaction false
                val current = dao.getById(projectId, id) ?: return@withTransaction false
                val staleOrDisabled = !current.enabled || current.revision != expectedRevision
                val invalidTarget =
                    targetCount > project.count ||
                        !ReminderRules.validAcknowledgement(current.firstCount, current.intervalCount, targetCount)
                if (staleOrDisabled || invalidTarget) {
                    return@withTransaction false
                }
                if (current.acknowledgedThrough == null || targetCount > current.acknowledgedThrough) {
                    dao.update(current.copy(acknowledgedThrough = targetCount))
                }
                true
            }
        }

    suspend fun resetAcknowledgements(
        projectId: String,
        id: String,
        expectedRevision: Long,
    ): Boolean =
        mutateCurrent(projectId, id, expectedRevision) { current ->
            dao.update(current.copy(acknowledgedThrough = null, revision = current.revision + 1))
            true
        }

    suspend fun delete(
        projectId: String,
        id: String,
        expectedRevision: Long,
    ): Boolean =
        mutateCurrent(projectId, id, expectedRevision) {
            dao.delete(projectId, id) == 1
        }

    private suspend fun mutateCurrent(
        projectId: String,
        id: String,
        expectedRevision: Long,
        mutation: suspend (ReminderEntity) -> Boolean,
    ): Boolean =
        mutationMutex.withLock {
            database.withTransaction {
                if (projects.getById(projectId)?.isArchived != false) return@withTransaction false
                val current = dao.getById(projectId, id) ?: return@withTransaction false
                if (current.revision != expectedRevision) return@withTransaction false
                mutation(current)
            }
        }
}
