package com.finnvek.rowtool.ui.screens.counter

internal data class ReminderActions(
    val onSave: suspend (String, String?, Long?, String, Long, Long?, Boolean, String?) -> Boolean,
    val onAcknowledge: suspend (String, String, Long, Long) -> Boolean,
    val onReset: suspend (String, String, Long) -> Boolean,
    val onDelete: suspend (String, String, Long) -> Boolean,
)
