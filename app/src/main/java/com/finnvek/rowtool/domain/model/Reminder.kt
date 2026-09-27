package com.finnvek.rowtool.domain.model

data class Reminder(
    val id: String,
    val projectId: String,
    val message: String,
    val firstCount: Long,
    val intervalCount: Long?,
    val enabled: Boolean,
    val acknowledgedThrough: Long?,
    val revision: Long,
)

data class ReminderStatus(
    val dueCount: Long? = null,
    val nextCount: Long? = null,
    val earlierUnacknowledged: Long = 0,
)

object ReminderRules {
    const val MAX_MESSAGE_CODE_POINTS = 200

    fun normalizeMessage(message: String): String = message.trim()

    fun validate(
        message: String,
        firstCount: Long,
        intervalCount: Long?,
    ): Boolean {
        val normalized = normalizeMessage(message)
        return normalized.isNotEmpty() &&
            normalized.codePointCount(0, normalized.length) <= MAX_MESSAGE_CODE_POINTS &&
            firstCount in 1..CounterConstants.MAX_COUNT &&
            (intervalCount == null || intervalCount in 1..CounterConstants.MAX_COUNT)
    }

    fun validAcknowledgement(
        firstCount: Long,
        intervalCount: Long?,
        acknowledgedThrough: Long?,
    ): Boolean =
        acknowledgedThrough == null ||
            (
                acknowledgedThrough in firstCount..CounterConstants.MAX_COUNT &&
                    (intervalCount?.let { (acknowledgedThrough - firstCount) % it == 0L } ?: (acknowledgedThrough == firstCount))
            )

    fun status(
        reminder: Reminder,
        count: Long,
    ): ReminderStatus {
        val first = reminder.firstCount
        val interval = reminder.intervalCount
        val acknowledged = reminder.acknowledgedThrough
        return when {
            !reminder.enabled -> {
                ReminderStatus()
            }

            interval == null -> {
                when {
                    acknowledged != null -> ReminderStatus()
                    count < first -> ReminderStatus(nextCount = first)
                    else -> ReminderStatus(dueCount = first)
                }
            }

            count < first -> {
                val next = maxOf(first, acknowledged?.plus(interval) ?: first)
                ReminderStatus(nextCount = next.takeIf { it <= CounterConstants.MAX_COUNT })
            }

            else -> {
                val latest = first + (count - first) / interval * interval
                if (acknowledged == null || latest > acknowledged) {
                    val firstUnacknowledged = acknowledged?.plus(interval) ?: first
                    ReminderStatus(
                        dueCount = latest,
                        earlierUnacknowledged = (latest - firstUnacknowledged) / interval,
                    )
                } else {
                    val next = maxOf(latest, acknowledged) + interval
                    ReminderStatus(nextCount = next.takeIf { it <= CounterConstants.MAX_COUNT })
                }
            }
        }
    }
}
