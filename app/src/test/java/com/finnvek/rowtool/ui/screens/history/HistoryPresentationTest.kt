package com.finnvek.rowtool.ui.screens.history

import com.finnvek.rowtool.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Locale
import java.util.TimeZone

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class HistoryPresentationTest {
    @Test fun unknownReasonUsesNeutralLabel() {
        assertEquals(R.string.history_change, historyReason("old"))
        assertEquals(R.string.history_increment, historyReason("INCREMENT"))
        assertEquals(R.string.history_decrement, historyReason("DECREMENT"))
        assertEquals(R.string.history_set, historyReason("MANUAL_SET"))
        assertEquals(R.string.history_reset, historyReason("RESET"))
    }

    @Test fun timeUsesLocaleZoneSecondsAndClockPreferenceWithoutInventingMissingTime() {
        val zone = TimeZone.getTimeZone("UTC")
        assertNull(historyTime(null, Locale.US, true, zone))
        assertNull(historyTime(-1, Locale.US, true, zone))
        assertNull(historyTime(Long.MAX_VALUE, Locale.US, true, zone))
        assertTrue(historyTime(45296000, Locale.US, true, zone)!!.contains("12:34:56"))
        assertTrue(historyTime(45296000, Locale.US, false, zone)!!.contains("PM"))
        assertTrue(historyTime(45296000, Locale.US, true, TimeZone.getTimeZone("GMT+02:00"))!!.contains("14:34:56"))
    }
}
