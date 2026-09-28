package com.finnvek.rowtool.ui

import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.RowToolApplication

internal fun testContainer() =
    (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as RowToolApplication).container
