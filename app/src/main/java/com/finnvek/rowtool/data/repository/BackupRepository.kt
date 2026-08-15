package com.finnvek.rowtool.data.repository

import android.database.SQLException
import androidx.room.withTransaction
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.local.toDomain
import com.finnvek.rowtool.data.local.toEntity
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.domain.model.CounterProject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream

class BackupRepository(
    private val database: RowToolDatabase,
    private val preferencesRepository: PreferencesRepository,
    private val clock: () -> Long = System::currentTimeMillis,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun exportJson(): String =
        withContext(ioDispatcher) {
            val projects = database.withTransaction { database.projectDao().getAll() }
            BackupCodec.encode(
                BackupFile(
                    schemaVersion = 1,
                    application = "RowTool",
                    exportedAt = clock(),
                    projects =
                        projects.map { entity ->
                            BackupProject(
                                id = entity.id,
                                name = entity.name,
                                counterUnit = entity.counterUnit,
                                count = entity.count,
                                startValue = entity.startValue,
                                targetCount = entity.targetCount,
                                repeatLength = entity.repeatLength,
                                isArchived = entity.isArchived,
                                createdAt = entity.createdAt,
                                updatedAt = entity.updatedAt,
                            )
                        },
                ),
            )
        }

    fun prepareImport(bytes: ByteArray): BackupDecodeResult = BackupCodec.decode(bytes)

    suspend fun prepareImport(input: InputStream): BackupDecodeResult =
        withContext(ioDispatcher) {
            BackupCodec.decode(input)
        }

    suspend fun replaceWith(backup: ValidatedBackup): BackupImportResult =
        withContext(ioDispatcher) {
            try {
                database.withTransaction {
                    database.counterHistoryDao().deleteAll()
                    database.projectDao().deleteAll()
                    database.projectDao().insertAll(backup.projects.map(CounterProject::toEntity))
                }
            } catch (_: SQLException) {
                return@withContext BackupImportResult.Failure(
                    BackupImportFailure.DATABASE_WRITE_FAILED,
                )
            }

            val lastActiveProjectId =
                backup.projects
                    .asSequence()
                    .filterNot { it.isArchived }
                    .maxWithOrNull(
                        compareBy<CounterProject> { it.updatedAt }
                            .thenBy { it.createdAt }
                            .thenBy { it.id },
                    )?.id
            try {
                preferencesRepository.setLastActiveProjectId(lastActiveProjectId)
            } catch (_: IOException) {
                // The project replacement is already committed; startup resolution repairs stale selection.
            }
            BackupImportResult.Success(
                projectCount = backup.projects.size,
                lastActiveProjectId = lastActiveProjectId,
            )
        }
}
