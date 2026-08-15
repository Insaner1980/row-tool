package com.finnvek.rowtool.data.local

import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit

fun ProjectEntity.toDomain(): CounterProject =
    CounterProject(
        id = id,
        name = name,
        counterUnit = CounterUnit.fromPersisted(counterUnit),
        count = count,
        startValue = startValue,
        targetCount = targetCount,
        repeatLength = repeatLength,
        isArchived = isArchived,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

fun CounterProject.toEntity(): ProjectEntity =
    ProjectEntity(
        id = id,
        name = name,
        counterUnit = counterUnit.name,
        count = count,
        startValue = startValue,
        targetCount = targetCount,
        repeatLength = repeatLength,
        isArchived = isArchived,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
