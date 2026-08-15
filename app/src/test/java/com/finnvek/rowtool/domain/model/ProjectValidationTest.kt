package com.finnvek.rowtool.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ProjectValidationTest {
    @Test
    fun blankNameIsRejected() {
        assertInvalid(ProjectValidationError.NAME_BLANK, validate(name = "  \t"))
    }

    @Test
    fun surroundingWhitespaceIsTrimmed() {
        val result = validate(name = "  Summer shawl  ")

        assertTrue(result is ProjectValidationResult.Valid)
        assertEquals("Summer shawl", (result as ProjectValidationResult.Valid).value.name)
    }

    @Test
    fun sixtyUnicodeCharactersAreAccepted() {
        val result = validate(name = "🧶".repeat(60))

        assertTrue(result is ProjectValidationResult.Valid)
    }

    @Test
    fun moreThanSixtyUnicodeCharactersAreRejected() {
        assertInvalid(
            ProjectValidationError.NAME_TOO_LONG,
            validate(name = "🧶".repeat(61)),
        )
    }

    @Test
    fun startValueMayBeZeroOrOne() {
        assertTrue(validate(startValue = 0) is ProjectValidationResult.Valid)
        assertTrue(validate(startValue = 1) is ProjectValidationResult.Valid)
    }

    @Test
    fun otherStartValuesAreRejected() {
        assertInvalid(ProjectValidationError.INVALID_START_VALUE, validate(startValue = -1))
        assertInvalid(ProjectValidationError.INVALID_START_VALUE, validate(startValue = 2))
    }

    @Test
    fun targetMayBeAbsentOrWithinBounds() {
        assertTrue(validate(targetCount = null) is ProjectValidationResult.Valid)
        assertTrue(validate(targetCount = 1) is ProjectValidationResult.Valid)
        assertTrue(validate(targetCount = CounterConstants.MAX_COUNT) is ProjectValidationResult.Valid)
        assertTrue(validate(count = 200, targetCount = 100) is ProjectValidationResult.Valid)
    }

    @Test
    fun targetOutsideBoundsIsRejected() {
        assertInvalid(ProjectValidationError.INVALID_TARGET, validate(targetCount = 0))
        assertInvalid(
            ProjectValidationError.INVALID_TARGET,
            validate(targetCount = CounterConstants.MAX_COUNT + 1),
        )
    }

    @Test
    fun repeatMayBeAbsentOrWithinBounds() {
        assertTrue(validate(repeatLength = null) is ProjectValidationResult.Valid)
        assertTrue(validate(repeatLength = 2) is ProjectValidationResult.Valid)
        assertTrue(validate(repeatLength = 999) is ProjectValidationResult.Valid)
    }

    @Test
    fun repeatOutsideBoundsIsRejected() {
        assertInvalid(ProjectValidationError.INVALID_REPEAT_LENGTH, validate(repeatLength = 1))
        assertInvalid(ProjectValidationError.INVALID_REPEAT_LENGTH, validate(repeatLength = 1_000))
    }

    @Test
    fun countMayUseBothBoundaries() {
        assertTrue(validate(count = 0) is ProjectValidationResult.Valid)
        assertTrue(validate(count = CounterConstants.MAX_COUNT) is ProjectValidationResult.Valid)
    }

    @Test
    fun countOutsideBoundsIsRejected() {
        assertInvalid(ProjectValidationError.INVALID_COUNT, validate(count = -1))
        assertInvalid(
            ProjectValidationError.INVALID_COUNT,
            validate(count = CounterConstants.MAX_COUNT + 1),
        )
    }

    @Test
    fun validationReportsAllIndependentErrors() {
        val result =
            ProjectValidation.validate(
                name = " ",
                counterUnit = CounterUnit.ROWS,
                count = -1,
                startValue = 3,
                targetCount = 0,
                repeatLength = 1,
            )

        assertTrue(result is ProjectValidationResult.Invalid)
        val errors = (result as ProjectValidationResult.Invalid).errors
        assertEquals(
            setOf(
                ProjectValidationError.NAME_BLANK,
                ProjectValidationError.INVALID_COUNT,
                ProjectValidationError.INVALID_START_VALUE,
                ProjectValidationError.INVALID_TARGET,
                ProjectValidationError.INVALID_REPEAT_LENGTH,
            ),
            errors,
        )
    }

    @Test
    fun persistedCounterUnitFallsBackToRows() {
        assertEquals(CounterUnit.ROWS, CounterUnit.fromPersisted("ROWS"))
        assertEquals(CounterUnit.ROUNDS, CounterUnit.fromPersisted("ROUNDS"))
        assertEquals(CounterUnit.ROWS, CounterUnit.fromPersisted("future-value"))
        assertEquals(CounterUnit.ROWS, CounterUnit.fromPersisted(""))
    }

    private fun validate(
        name: String = "Project",
        count: Long = 0,
        startValue: Int = 0,
        targetCount: Long? = null,
        repeatLength: Int? = null,
    ): ProjectValidationResult =
        ProjectValidation.validate(
            name = name,
            counterUnit = CounterUnit.ROWS,
            count = count,
            startValue = startValue,
            targetCount = targetCount,
            repeatLength = repeatLength,
        )

    private fun assertInvalid(
        expected: ProjectValidationError,
        result: ProjectValidationResult,
    ) {
        assertTrue(result is ProjectValidationResult.Invalid)
        assertTrue(expected in (result as ProjectValidationResult.Invalid).errors)
        assertFalse(result.errors.isEmpty())
    }
}
