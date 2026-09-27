package com.finnvek.rowtool.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders WHERE projectId = :projectId ORDER BY rowid")
    fun observe(projectId: String): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE projectId = :projectId ORDER BY rowid")
    suspend fun getForProject(projectId: String): List<ReminderEntity>

    @Query("SELECT * FROM reminders WHERE projectId = :projectId AND id = :id")
    suspend fun getById(
        projectId: String,
        id: String,
    ): ReminderEntity?

    @Query("SELECT * FROM reminders ORDER BY rowid")
    suspend fun getAll(): List<ReminderEntity>

    @Insert
    suspend fun insert(reminder: ReminderEntity)

    @Update
    suspend fun update(reminder: ReminderEntity): Int

    @Query("DELETE FROM reminders WHERE projectId = :projectId AND id = :id")
    suspend fun delete(
        projectId: String,
        id: String,
    ): Int
}
