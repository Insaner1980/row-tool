package com.finnvek.rowtool.domain.counter

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CounterCalculatorsTest {
    @Test
    fun absentRepeatProducesNoProgress() {
        assertNull(RepeatProgressCalculator.calculate(count = 7, repeatLength = null))
    }

    @Test
    fun repeatPositionsMatchTheCounterContract() {
        val cases =
            listOf(
                Triple(0L, 0, 0L),
                Triple(1L, 1, 0L),
                Triple(5L, 5, 0L),
                Triple(6L, 6, 1L),
                Triple(7L, 1, 1L),
                Triple(12L, 6, 2L),
                Triple(13L, 1, 2L),
                Triple(999_999L, 3, 166_666L),
            )

        cases.forEach { (count, expectedStep, expectedCompleted) ->
            val progress = RepeatProgressCalculator.calculate(count, repeatLength = 6)
            requireNotNull(progress)
            assertEquals("step for $count", expectedStep, progress.currentStep)
            assertEquals("completed for $count", expectedCompleted, progress.completedRepeats)
            assertEquals(6, progress.repeatLength)
        }
    }

    @Test
    fun absentTargetProducesNoProgress() {
        assertNull(TargetProgressCalculator.calculate(count = 7, targetCount = null))
    }

    @Test
    fun targetProgressStartsAtZero() {
        val progress = requireNotNull(TargetProgressCalculator.calculate(count = 0, targetCount = 120))

        assertEquals(0f, progress.fraction, 0f)
        assertFalse(progress.isReached)
    }

    @Test
    fun targetProgressRepresentsCountsBelowTarget() {
        val progress = requireNotNull(TargetProgressCalculator.calculate(count = 42, targetCount = 120))

        assertEquals(0.35f, progress.fraction, 0.0001f)
        assertFalse(progress.isReached)
    }

    @Test
    fun targetIsReachedExactlyAtTarget() {
        val progress = requireNotNull(TargetProgressCalculator.calculate(count = 120, targetCount = 120))

        assertEquals(1f, progress.fraction, 0f)
        assertTrue(progress.isReached)
    }

    @Test
    fun visualTargetProgressIsClampedAboveTarget() {
        val progress = requireNotNull(TargetProgressCalculator.calculate(count = 150, targetCount = 120))

        assertEquals(1f, progress.fraction, 0f)
        assertTrue(progress.isReached)
        assertEquals(150L, progress.count)
        assertEquals(120L, progress.targetCount)
    }
}
