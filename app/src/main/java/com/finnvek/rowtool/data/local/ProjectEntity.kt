package com.finnvek.rowtool.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "projects",
    indices = [
        Index(value = ["isArchived", "updatedAt"]),
    ],
)
data class ProjectEntity(
    @PrimaryKey val id: String,
    val name: String,
    val counterUnit: String,
    val count: Long,
    val startValue: Int,
    val targetCount: Long?,
    val repeatLength: Int?,
    val isArchived: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
)
