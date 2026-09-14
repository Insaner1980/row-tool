package com.finnvek.rowtool.data.repository

import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.ProjectValidation
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidatedBackupTest {
    @Test
    fun validProjectsAreNormalizedAndPreserveAcceptedValues() {
        val projects =
            listOf(
                project().copy(id = " plain id ", name = "  Project  ", createdAt = Long.MIN_VALUE),
                project().copy(id = "plain id", isArchived = true, updatedAt = Long.MAX_VALUE),
            )

        val backup = (ValidatedBackup.create(Long.MIN_VALUE, projects) as BackupDecodeResult.Valid).backup

        assertEquals(Long.MIN_VALUE, backup.exportedAt)
        assertEquals(listOf(projects[0].copy(name = "Project"), projects[1]), backup.projects)
    }

    @Test
    fun emptyAndMaximumSizeCollectionsAreAccepted() {
        val projects = List(CounterConstants.MAX_PROJECTS_IN_BACKUP) { project().copy(id = "p$it") }

        assertEquals(emptyList<CounterProject>(), (ValidatedBackup.create(0, emptyList()) as BackupDecodeResult.Valid).backup.projects)
        assertEquals(projects, (ValidatedBackup.create(0, projects) as BackupDecodeResult.Valid).backup.projects)
    }

    @Test
    fun domainBoundaryValuesAreAccepted() {
        val projects =
            listOf(
                project().copy(
                    count = CounterConstants.MIN_COUNT,
                    targetCount = ProjectValidation.MIN_TARGET_COUNT,
                    repeatLength = ProjectValidation.MIN_REPEAT_LENGTH,
                ),
                project().copy(
                    id = "maximum",
                    name = "x".repeat(ProjectValidation.MAX_NAME_CODE_POINTS),
                    counterUnit = CounterUnit.ROUNDS,
                    count = CounterConstants.MAX_COUNT,
                    startValue = 1,
                    targetCount = CounterConstants.MAX_COUNT,
                    repeatLength = ProjectValidation.MAX_REPEAT_LENGTH,
                ),
            )

        assertEquals(projects, (ValidatedBackup.create(0, projects) as BackupDecodeResult.Valid).backup.projects)
    }

    @Test
    fun blankAndDuplicateIdsAreRejected() {
        assertInvalid(listOf(project().copy(id = " \t")))
        assertInvalid(listOf(project(), project()), BackupValidationError.DUPLICATE_PROJECT_ID)
    }

    @Test
    fun tooManyProjectsAreRejected() {
        val projects = List(CounterConstants.MAX_PROJECTS_IN_BACKUP + 1) { project().copy(id = "p$it") }

        assertInvalid(projects, BackupValidationError.TOO_MANY_PROJECTS)
    }

    @Test
    fun invalidNamesAreRejected() {
        assertInvalid(listOf(project().copy(name = "  ")))
        assertInvalid(listOf(project().copy(name = "x".repeat(ProjectValidation.MAX_NAME_CODE_POINTS + 1))))
    }

    @Test
    fun invalidCountsAndStartValuesAreRejected() {
        assertInvalid(listOf(project().copy(count = CounterConstants.MIN_COUNT - 1)))
        assertInvalid(listOf(project().copy(count = CounterConstants.MAX_COUNT + 1)))
        assertInvalid(listOf(project().copy(startValue = -1)))
        assertInvalid(listOf(project().copy(startValue = 2)))
    }

    @Test
    fun invalidTargetsAndRepeatsAreRejected() {
        assertInvalid(listOf(project().copy(targetCount = 0)))
        assertInvalid(listOf(project().copy(targetCount = CounterConstants.MAX_COUNT + 1)))
        assertInvalid(listOf(project().copy(repeatLength = 0)))
        assertInvalid(listOf(project().copy(repeatLength = 1)))
        assertInvalid(listOf(project().copy(repeatLength = ProjectValidation.MAX_REPEAT_LENGTH + 1)))
    }

    @Test
    fun changingOriginalListDoesNotChangeValidatedProjects() {
        val original = mutableListOf(project(), project().copy(id = "second"))
        val expected = original.toList()
        val backup = (ValidatedBackup.create(0, original) as BackupDecodeResult.Valid).backup

        original[0] = project().copy(repeatLength = 0)
        original.clear()
        original += project().copy(id = "")

        assertEquals(expected, backup.projects)
    }

    private fun assertInvalid(
        projects: List<CounterProject>,
        error: BackupValidationError = BackupValidationError.INVALID_PROJECT,
    ) {
        assertEquals(BackupDecodeResult.Invalid(error), ValidatedBackup.create(0, projects))
    }

    private fun project(): CounterProject =
        CounterProject(
            id = "project",
            name = "Project",
            counterUnit = CounterUnit.ROWS,
            count = 0,
            startValue = 0,
            targetCount = null,
            repeatLength = null,
            isArchived = false,
            createdAt = 0,
            updatedAt = 0,
        )
}
