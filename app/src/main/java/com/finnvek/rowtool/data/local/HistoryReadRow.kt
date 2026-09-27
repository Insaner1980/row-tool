package com.finnvek.rowtool.data.local

/** A single-query snapshot: the history limit is applied before joining effects. */
data class HistoryReadRow(
    val projectName: String,
    val historyId: Long?,
    val reason: String?,
    val createdAt: Long?,
    val mainBefore: Long?,
    val mainAfter: Long?,
    val counterId: String?,
    val counterName: String?,
    val deleted: Boolean?,
    val effectBefore: Long?,
    val effectAfter: Long?,
)
