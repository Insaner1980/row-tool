package com.finnvek.rowtool.data.repository

import com.finnvek.rowtool.domain.model.CounterUnit

internal suspend fun createTwoProjects(counters: CounterRepository): Pair<String, String> {
    val first = counters.createProject("A", CounterUnit.ROWS, 0, null, null).id
    val second = counters.createProject("B", CounterUnit.ROWS, 0, null, null).id
    return first to second
}
