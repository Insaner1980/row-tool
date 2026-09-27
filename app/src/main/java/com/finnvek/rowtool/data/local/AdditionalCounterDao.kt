package com.finnvek.rowtool.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AdditionalCounterDao {
    @Query("SELECT * FROM additional_counters WHERE projectId = :projectId AND isDeleted = 0 ORDER BY rowid")
    fun observeActive(projectId: String): Flow<List<AdditionalCounterEntity>>

    @Query("SELECT * FROM additional_counters WHERE projectId = :projectId AND isDeleted = 0 ORDER BY rowid")
    suspend fun getActive(projectId: String): List<AdditionalCounterEntity>

    @Query("SELECT * FROM additional_counters WHERE projectId = :projectId AND id = :id")
    suspend fun getById(
        projectId: String,
        id: String,
    ): AdditionalCounterEntity?

    @Query("SELECT * FROM additional_counters ORDER BY rowid")
    suspend fun getAll(): List<AdditionalCounterEntity>

    @Insert
    suspend fun insert(counter: AdditionalCounterEntity)

    @Update
    suspend fun update(counter: AdditionalCounterEntity)

    @Query("UPDATE additional_counters SET count = :count WHERE id = :id")
    suspend fun setCount(
        id: String,
        count: Long,
    )

    @Query(
        """
        DELETE FROM additional_counters WHERE isDeleted = 1
        AND id NOT IN (SELECT counterId FROM counter_history_effects)
        """,
    )
    suspend fun deleteUnreferenced()
}
