package com.finnvek.rowtool.data.repository

import androidx.room.withTransaction
import com.finnvek.rowtool.data.local.CounterHistoryEffectEntity
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
import com.finnvek.rowtool.domain.model.ReminderRules
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
    private val additionalDao = database.additionalCounterDao()
    private val effectDao = database.counterHistoryEffectDao()
    private val mutationMutex = Mutex()

    val additionalCounters = AdditionalCounterStore(database, mutationMutex, clock, idGenerator)
    val repeatSettings = RepeatSettingsStore(database, mutationMutex, clock)
    val reminders = ReminderStore(database, mutationMutex, idGenerator)
    val notes = ProjectNoteStore(database, mutationMutex, clock)
    val history = RecentHistoryStore(historyDao)
    val copySetups =
        CopySetupStore(database, mutationMutex, idGenerator) { values, id ->
            insertNewProject(
                values.name,
                values.counterUnit,
                values.startValue,
                values.targetCount,
                values.repeatLength,
                values.repeatStartCount,
                id,
            )
        }

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
        repeatStartCount: Long? = if (repeatLength != null) 1L else null,
    ): CounterProject =
        mutationMutex.withLock {
            database.withTransaction {
                insertNewProject(name, counterUnit, startValue, targetCount, repeatLength, repeatStartCount)
            }
        }

    private suspend fun insertNewProject(
        name: String,
        counterUnit: CounterUnit,
        startValue: Int,
        targetCount: Long?,
        repeatLength: Int?,
        repeatStartCount: Long?,
        id: String = idGenerator(),
    ): CounterProject {
        if (projectDao.count() >= CounterConstants.MAX_PROJECTS_IN_BACKUP) {
            throw ProjectLimitReachedException()
        }
        val validated =
            requireValid(
                name = name,
                counterUnit = counterUnit,
                count = startValue.toLong(),
                startValue = startValue,
                targetCount = targetCount,
                repeatLength = repeatLength,
                repeatStartCount = repeatStartCount,
            )
        val timestamp = clock()
        val entity =
            ProjectEntity(
                id = id,
                name = validated.name,
                counterUnit = validated.counterUnit.name,
                count = validated.count,
                startValue = validated.startValue,
                targetCount = validated.targetCount,
                repeatLength = validated.repeatLength,
                isArchived = false,
                createdAt = timestamp,
                updatedAt = timestamp,
                repeatStartCount = validated.repeatStartCount,
            )
        projectDao.insert(entity)
        return entity.toDomain()
    }

    suspend fun updateProject(
        id: String,
        name: String,
        counterUnit: CounterUnit,
        startValue: Int,
        targetCount: Long?,
        repeatLength: Int?,
        repeatStartCount: Long? = if (repeatLength != null) 1L else null,
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
                        repeatStartCount = repeatStartCount,
                    )
                val candidate =
                    current.copy(
                        name = validated.name,
                        counterUnit = validated.counterUnit.name,
                        startValue = validated.startValue,
                        targetCount = validated.targetCount,
                        repeatLength = validated.repeatLength,
                        repeatStartCount = validated.repeatStartCount,
                    )
                if (candidate == current) {
                    return@withTransaction current.toDomain()
                }
                val updated = candidate.copy(updatedAt = clock())
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
        counterId: String? = null,
    ): CounterMutationResult =
        mutationMutex.withLock {
            database.withTransaction {
                val project = projectDao.getById(id) ?: return@withTransaction CounterMutationResult.ProjectMissing
                if (project.isArchived) return@withTransaction CounterMutationResult.ProjectArchived
                database.applyMutation(project, mutation, counterId, clock)
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
                effectDao.getForHistory(history.id).forEach { additionalDao.setCount(it.counterId, it.previousCount) }
                historyDao.deleteById(history.id)
                additionalDao.deleteUnreferenced()
                CounterMutationResult.Changed(project.count, history.previousCount, reason)
            }
        }
}

internal class ProjectLimitReachedException : IllegalStateException("Project limit reached")

private suspend fun RowToolDatabase.reachesReminder(
    projectId: String,
    newCount: Long,
    reason: HistoryChangeReason,
    counterId: String?,
): Boolean {
    if (counterId != null || reason != HistoryChangeReason.INCREMENT) return false
    return reminderDao().getForProject(projectId).any { reminder ->
        ReminderRules.status(reminder.toDomain(), newCount).dueCount == newCount
    }
}

private fun changedWithFeedback(
    previousCount: Long,
    newCount: Long,
    reason: HistoryChangeReason,
    project: ProjectEntity,
    counterId: String?,
    reminderReached: Boolean,
): CounterMutationResult.Changed {
    val main = project.takeIf { counterId == null }
    return CounterMutationResult.Changed(
        previousCount = previousCount,
        newCount = newCount,
        reason = reason,
        repeatLength = main?.repeatLength,
        repeatStartCount = main?.repeatStartCount,
        targetCount = main?.targetCount,
        reminderReached = reminderReached,
    )
}

