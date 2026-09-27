package com.finnvek.rowtool.domain.model

data class ProjectNote(
    val projectId: String,
    val version: String,
    val text: String,
    val savedAt: Long,
    val savedCount: Long?,
)

object ProjectNoteRules {
    const val MAX_CODE_POINTS = 5_000

    fun normalize(text: String): String = text.replace("\r\n", "\n").replace('\r', '\n')

    fun withinLimit(text: String): Boolean = normalize(text).let { it.codePointCount(0, it.length) <= MAX_CODE_POINTS }

    fun validContent(text: String): Boolean = text.isNotBlank() && withinLimit(text)
}
