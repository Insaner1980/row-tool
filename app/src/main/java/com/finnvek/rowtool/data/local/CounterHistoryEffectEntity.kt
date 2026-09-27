package com.finnvek.rowtool.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "counter_history_effects",
    primaryKeys = ["historyId", "counterId"],
    foreignKeys = [
        ForeignKey(
            entity = CounterHistoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["historyId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = AdditionalCounterEntity::class,
            parentColumns = ["id"],
            childColumns = ["counterId"],
        ),
    ],
    indices = [Index("counterId")],
)
data class CounterHistoryEffectEntity(
    val historyId: Long,
    val counterId: String,
    val previousCount: Long,
    val newCount: Long,
)
