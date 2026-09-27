package com.finnvek.rowtool.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CounterHistoryDao {
    @Query(
        """
        SELECT p.name AS projectName, h.id AS historyId, h.changeReason AS reason,
               h.createdAt, h.previousCount AS mainBefore, h.newCount AS mainAfter,
               e.counterId, c.name AS counterName, c.isDeleted AS deleted,
               e.previousCount AS effectBefore, e.newCount AS effectAfter
        FROM projects p
        LEFT JOIN (
            SELECT * FROM counter_history WHERE projectId = :projectId ORDER BY id DESC LIMIT :limit
        ) h ON h.projectId = p.id
        LEFT JOIN counter_history_effects e ON e.historyId = h.id
        LEFT JOIN additional_counters c ON c.id = e.counterId AND c.projectId = p.id
        WHERE p.id = :projectId
        ORDER BY h.id DESC, e.counterId
        """,
    )
    fun observeHistory(
        projectId: String,
        limit: Int,
    ): Flow<List<HistoryReadRow>>

    @Insert
    suspend fun insert(history: CounterHistoryEntity): Long

    @Query("SELECT * FROM counter_history WHERE projectId = :projectId ORDER BY id DESC LIMIT 1")
    suspend fun getLatest(projectId: String): CounterHistoryEntity?

    @Query("SELECT * FROM counter_history ORDER BY id")
    suspend fun getAll(): List<CounterHistoryEntity>

    @Query("SELECT COUNT(*) FROM counter_history WHERE projectId = :projectId")
    fun observeCountForProject(projectId: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM counter_history WHERE projectId = :projectId")
    suspend fun countForProject(projectId: String): Int

    @Query("SELECT COUNT(*) FROM counter_history")
    suspend fun countAll(): Int

    @Query("DELETE FROM counter_history WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query(
        """
        DELETE FROM counter_history
        WHERE projectId = :projectId
          AND id NOT IN (
              SELECT id FROM counter_history
              WHERE projectId = :projectId
              ORDER BY id DESC
              LIMIT :keepCount
          )
        """,
    )
    suspend fun trimToNewest(
        projectId: String,
        keepCount: Int,
    )

    @Query("DELETE FROM counter_history")
    suspend fun deleteAll()
}
