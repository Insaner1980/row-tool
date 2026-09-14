package com.finnvek.rowtool.test

import com.finnvek.rowtool.data.local.ProjectEntity
import com.finnvek.rowtool.domain.model.CounterUnit

internal fun projectEntities(count: Int): List<ProjectEntity> =
    List(count) { index ->
        ProjectEntity(
            id = "project-$index",
            name = "Project $index",
            counterUnit = CounterUnit.ROWS.name,
            count = 0,
            startValue = 0,
            targetCount = null,
            repeatLength = null,
            isArchived = false,
            createdAt = index.toLong(),
            updatedAt = index.toLong(),
        )
    }
