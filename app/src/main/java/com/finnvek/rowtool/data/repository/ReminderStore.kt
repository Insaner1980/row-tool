package com.finnvek.rowtool.data.repository

import androidx.room.withTransaction
import com.finnvek.rowtool.data.local.ReminderEntity
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.local.toDomain
import com.finnvek.rowtool.domain.model.Reminder
import com.finnvek.rowtool.domain.model.ReminderRules
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
        message: String,
        firstCount: Long,
        intervalCount: Long?,
        enabled: Boolean,
        creationId: String? = null,
    ): Reminder? {
        require(ReminderRules.validate(message, firstCount, intervalCount))
        return mutationMutex.withLock {
            database.withTransaction {
                if (projects.getById(projectId)?.isArchived != false) return@withTransaction null
                val normalized = ReminderRules.normalizeMessage(message)
                if (id == null) {
                    val newId = creationId ?: idGenerator()
                    require(newId.isNotBlank())
                    val existing = dao.getById(projectId, newId)
                    if (existing != null) {
                        return@withTransaction existing
                            .takeIf {
                                it.message == normalized && it.firstCount == firstCount && it.intervalCount == intervalCount &&
                                    it.enabled == enabled
                            }?.toDomain()
                    }
                    val created = ReminderEntity(newId, projectId, normalized, firstCount, intervalCount, enabled, null, 1)
                    dao.insert(created)
                    created.toDomain()
                } else {
                    val current = dao.getById(projectId, id) ?: return@withTransaction null
                    if (current.revision != expectedRevision) return@withTransaction null
                    val changedSchedule = current.firstCount != firstCount || current.intervalCount != intervalCount
                    val candidate =
                        current.copy(
                            message = normalized,
                            firstCount = firstCount,
                            intervalCount = intervalCount,
                            enabled = enabled,
                            acknowledgedThrough = if (changedSchedule) null else current.acknowledgedThrough,
                        )
                    if (candidate == current) return@withTransaction current.toDomain()
                    val updated = candidate.copy(revision = current.revision + 1)
                    dao.update(updated)
                    updated.toDomain()
                }
            }
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
        mutationMutex.withLock {
            database.withTransaction {
                if (projects.getById(projectId)?.isArchived != false) return@withTransaction false
                val current = dao.getById(projectId, id) ?: return@withTransaction false
                if (current.revision != expectedRevision) return@withTransaction false
                dao.update(current.copy(acknowledgedThrough = null, revision = current.revision + 1))
                true
            }
        }

    suspend fun delete(
        projectId: String,
        id: String,
        expectedRevision: Long,
    ): Boolean =
        mutationMutex.withLock {
            database.withTransaction {
                if (projects.getById(projectId)?.isArchived != false) return@withTransaction false
                val current = dao.getById(projectId, id) ?: return@withTransaction false
                if (current.revision != expectedRevision) return@withTransaction false
                dao.delete(projectId, id) == 1
            }
        }
}