private fun requireValid(
    name: String,
    counterUnit: CounterUnit,
    count: Long,
    startValue: Int,
    targetCount: Long?,
    repeatLength: Int?,
    repeatStartCount: Long?,
) = when (
    val result =
        ProjectValidation.validate(
            name = name,
            counterUnit = counterUnit,
            count = count,
            startValue = startValue,
            targetCount = targetCount,
            repeatLength = repeatLength,
            repeatStartCount = repeatStartCount,
        )
) {
    is ProjectValidationResult.Valid -> result.value

    is ProjectValidationResult.Invalid -> throw IllegalArgumentException(
        "Invalid project values: ${result.errors.joinToString()}",
    )
}

private fun calculateMutation(
    count: Long,
    resetValue: Long,
    mutation: CounterMutation,
): CalculatedMutation =
    when (mutation) {
        CounterMutation.Increment -> {
            CalculatedMutation.Valid(
                newCount = (count + 1).coerceAtMost(CounterConstants.MAX_COUNT),
                reason = HistoryChangeReason.INCREMENT,
            )
        }

        CounterMutation.Decrement -> {
            CalculatedMutation.Valid(
                newCount = (count - 1).coerceAtLeast(CounterConstants.MIN_COUNT),
                reason = HistoryChangeReason.DECREMENT,
            )
        }

        CounterMutation.Reset -> {
            CalculatedMutation.Valid(
                newCount = resetValue,
                reason = HistoryChangeReason.RESET,
            )
        }

        is CounterMutation.ManualSet -> {
            if (mutation.count in CounterConstants.MIN_COUNT..CounterConstants.MAX_COUNT) {
                CalculatedMutation.Valid(mutation.count, HistoryChangeReason.MANUAL_SET)
            } else {
                CalculatedMutation.Invalid(setOf(ProjectValidationError.INVALID_COUNT))
            }
        }
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

private suspend fun RowToolDatabase.applyMutation(
    project: ProjectEntity,
    mutation: CounterMutation,
    counterId: String?,
    clock: () -> Long,
): CounterMutationResult {
    val id = project.id
    val additionalDao = additionalCounterDao()
    val effectDao = counterHistoryEffectDao()
    val projectDao = projectDao()
    val historyDao = counterHistoryDao()
    val counter = counterId?.let { additionalDao.getById(id, it)?.takeUnless { item -> item.isDeleted } }
    val previousCount = counter?.count ?: project.count
    val next = calculateMutation(previousCount, if (counter == null) project.startValue.toLong() else 0L, mutation)
    return when {
        counterId != null && counter == null -> {
            CounterMutationResult.CounterMissing
        }

        next is CalculatedMutation.Invalid -> {
            CounterMutationResult.Invalid(next.errors)
        }

        next is CalculatedMutation.Valid && next.newCount == previousCount -> {
            CounterMutationResult.NoOp(previousCount)
        }

        else -> {
            next as CalculatedMutation.Valid
            val timestamp = clock()
            val mainCount = if (counter == null) next.newCount else project.count
            val historyId =
                historyDao.insert(
                    CounterHistoryEntity(
                        projectId = id,
                        previousCount = project.count,
                        newCount = mainCount,
                        changeReason = next.reason.name,
                        createdAt = timestamp,
                    ),
                )
            val effects =
                if (counter != null) {
                    listOf(CounterHistoryEffectEntity(historyId, counter.id, counter.count, next.newCount))
                } else {
                    followerEffects(id, historyId, mainCount - project.count)
                }
            effectDao.insertAll(effects)
            effects.forEach { additionalDao.setCount(it.counterId, it.newCount) }
            projectDao.update(project.copy(count = mainCount, updatedAt = timestamp))
            historyDao.trimToNewest(id, CounterConstants.MAX_HISTORY_ENTRIES)
            additionalDao.deleteUnreferenced()
            val reminderReached = reachesReminder(id, next.newCount, next.reason, counterId)
            changedWithFeedback(previousCount, next.newCount, next.reason, project, counterId, reminderReached)
        }
    }
}

private suspend fun RowToolDatabase.followerEffects(
    id: String,
    historyId: Long,
    delta: Long,
): List<CounterHistoryEffectEntity> =
    additionalCounterDao().getActive(id).filter { it.followsMain }.mapNotNull { item ->
        val newCount = (item.count + delta).coerceIn(CounterConstants.MIN_COUNT, CounterConstants.MAX_COUNT)
        if (newCount == item.count) null else CounterHistoryEffectEntity(historyId, item.id, item.count, newCount)
    }
