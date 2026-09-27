package com.finnvek.rowtool.data.repository

import androidx.room.withTransaction
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.local.toDomain
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.ProjectValidation
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RepeatSettingsStore internal constructor(
    private val database: RowToolDatabase,
    private val mutationMutex: Mutex,
    private val clock: () -> Long,
) {
    suspend fun save(
        projectId: String,
        repeatLength: Int?,
        repeatStartCount: Long?,
    ): CounterProject? =
        mutationMutex.withLock {
            database.withTransaction {
                val dao = database.projectDao()
                val current = dao.getById(projectId)?.takeUnless { it.isArchived } ?: return@withTransaction null
                require(
                    ProjectValidation.isRepeatValid(repeatLength) &&
                        ProjectValidation.isRepeatStartValid(repeatLength, repeatStartCount),
                )
                val candidate = current.copy(repeatLength = repeatLength, repeatStartCount = repeatStartCount)
                if (candidate == current) return@withTransaction current.toDomain()
                val updated = candidate.copy(updatedAt = clock())
                dao.update(updated)
                updated.toDomain()
            }
        }
}
