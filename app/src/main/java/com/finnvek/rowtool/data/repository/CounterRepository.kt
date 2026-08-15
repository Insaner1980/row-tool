package com.finnvek.rowtool.data.repository

import androidx.room.withTransaction
import com.finnvek.rowtool.data.local.CounterHistoryEntity
import com.finnvek.rowtool.data.local.ProjectEntity
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.local.toDomain
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterMutationResult
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.HistoryChangeReason
import com.finnvek.rowtool.domain.model.ProjectValidation
import com.finnvek.rowtool.domain.model.ProjectValidationError
import com.finnvek.rowtool.domain.model.ProjectValidationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID

class CounterRepository(
    private val database: RowToolDatabase,
    private val clock: () -> Long = System::currentTimeMillis,
    private val idGenerator: () -> String = { UUID.randomUUID().toString() },
) {
    private val projectDao = database.projectDao()
    private val historyDao = database.counterHistoryDao()
    private val mutationMutex = Mutex()

    val projects: Flow<List<CounterProject>> =
        projectDao
            .observeAll()
            .map { entities -> entities.map { it.toDomain() } }

    fun observeProject(id: String): Flow<CounterProject?> =
        projectDao
            .observeById(id)
            .map { it?.toDomain() }

    fun observeCanUndo(id: String): Flow<Boolean> =
        historyDao
            .observeCountForProject(id)
            .map { it > 0 }

    suspend fun getProject(id: String): CounterProject? = projectDao.getById(id)?.toDomain()

    suspend fun createProject(
        name: String,
        counterUnit: CounterUnit,
        startValue: Int,
        targetCount: Long?,
        repeatLength: Int?,
    ): CounterProject =
        mutationMutex.withLock {
            val validated =
                requireValid(
                    name = name,
                    counterUnit = counterUnit,
                    count = startValue.toLong(),
                    startValue = startValue,
                    targetCount = targetCount,
                    repeatLength = repeatLength,
                )
            val timestamp = clock()
            val entity =
                ProjectEntity(
                    id = idGenerator(),
                    name = validated.name,
                    counterUnit = validated.counterUnit.name,
                    count = validated.count,
                    startValue = validated.startValue,
                    targetCount = validated.targetCount,
                    repeatLength = validated.repeatLength,
                    isArchived = false,
                    createdAt = timestamp,
                    updatedAt = timestamp,
                )
            projectDao.insert(entity)
            entity.toDomain()
        }

    suspend fun updateProject(
        id: String,
        name: String,
        counterUnit: CounterUnit,
        startValue: Int,
        targetCount: Long?,
        repeatLength: Int?,
    ): CounterProject? =
        mutationMutex.withLock {
            database.withTransaction {
                val current = projectDao.getById(id) ?: return@withTransaction null
                val validated =
                    requireValid(
                        name = name,
                        counterUnit = counterUnit,
                        count = current.count,
                        startValue = startValue,
                        targetCount = targetCount,
                        repeatLength = repeatLength,
                    )
                val updated =
                    current.copy(
                        name = validated.name,
                        counterUnit = validated.counterUnit.name,
                        startValue = validated.startValue,
                        targetCount = validated.targetCount,
                        repeatLength = validated.repeatLength,
                        updatedAt = clock(),
                    )
                projectDao.update(updated)
                updated.toDomain()
            }
        }

    suspend fun setArchived(
        id: String,
        archived: Boolean,
    ): Boolean =
        mutationMutex.withLock {
            database.withTransaction {
                val current = projectDao.getById(id) ?: return@withTransaction false
                if (current.isArchived != archived) {
                    projectDao.update(current.copy(isArchived = archived, updatedAt = clock()))
                }
                true
            }
        }

    suspend fun deleteProject(id: String) {
        mutationMutex.withLock {
            database.withTransaction { projectDao.deleteById(id) }
        }
    }

    suspend fun mutate(
        id: String,
        mutation: CounterMutation,
    ): CounterMutationResult =
        mutationMutex.withLock {
            database.withTransaction {
                val project =
                    projectDao.getById(id)
                        ?: return@withTransaction CounterMutationResult.ProjectMissing
                if (project.isArchived) {
                    return@withTransaction CounterMutationResult.ProjectArchived
                }

                val next = calculateMutation(project, mutation)
                if (next is CalculatedMutation.Invalid) {
                    return@withTransaction CounterMutationResult.Invalid(next.errors)
                }
                next as CalculatedMutation.Valid
                if (next.newCount == project.count) {
                    return@withTransaction CounterMutationResult.NoOp(project.count)
                }

                val timestamp = clock()
                historyDao.insert(
                    CounterHistoryEntity(
                        projectId = id,
                        previousCount = project.count,
                        newCount = next.newCount,
                        changeReason = next.reason.name,
                        createdAt = timestamp,
                    ),
                )
                projectDao.update(project.copy(count = next.newCount, updatedAt = timestamp))
                historyDao.trimToNewest(id, CounterConstants.MAX_HISTORY_ENTRIES)
                CounterMutationResult.Changed(project.count, next.newCount, next.reason)
            }
        }

    suspend fun undo(id: String): CounterMutationResult =
        mutationMutex.withLock {
            database.withTransaction {
                val project =
                    projectDao.getById(id)
                        ?: return@withTransaction CounterMutationResult.ProjectMissing
                if (project.isArchived) {
                    return@withTransaction CounterMutationResult.ProjectArchived
                }
                val history =
                    historyDao.getLatest(id)
                        ?: return@withTransaction CounterMutationResult.NoOp(project.count)
                val reason =
                    HistoryChangeReason.entries.firstOrNull { it.name == history.changeReason }
                        ?: HistoryChangeReason.MANUAL_SET
                projectDao.update(project.copy(count = history.previousCount, updatedAt = clock()))
                historyDao.deleteById(history.id)
                CounterMutationResult.Changed(project.count, history.previousCount, reason)
            }
        }

    private fun calculateMutation(
        project: ProjectEntity,
        mutation: CounterMutation,
    ): CalculatedMutation =
        when (mutation) {
            CounterMutation.Increment -> {
                CalculatedMutation.Valid(
                    newCount = (project.count + 1).coerceAtMost(CounterConstants.MAX_COUNT),
                    reason = HistoryChangeReason.INCREMENT,
                )
            }

            CounterMutation.Decrement -> {
                CalculatedMutation.Valid(
                    newCount = (project.count - 1).coerceAtLeast(0),
                    reason = HistoryChangeReason.DECREMENT,
                )
            }

            CounterMutation.Reset -> {
                CalculatedMutation.Valid(
                    newCount = project.startValue.toLong(),
                    reason = HistoryChangeReason.RESET,
                )
            }

            is CounterMutation.ManualSet -> {
                if (mutation.count in 0..CounterConstants.MAX_COUNT) {
                    CalculatedMutation.Valid(mutation.count, HistoryChangeReason.MANUAL_SET)
                } else {
                    CalculatedMutation.Invalid(setOf(ProjectValidationError.INVALID_COUNT))
                }
            }
        }

    private fun requireValid(
        name: String,
        counterUnit: CounterUnit,
        count: Long,
        startValue: Int,
        targetCount: Long?,
        repeatLength: Int?,
    ) = when (
        val result =
            ProjectValidation.validate(
                name = name,
                counterUnit = counterUnit,
                count = count,
                startValue = startValue,
                targetCount = targetCount,
                repeatLength = repeatLength,
            )
    ) {
        is ProjectValidationResult.Valid -> result.value

        is ProjectValidationResult.Invalid -> throw IllegalArgumentException(
            "Invalid project values: ${result.errors.joinToString()}",
        )
    }

    private sealed interface CalculatedMutation {
        data class Valid(
            val newCount: Long,
            val reason: HistoryChangeReason,
        ) : CalculatedMutation

        data class Invalid(
            val errors: Set<ProjectValidationError>,
        ) : CalculatedMutation
    }
}
