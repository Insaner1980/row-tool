package com.finnvek.rowtool.domain.counter

import com.finnvek.rowtool.domain.model.RepeatProgress
import com.finnvek.rowtool.domain.model.TargetProgress

object RepeatProgressCalculator {
    fun calculate(
        count: Long,
        repeatLength: Int?,
        repeatStartCount: Long? = if (repeatLength != null) 1L else null,
    ): RepeatProgress? {
        if (repeatLength == null) return null

        val progress = (count - requireNotNull(repeatStartCount) + 1).coerceAtLeast(0)
        val currentStep =
            if (progress == 0L) {
                0
            } else {
                (((progress - 1) % repeatLength) + 1).toInt()
            }
        return RepeatProgress(
            currentStep = currentStep,
            repeatLength = repeatLength,
            completedRepeats = progress / repeatLength,
        )
    }
}

object TargetProgressCalculator {
    fun calculate(
        count: Long,
        targetCount: Long?,
    ): TargetProgress? {
        if (targetCount == null) return null

        return TargetProgress(
            count = count,
            targetCount = targetCount,
            fraction = (count.toDouble() / targetCount.toDouble()).coerceIn(0.0, 1.0).toFloat(),
            isReached = count >= targetCount,
        )
    }
}
