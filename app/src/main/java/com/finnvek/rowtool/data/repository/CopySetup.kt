package com.finnvek.rowtool.data.repository

import androidx.room.withTransaction
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.local.toDomain
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.ProjectValidation
import com.finnvek.rowtool.ui.screens.projects.ProjectEditorValues
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

data class CopyCounterSetup(
    val name: String,
    val followsMain: Boolean,
)

data class CopySetup(
    val sourceId: String,
    val sourceName: String,
    val generation: String,
    val projectId: String,
    val settings: ProjectEditorValues,
    val counters: List<CopyCounterSetup>,
)

class CopySourceUnavailableException : IllegalStateException("Copy source unavailable")

class CopySetupStore internal constructor(
    private val database: RowToolDatabase,
    private val mutationMutex: Mutex,
    private val idGenerator: () -> String,
    private val insertProject: suspend (ProjectEditorValues, String) -> CounterProject,
) {
    private val projectDao = database.projectDao()
    private val additionalDao = database.additionalCounterDao()

    suspend fun capture(sourceId: String): CopySetup =
        database.withTransaction {
            val source = projectDao.getById(sourceId) ?: throw CopySourceUnavailableException()
            CopySetup(
                sourceId = source.id,
                sourceName = source.name,
                generation = database.copyGeneration,
                projectId = idGenerator(),
                settings =
                    com.finnvek.rowtool.ui.screens.projects.ProjectEditorValues(
                        "",
                        CounterUnit.valueOf(source.counterUnit),
                        source.startValue,
                        source.targetCount,
                        source.repeatLength,
                        source.repeatStartCount,
                    ),
                counters = additionalDao.getActive(sourceId).map { CopyCounterSetup(it.name, it.followsMain) },
            )
        }

    suspend fun create(
        draft: CopySetup,
        values: com.finnvek.rowtool.ui.screens.projects.ProjectEditorValues,
    ): CounterProject =
        mutationMutex.withLock {
            database.withTransaction {
                if (draft.generation != database.copyGeneration) throw CopySourceUnavailableException()
                // The draft owns its fresh identity before submission, including interrupted delivery.
                projectDao.getById(draft.projectId)?.let { return@withTransaction it.toDomain() }
                if (projectDao.getById(draft.sourceId) == null) throw CopySourceUnavailableException()
                val project =
                    insertProject(values, draft.projectId)
                draft.counters.forEach { counter ->
                    require(ProjectValidation.nameErrors(counter.name).isEmpty())
                    additionalDao.insert(
                        com.finnvek.rowtool.data.local.AdditionalCounterEntity(
                            idGenerator(),
                            project.id,
                            ProjectValidation.normalizeName(counter.name),
                            0,
                            counter.followsMain,
                        ),
                    )
                }
                project
            }
        }
}
