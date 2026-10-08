package com.finnvek.rowtool.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class SplashLogoDrawableTest {
    @Test
    fun revealsOuterRingBeforeTextAndCenter() {
        val source = Bitmap.createBitmap(1254, 1254, Bitmap.Config.ARGB_8888).apply { eraseColor(Color.RED) }
        val logo = SplashLogoDrawable(source).apply { setBounds(0, 0, 1254, 1254) }
        assertEquals(Color.TRANSPARENT, render(logo, 0f).getPixel(1000, 300))
        val outer = render(logo, 100f)
        assertEquals(Color.RED, outer.getPixel(1000, 300))
        assertEquals(Color.TRANSPARENT, outer.getPixel(100, 620))
        assertEquals(Color.TRANSPARENT, outer.getPixel(700, 250))
        val text = render(logo, 350f)
        assertEquals(Color.RED, text.getPixel(700, 250))
        assertEquals(Color.TRANSPARENT, text.getPixel(627, 620))
        val center = render(logo, 530f)
        assertEquals(Color.RED, center.getPixel(627, 620))
        assertEquals(Color.RED, center.getPixel(1000, 300))
        assertEquals(Color.RED, center.getPixel(100, 620))
        assertEquals(Color.RED, center.getPixel(700, 250))
        assertEquals(Color.RED, center.getPixel(300, 620))
    }

    @Test
    fun completedRevealPreservesOriginalArtworkIncludingLetterCutouts() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val source = BitmapFactory.decodeResource(context.resources, R.drawable.rowtool_logo)
        val logo = SplashLogoDrawable(source).apply { setBounds(0, 0, 1254, 1254) }
        val expected = Bitmap.createBitmap(1254, 1254, Bitmap.Config.ARGB_8888)
        Canvas(expected).drawBitmap(source, 0f, 0f, null)
        assertTrue(expected.sameAs(render(logo, 640f)))
        assertTrue(expected.sameAs(render(logo, 760f)))
    }

    private fun render(
        logo: SplashLogoDrawable,
        elapsed: Float,
    ): Bitmap {
        val result = Bitmap.createBitmap(1254, 1254, Bitmap.Config.ARGB_8888)
        logo.elapsedMillis = elapsed
        logo.draw(Canvas(result))
        return result
    }
}
