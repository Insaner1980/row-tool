package com.finnvek.rowtool.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CounterHistoryDao {
    @Insert
    suspend fun insert(history: CounterHistoryEntity): Long

    @Query("SELECT * FROM counter_history WHERE projectId = :projectId ORDER BY id DESC LIMIT 1")
    suspend fun getLatest(projectId: String): CounterHistoryEntity?

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
