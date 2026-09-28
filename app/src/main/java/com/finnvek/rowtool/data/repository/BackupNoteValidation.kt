package com.finnvek.rowtool.data.repository

import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.ProjectNoteRules

internal fun validateBackupNotes(
    projects: List<CounterProject>,
    notes: List<BackupNote>,
): BackupValidationError? {
    val owners = projects.map { it.id }.toSet()
    val duplicate = notes.map { it.projectId }.toSet().size != notes.size
    val invalid =
        notes.any {
            it.projectId !in owners || !ProjectNoteRules.validContent(it.text) || it.savedAt < 0 ||
                (it.savedCount != null && it.savedCount !in CounterConstants.MIN_COUNT..CounterConstants.MAX_COUNT)
        }
    return BackupValidationError.INVALID_NOTE.takeIf { duplicate || invalid }
}
