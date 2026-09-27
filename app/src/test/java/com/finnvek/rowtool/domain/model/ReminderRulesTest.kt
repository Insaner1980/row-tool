package com.finnvek.rowtool.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ReminderRulesTest {
    private fun reminder(
        first: Long = 32,
        interval: Long? = null,
        acknowledged: Long? = null,
    ) = Reminder("r", "p", "  Check work  ", first, interval, true, acknowledged, 1)

    @Test
    fun oneTimeReminderPersistsUntilAcknowledged() {
        assertNull(ReminderRules.status(reminder(), 31).dueCount)
        assertEquals(32L, ReminderRules.status(reminder(), 31).nextCount)
        assertEquals(32L, ReminderRules.status(reminder(), 32).dueCount)
        assertEquals(32L, ReminderRules.status(reminder(), 50).dueCount)
        assertNull(ReminderRules.status(reminder(acknowledged = 32), 50).dueCount)
        assertNull(ReminderRules.status(reminder(acknowledged = 32), 0).dueCount)
    }

    @Test
    fun recurringReminderShowsLatestAndMissedOccurrences() {
        val recurring = reminder(interval = 6)
        assertEquals(32L, ReminderRules.status(recurring, 32).dueCount)
        assertEquals(32L, ReminderRules.status(recurring, 33).dueCount)
        assertEquals(38L, ReminderRules.status(recurring, 38).dueCount)
        assertEquals(1L, ReminderRules.status(recurring, 38).earlierUnacknowledged)
        assertEquals(50L, ReminderRules.status(recurring, 50).dueCount)
        assertEquals(3L, ReminderRules.status(recurring, 50).earlierUnacknowledged)
        assertNull(ReminderRules.status(reminder(interval = 6, acknowledged = 38), 38).dueCount)
        assertEquals(44L, ReminderRules.status(reminder(interval = 6, acknowledged = 38), 44).dueCount)
    }

    @Test
    fun boundsIntervalOneAndResetRemainSafe() {
        val every = reminder(first = 1, interval = 1, acknowledged = 3)
        assertNull(ReminderRules.status(every, 0).dueCount)
        assertNull(ReminderRules.status(every, 2).dueCount)
        assertEquals(4L, ReminderRules.status(every, 4).dueCount)
        assertEquals(
            CounterConstants.MAX_COUNT,
            ReminderRules.status(reminder(first = CounterConstants.MAX_COUNT, interval = 1), CounterConstants.MAX_COUNT).dueCount,
        )
        assertNull(ReminderRules.status(reminder(first = CounterConstants.MAX_COUNT, interval = 1), CounterConstants.MAX_COUNT).nextCount)
        assertTrue(ReminderRules.validate("🧶".repeat(200), 1, 1))
        assertFalse(ReminderRules.validate("x".repeat(201), 1, null))
        assertFalse(ReminderRules.validate(" ", 1, null))
        assertFalse(ReminderRules.validate("ok", 0, null))
        assertFalse(ReminderRules.validate("ok", 1, 0))
    }
}
