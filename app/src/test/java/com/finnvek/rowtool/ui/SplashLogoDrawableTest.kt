package com.finnvek.rowtool.ui

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.util.TypedValue
import android.view.ContextThemeWrapper
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
    fun startingThemeShowsLogoWhileWaitingForStartup() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        for (nightMode in listOf(Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_YES)) {
            for ((width, scale) in listOf(320 to 1f, 411 to 2f, 320 to 2f)) {
                val configuration =
                    Configuration(context.resources.configuration).apply {
                        screenWidthDp = width
                        fontScale = scale
                        uiMode = (uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()) or nightMode
                    }
                val themed =
                    ContextThemeWrapper(context.createConfigurationContext(configuration), R.style.Theme_RowTool_Starting)
                val icon = TypedValue()
                assertTrue(
                    themed.theme.resolveAttribute(androidx.core.splashscreen.R.attr.windowSplashScreenAnimatedIcon, icon, true),
                )
                val drawable = checkNotNull(themed.getDrawable(icon.resourceId))
                val rendered = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
                drawable.setBounds(0, 0, width, width)
                drawable.draw(Canvas(rendered))
                val pixels = IntArray(width * width)
                rendered.getPixels(pixels, 0, width, 0, 0, width, width)
                assertTrue(
                    "Logo must be visible: night=$nightMode, width=$width, fontScale=$scale",
                    pixels.any { Color.alpha(it) > 0 },
                )
            }
        }
    }

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
