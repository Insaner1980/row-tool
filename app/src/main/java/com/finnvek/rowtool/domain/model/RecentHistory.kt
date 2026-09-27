package com.finnvek.rowtool.domain.model

data class RecentHistory(
    val projectName: String,
    val entries: List<RecentHistoryEntry>,
)

data class RecentHistoryEntry(
    val id: Long,
    val reason: String,
    val createdAt: Long?,
    val changes: List<HistoryCountChange>,
)

data class HistoryCountChange(
    val counterId: String?,
    val name: String?,
    val deleted: Boolean,
    val before: Long,
    val after: Long,
)
