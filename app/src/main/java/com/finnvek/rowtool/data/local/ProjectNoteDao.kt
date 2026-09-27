package com.finnvek.rowtool.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectNoteDao {
    @Query("SELECT * FROM project_notes WHERE projectId = :projectId")
    suspend fun get(projectId: String): ProjectNoteEntity?

    @Query("SELECT * FROM project_notes WHERE projectId = :projectId")
    fun observe(projectId: String): Flow<ProjectNoteEntity?>

    @Query("SELECT projectId FROM project_notes")
    fun observeProjectIds(): Flow<List<String>>

    @Query("SELECT * FROM project_notes")
    suspend fun getAll(): List<ProjectNoteEntity>

    @Insert
    suspend fun insert(note: ProjectNoteEntity)

    @Update
    suspend fun update(note: ProjectNoteEntity)

    @Query("DELETE FROM project_notes WHERE projectId = :projectId")
    suspend fun delete(projectId: String)
}
