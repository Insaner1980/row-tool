package com.finnvek.rowtool.domain.counter

import com.finnvek.rowtool.domain.model.RepeatProgress
import com.finnvek.rowtool.domain.model.TargetProgress

object RepeatProgressCalculator {
    fun calculate(
        count: Long,
        repeatLength: Int?,
    ): RepeatProgress? {
        if (repeatLength == null) return null

        val currentStep =
            if (count == 0L) {
                0
            } else {
                (((count - 1) % repeatLength) + 1).toInt()
            }
        return RepeatProgress(
            currentStep = currentStep,
            repeatLength = repeatLength,
            completedRepeats = count / repeatLength,
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
