package com.finnvek.rowtool.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.finnvek.rowtool.domain.model.Reminder

@Entity(
    tableName = "reminders",
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["projectId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("projectId")],
)
data class ReminderEntity(
    @PrimaryKey val id: String,
    val projectId: String,
    val message: String,
    val firstCount: Long,
    val intervalCount: Long?,
    val enabled: Boolean,
    val acknowledgedThrough: Long?,
    val revision: Long,
)

fun ReminderEntity.toDomain() = Reminder(id, projectId, message, firstCount, intervalCount, enabled, acknowledgedThrough, revision)

fun Reminder.toEntity() = ReminderEntity(id, projectId, message, firstCount, intervalCount, enabled, acknowledgedThrough, revision)
