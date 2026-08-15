package com.finnvek.rowtool.ui

import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.RowToolApplication
import kotlinx.coroutines.runBlocking
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

internal class PrepareApplicationStateRule(
    private val prepare: suspend RowToolApplication.() -> Unit,
) : TestRule {
    override fun apply(
        base: Statement,
        description: Description,
    ): Statement =
        object : Statement() {
            override fun evaluate() {
                val application =
                    InstrumentationRegistry
                        .getInstrumentation()
                        .targetContext.applicationContext as RowToolApplication
                runBlocking {
                    application.container.database.clearAllTables()
                    prepare(application)
                }
                base.evaluate()
            }
        }
}
