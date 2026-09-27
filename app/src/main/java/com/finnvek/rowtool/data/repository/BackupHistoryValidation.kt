package com.finnvek.rowtool.data.repository

import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.HistoryChangeReason
import com.finnvek.rowtool.domain.model.ProjectValidation

internal fun validateBackupCounters(
    projects: List<CounterProject>,
    counters: List<BackupCounter>,
): BackupValidationError? {
    val projectIds = projects.map { it.id }.toSet()
    val valid =
        counters.map { it.id }.toSet().size == counters.size &&
            counters.all {
                it.id.isNotBlank() && it.projectId in projectIds && ProjectValidation.nameErrors(it.name).isEmpty() && validCount(it.count)
            }
    return if (valid) null else BackupValidationError.INVALID_COUNTER
}

internal fun validateBackupHistory(
    projects: List<CounterProject>,
    counters: List<BackupCounter>,
    history: List<BackupHistory>,
): BackupValidationError? {
    val projectCounts = projects.associate { it.id to it.count }.toMutableMap()
    val counterCounts = counters.associate { it.id to it.count }.toMutableMap()
    val owners = counters.associate { it.id to it.projectId }
    val counts = history.groupingBy { it.projectId }.eachCount()
    val identitiesValid =
        history.map { it.id }.toSet().size == history.size &&
            counts.values.all { it <= CounterConstants.MAX_HISTORY_ENTRIES }
    val chainValid =
        history.sortedByDescending { it.id }.all { entry ->
            val valid =
                validHistoryEntry(entry) && projectCounts[entry.projectId] == entry.newCount &&
                    entry.effects.all { owners[it.counterId] == entry.projectId && counterCounts[it.counterId] == it.newCount }
            if (valid) {
                entry.effects.forEach { counterCounts[it.counterId] = it.previousCount }
                projectCounts[entry.projectId] = entry.previousCount
            }
            valid
        }
    val referencedIds = history.flatMap { it.effects }.map { it.counterId }.toSet()
    val hiddenCountersValid = counters.all { !it.isDeleted || it.id in referencedIds }
    return if (identitiesValid && chainValid && hiddenCountersValid) null else BackupValidationError.INVALID_HISTORY
}

private fun validHistoryEntry(entry: BackupHistory): Boolean {
    val reason = HistoryChangeReason.entries.firstOrNull { it.name == entry.changeReason } ?: return false
    val effectsValid =
        entry.effects
            .map { it.counterId }
            .toSet()
            .size == entry.effects.size &&
            entry.effects.all { validCount(it.previousCount) && validCount(it.newCount) && it.previousCount != it.newCount }
    val delta = entry.newCount - entry.previousCount
    val changeValid =
        if (delta == 0L) {
            entry.effects.singleOrNull()?.let { validChange(it.previousCount, it.newCount, reason, additional = true) } == true
        } else {
            validChange(entry.previousCount, entry.newCount, reason, additional = false) &&
                entry.effects.all {
                    it.newCount == (it.previousCount + delta).coerceIn(CounterConstants.MIN_COUNT, CounterConstants.MAX_COUNT)
                }
        }
    return entry.id > 0 && validCount(entry.previousCount) && validCount(entry.newCount) && effectsValid && changeValid
}

private fun validChange(
    previous: Long,
    next: Long,
    reason: HistoryChangeReason,
    additional: Boolean,
): Boolean =
    when (reason) {
        HistoryChangeReason.INCREMENT -> next == previous + 1
        HistoryChangeReason.DECREMENT -> next == previous - 1
        HistoryChangeReason.MANUAL_SET -> next != previous
        HistoryChangeReason.RESET -> next == 0L || (!additional && next == 1L)
    }

private fun validCount(count: Long): Boolean = count in CounterConstants.MIN_COUNT..CounterConstants.MAX_COUNT
