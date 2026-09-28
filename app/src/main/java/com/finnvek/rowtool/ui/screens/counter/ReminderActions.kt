package com.finnvek.rowtool.ui.screens.counter

import com.finnvek.rowtool.domain.model.ReminderValues

internal data class ReminderActions(
    val onSave: suspend (String, String?, Long?, ReminderValues, String?) -> Boolean,
    val onAcknowledge: suspend (String, String, Long, Long) -> Boolean,
    val onReset: suspend (String, String, Long) -> Boolean,
    val onDelete: suspend (String, String, Long) -> Boolean,
)
