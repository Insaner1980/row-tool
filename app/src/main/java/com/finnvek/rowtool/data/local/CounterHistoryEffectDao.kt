package com.finnvek.rowtool.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CounterHistoryEffectDao {
    @Insert
    suspend fun insertAll(effects: List<CounterHistoryEffectEntity>)

    @Query("SELECT * FROM counter_history_effects WHERE historyId = :historyId ORDER BY counterId")
    suspend fun getForHistory(historyId: Long): List<CounterHistoryEffectEntity>

    @Query("SELECT * FROM counter_history_effects ORDER BY historyId, counterId")
    suspend fun getAll(): List<CounterHistoryEffectEntity>
}
