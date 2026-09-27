package com.finnvek.rowtool.data.repository

import androidx.room.withTransaction
import com.finnvek.rowtool.data.local.AdditionalCounterEntity
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.local.toDomain
import com.finnvek.rowtool.domain.model.AdditionalCounter
import com.finnvek.rowtool.domain.model.ProjectValidation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AdditionalCounterStore internal constructor(
    private val database: RowToolDatabase,
    private val mutationMutex: Mutex,
    private val clock: () -> Long,
    private val idGenerator: () -> String,
) {
    private val projectDao = database.projectDao()
    private val additionalDao = database.additionalCounterDao()

    fun observe(projectId: String): Flow<List<AdditionalCounter>> =
        additionalDao.observeActive(projectId).map { counters -> counters.map { it.toDomain() } }

    suspend fun save(
        projectId: String,
        counterId: String?,
        name: String,
        followsMain: Boolean = false,
    ): Boolean =
        mutationMutex.withLock {
            database.withTransaction {
                val project = projectDao.getById(projectId)?.takeUnless { it.isArchived } ?: return@withTransaction false
                require(ProjectValidation.nameErrors(name).isEmpty())
                val normalizedName = ProjectValidation.normalizeName(name)
                if (counterId == null) {
                    additionalDao.insert(AdditionalCounterEntity(idGenerator(), projectId, normalizedName, 0, followsMain))
                } else {
                    val current =
                        additionalDao.getById(projectId, counterId)?.takeUnless { it.isDeleted }
                            ?: return@withTransaction false
                    val updated = current.copy(name = normalizedName, followsMain = followsMain)
                    if (updated == current) return@withTransaction true
                    additionalDao.update(updated)
                }
                projectDao.update(project.copy(updatedAt = clock()))
                true
            }
        }

    suspend fun delete(
        projectId: String,
        counterId: String,
    ): Boolean =
        mutationMutex.withLock {
            database.withTransaction {
                val project = projectDao.getById(projectId)?.takeUnless { it.isArchived } ?: return@withTransaction false
                val counter =
                    additionalDao.getById(projectId, counterId)?.takeUnless { it.isDeleted }
                        ?: return@withTransaction false
                additionalDao.update(counter.copy(isDeleted = true))
                additionalDao.deleteUnreferenced()
                projectDao.update(project.copy(updatedAt = clock()))
                true
            }
        }
}
