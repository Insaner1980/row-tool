package com.finnvek.rowtool.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.finnvek.rowtool.domain.model.AdditionalCounter

// CPD-OFF: Each Room entity declares its own table and project foreign key.
@Entity(
    tableName = "additional_counters",
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
data class AdditionalCounterEntity(
    // CPD-ON
    @PrimaryKey val id: String,
    val projectId: String,
    val name: String,
    val count: Long,
    val followsMain: Boolean,
    val isDeleted: Boolean = false,
)

fun AdditionalCounterEntity.toDomain(): AdditionalCounter = AdditionalCounter(id, projectId, name, count, followsMain)
