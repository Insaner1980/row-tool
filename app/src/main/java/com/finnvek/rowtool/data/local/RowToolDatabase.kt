package com.finnvek.rowtool.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ProjectEntity::class, CounterHistoryEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class RowToolDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao

    abstract fun counterHistoryDao(): CounterHistoryDao

    companion object {
        const val DATABASE_NAME = "rowtool.db"

        fun create(context: Context): RowToolDatabase =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    RowToolDatabase::class.java,
                    DATABASE_NAME,
                ).build()
    }
}
