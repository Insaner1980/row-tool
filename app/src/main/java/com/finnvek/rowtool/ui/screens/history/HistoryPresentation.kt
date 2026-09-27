package com.finnvek.rowtool.ui.screens.history

import android.text.format.DateFormat
import com.finnvek.rowtool.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val MAX_DISPLAY_YEAR = 9999

internal fun historyReason(reason: String): Int =
    when (reason) {
        "INCREMENT" -> R.string.history_increment
        "DECREMENT" -> R.string.history_decrement
        "MANUAL_SET" -> R.string.history_set
        "RESET" -> R.string.history_reset
        else -> R.string.history_change
    }

internal fun historyTime(
    timestamp: Long?,
    locale: Locale,
    use24Hour: Boolean,
    zone: TimeZone,
): String? {
    if (timestamp == null || timestamp < 0 || Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.UTC).year > MAX_DISPLAY_YEAR) return null
    val skeleton = if (use24Hour) "yMMMdHms" else "yMMMdhms"
    return SimpleDateFormat(DateFormat.getBestDateTimePattern(locale, skeleton), locale).apply { timeZone = zone }.format(Date(timestamp))
}
