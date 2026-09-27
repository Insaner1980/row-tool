package com.finnvek.rowtool.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.finnvek.rowtool.domain.model.ProjectNote

@Entity(
    tableName = "project_notes",
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["projectId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class ProjectNoteEntity(
    @PrimaryKey val projectId: String,
    val version: String,
    val text: String,
    val savedAt: Long,
    val savedCount: Long?,
)

internal fun ProjectNoteEntity.toDomain(): ProjectNote = ProjectNote(projectId, version, text, savedAt, savedCount)
