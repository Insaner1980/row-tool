package com.finnvek.rowtool.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        ProjectEntity::class,
        CounterHistoryEntity::class,
        AdditionalCounterEntity::class,
        CounterHistoryEffectEntity::class,
        ReminderEntity::class,
        ProjectNoteEntity::class,
    ],
    version = 5,
    exportSchema = true,
)
abstract class RowToolDatabase : RoomDatabase() {
    // Accessed only within Room transactions. A new process also invalidates old source identity.
    internal var copyGeneration: String =
        java.util.UUID
            .randomUUID()
            .toString()

    abstract fun projectDao(): ProjectDao

    abstract fun counterHistoryDao(): CounterHistoryDao

    abstract fun additionalCounterDao(): AdditionalCounterDao

    abstract fun counterHistoryEffectDao(): CounterHistoryEffectDao

    abstract fun reminderDao(): ReminderDao

    abstract fun projectNoteDao(): ProjectNoteDao

    companion object {
        const val DATABASE_NAME = "rowtool.db"

        val MIGRATION_1_2 =
            object : Migration(1, 2) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL(
                        """
                        CREATE TABLE IF NOT EXISTS additional_counters (
                            id TEXT NOT NULL PRIMARY KEY, projectId TEXT NOT NULL, name TEXT NOT NULL,
                            count INTEGER NOT NULL, followsMain INTEGER NOT NULL, isDeleted INTEGER NOT NULL,
                            FOREIGN KEY(projectId) REFERENCES projects(id) ON UPDATE NO ACTION ON DELETE CASCADE
                        )
                        """.trimIndent(),
                    )
                    db.execSQL("CREATE INDEX IF NOT EXISTS index_additional_counters_projectId ON additional_counters(projectId)")
                    db.execSQL(
                        """
                        CREATE TABLE IF NOT EXISTS counter_history_effects (
                            historyId INTEGER NOT NULL, counterId TEXT NOT NULL,
                            previousCount INTEGER NOT NULL, newCount INTEGER NOT NULL,
                            PRIMARY KEY(historyId, counterId),
                            FOREIGN KEY(historyId) REFERENCES counter_history(id) ON UPDATE NO ACTION ON DELETE CASCADE,
                            FOREIGN KEY(counterId) REFERENCES additional_counters(id) ON UPDATE NO ACTION ON DELETE NO ACTION
                        )
                        """.trimIndent(),
                    )
                    db.execSQL("CREATE INDEX IF NOT EXISTS index_counter_history_effects_counterId ON counter_history_effects(counterId)")
                }
            }

        val MIGRATION_2_3 =
            object : Migration(2, 3) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("ALTER TABLE projects ADD COLUMN repeatStartCount INTEGER")
                    db.execSQL("UPDATE projects SET repeatStartCount = 1 WHERE repeatLength IS NOT NULL")
                }
            }

        val MIGRATION_3_4 =
            object : Migration(3, 4) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL(
                        """
                        CREATE TABLE IF NOT EXISTS reminders (
                            id TEXT NOT NULL PRIMARY KEY, projectId TEXT NOT NULL, message TEXT NOT NULL,
                            firstCount INTEGER NOT NULL, intervalCount INTEGER, enabled INTEGER NOT NULL,
                            acknowledgedThrough INTEGER, revision INTEGER NOT NULL,
                            FOREIGN KEY(projectId) REFERENCES projects(id) ON UPDATE NO ACTION ON DELETE CASCADE
                        )
                        """.trimIndent(),
                    )
                    db.execSQL("CREATE INDEX IF NOT EXISTS index_reminders_projectId ON reminders(projectId)")
                }
            }

        val MIGRATION_4_5 =
            object : Migration(4, 5) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL(
                        """
                        CREATE TABLE IF NOT EXISTS project_notes (
                            projectId TEXT NOT NULL PRIMARY KEY, version TEXT NOT NULL, text TEXT NOT NULL,
                            savedAt INTEGER NOT NULL, savedCount INTEGER,
                            FOREIGN KEY(projectId) REFERENCES projects(id) ON UPDATE NO ACTION ON DELETE CASCADE
                        )
                        """.trimIndent(),
                    )
                }
            }

        fun create(context: Context): RowToolDatabase =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    RowToolDatabase::class.java,
                    DATABASE_NAME,
                ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                .build()
    }
}
