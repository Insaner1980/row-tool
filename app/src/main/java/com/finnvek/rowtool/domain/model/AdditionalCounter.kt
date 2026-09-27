package com.finnvek.rowtool.domain.model

data class AdditionalCounter(
    val id: String,
    val projectId: String,
    val name: String,
    val count: Long,
    val followsMain: Boolean,
)
