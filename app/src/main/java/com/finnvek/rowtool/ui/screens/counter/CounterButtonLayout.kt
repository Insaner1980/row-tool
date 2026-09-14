package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finnvek.rowtool.ui.theme.RowToolDimens

data class CounterButtonLayout(
    val visualSize: Dp,
    val touchSize: Dp,
    val visualOffsetY: Dp = 0.dp,
)

internal fun counterControlScale(availableWidth: Dp): Float =
    // Shrink only when the primary pair would overlap; retain accessible touch targets.
    (availableWidth / (RowToolDimens.CounterPrimaryTouchSize * 2)).coerceIn(
        RowToolDimens.MinimumTouchSize / RowToolDimens.CounterPrimaryTouchSize,
        1f,
    )
