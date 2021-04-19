package com.arupakaman.kawa.ui.revealer

import android.animation.Animator
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.arupakaman.kawa.databinding.ActivityThemeRevealerScreenshotBinding
import com.arupakaman.kawa.utils.theme.reveal.CubicBezierInterpolator
import com.arupakaman.kawa.utils.theme.reveal.ThemeRevealer
import com.arupakaman.kawa.utils.updateTheme

class ThemeRevealerScreenshotActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityThemeRevealerScreenshotBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        val bitmap = ThemeRevealer.screenshot
        val posX = intent.getIntExtra(ThemeRevealer.SCREENSHOT_X, -1)
        val posY = intent.getIntExtra(ThemeRevealer.SCREENSHOT_Y, -1)
        val toDark = intent.getBooleanExtra(ThemeRevealer.TO_DARK,false)

        mBinding.imgScreenshot.run {
            setImageBitmap(bitmap)
            scaleType = ImageView.ScaleType.MATRIX
            visibility = View.VISIBLE
        }

        if (bitmap!=null) {
            mBinding.imgScreenshot.post {
                startCircularAnimation(mBinding.imgScreenshot, bitmap, posX, posY, toDark)
            }
        }
    }

    private fun startCircularAnimation(imgScreenshot:ImageView,bitmap: Bitmap, posX: Int, posY: Int, toDark:Boolean) {
        imgScreenshot.setImageBitmap(bitmap)
        imgScreenshot.scaleType = ImageView.ScaleType.MATRIX
        imgScreenshot.visibility = View.VISIBLE

        /*if (toDark)
        {
            updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
        }*/

        // Final radius is approximated here.
        val finalRadius = 1500f
        val anim = ViewAnimationUtils.createCircularReveal(imgScreenshot, posX, posY, 0f, finalRadius)
        anim.duration = 4000
        anim.interpolator = CubicBezierInterpolator.EASE_IN_OUT_QUAD
        val animationListener = object: Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                imgScreenshot.setImageDrawable(null)
                imgScreenshot.visibility = View.GONE
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        }
        anim.addListener(animationListener)
        anim.start()
    }
}