package com.finnvek.rowtool.data.repository

import com.finnvek.rowtool.data.local.CounterHistoryDao
import com.finnvek.rowtool.data.local.HistoryReadRow
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.HistoryCountChange
import com.finnvek.rowtool.domain.model.RecentHistory
import com.finnvek.rowtool.domain.model.RecentHistoryEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecentHistoryStore(
    private val dao: CounterHistoryDao,
) {
    fun observe(projectId: String): Flow<RecentHistory?> =
        dao.observeHistory(projectId, CounterConstants.MAX_HISTORY_ENTRIES).map { it.toRecentHistory() }
}

internal fun List<HistoryReadRow>.toRecentHistory(): RecentHistory? {
    val project = firstOrNull() ?: return null
    val entries =
        filter { it.historyId != null }.groupBy { requireNotNull(it.historyId) }.map { (id, rows) ->
            val header = rows.first()
            val changes =
                buildList {
                    if (header.mainBefore != null && header.mainAfter != null && header.mainBefore != header.mainAfter) {
                        add(HistoryCountChange(null, null, false, header.mainBefore, header.mainAfter))
                    }
                    rows.forEach { row ->
                        if (row.counterId == null) return@forEach
                        if (row.effectBefore != null && row.effectAfter != null && row.effectBefore != row.effectAfter) {
                            add(HistoryCountChange(row.counterId, row.counterName, row.deleted == true, row.effectBefore, row.effectAfter))
                        }
                    }
                }
            RecentHistoryEntry(id, header.reason.orEmpty(), header.createdAt, changes)
        }
    return RecentHistory(project.projectName, entries)
}
