package com.finnvek.rowtool.widget

import android.net.Uri
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class WidgetOpenRequestTest {
    @Test fun onlyExplicitWidgetRoutesAreParsed() {
        assertNull(WidgetOpenRequest.parse(Uri.parse("https://example.com/open/1/token")))
        assertNull(WidgetOpenRequest.parse(Uri.parse("rowtool-widget://open/-1/token")))
        assertNull(WidgetOpenRequest.parse(Uri.parse("rowtool-widget://open/1")))
        assertNull(WidgetOpenRequest.parse(Uri.parse("rowtool-widget://count/1/token")))
        assertEquals(WidgetOpenRequest(7, "token", true), WidgetOpenRequest.parse(Uri.parse("rowtool-widget://reminders/7/token")))
    }
}
