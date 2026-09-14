package com.finnvek.rowtool.ui.screens.counter

import android.view.HapticFeedbackConstants
import androidx.compose.ui.unit.dp
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.theme.RowToolDimens
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CounterPresentationTest {
    @Test
    fun controlsKeepReferenceSizeWhenPrimaryPairFits() {
        listOf(288, 320, 360, 600).forEach { width ->
            assertEquals(1f, counterControlScale(width.dp), 0f)
        }
    }

    @Test
    fun narrowControlsFitWithoutNegativeSizesOrUndersizedTouchTargets() {
        listOf(-1, 0, 48, 96, 120, 200, 280).forEach { width ->
            val scale = counterControlScale(width.dp)
            val primaryTouchSize = RowToolDimens.CounterPrimaryTouchSize * scale
            assertTrue(scale.isFinite() && scale > 0f && scale <= 1f)
            assertTrue(primaryTouchSize >= RowToolDimens.MinimumTouchSize)
            if (width >= 96) {
                assertTrue(primaryTouchSize * 2 <= width.dp)
            }
        }
        assertEquals(280f / 288f, counterControlScale(280.dp), 0.0001f)
    }

    @Test
    fun counterUnitSelectsMatchingLabels() {
        assertEquals(
            CounterUnitResources(
                label = R.string.counter_rows_label,
                addDescription = R.string.counter_add_row,
                removeDescription = R.string.counter_remove_row,
                targetProgress = R.plurals.counter_target_rows,
            ),
            counterUnitResources(CounterUnit.ROWS),
        )
        assertEquals(
            CounterUnitResources(
                label = R.string.counter_rounds_label,
                addDescription = R.string.counter_add_round,
                removeDescription = R.string.counter_remove_round,
                targetProgress = R.plurals.counter_target_rounds,
            ),
            counterUnitResources(CounterUnit.ROUNDS),
        )
    }

    @Test
    fun countTextSizeRespondsToDigitsWidthAndFontScale() {
        assertEquals(115f, responsiveCountTextSize(digitCount = 3, maxWidth = 340f, fontScale = 1f), 0f)
        assertEquals(102f, responsiveCountTextSize(digitCount = 4, maxWidth = 340f, fontScale = 1f), 0f)
        assertEquals(90f, responsiveCountTextSize(digitCount = 5, maxWidth = 340f, fontScale = 1f), 0f)
        assertEquals(76f, responsiveCountTextSize(digitCount = 6, maxWidth = 340f, fontScale = 1f), 0f)
        assertEquals(38.532f, responsiveCountTextSize(digitCount = 6, maxWidth = 265.2f, fontScale = 2f), 0.001f)
    }

    @Test
    fun hapticConstantMatchesStrengthAndAndroidVersion() {
        assertEquals(HapticFeedbackConstants.CLOCK_TICK, hapticFeedbackConstant(strong = false, sdkInt = 29))
        assertEquals(HapticFeedbackConstants.LONG_PRESS, hapticFeedbackConstant(strong = true, sdkInt = 29))
        assertEquals(HapticFeedbackConstants.CONFIRM, hapticFeedbackConstant(strong = true, sdkInt = 30))
    }

    @Test
    fun unavailableProjectFeedbackOnlyPostsMessage() {
        val events = mutableListOf<String>()

        handleCounterEffect(
            effect = CounterEffect.ShowMessage(R.string.counter_project_missing),
            hapticsEnabled = true,
            onMessage = { events += "message" },
            onHaptic = { events += "haptic" },
        )

        assertEquals(listOf("message"), events)
    }
}
