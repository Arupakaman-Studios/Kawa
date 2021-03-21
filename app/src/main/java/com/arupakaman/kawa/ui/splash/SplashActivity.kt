package com.arupakaman.kawa.ui.splash

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.arupakaman.kawa.R
import com.arupakaman.kawa.utils.makeItFullScreenStatusBarHidden

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        makeItFullScreenStatusBarHidden()

        setFluidAnimation()

    }

    fun setFluidAnimation(){
        val topLeftAnimationForward =
                AnimatedVectorDrawableCompat.create(this,
                    R.drawable.top_left_liquid_forward
                )
        val topLeftAnimationReverse =
                AnimatedVectorDrawableCompat.create(this,
                    R.drawable.top_left_liquid_reverse
                )

        val bottomRightAnimationForward =
                AnimatedVectorDrawableCompat.create(this,
                    R.drawable.bottom_right_liquid_forward
                )
        val bottomRightAnimationReverse =
                AnimatedVectorDrawableCompat.create(this,
                    R.drawable.bottom_right_liquid_reverse
                )

        val topLeftImageView = (findViewById<ImageView>(R.id.imgFluidTopLeft)).apply {
            setImageDrawable(topLeftAnimationForward)
        }
        val bottomRightImageView =
                (findViewById<ImageView>(R.id.imgFluidBottomRight)).apply {
                    setImageDrawable(bottomRightAnimationForward)
                }

        topLeftAnimationForward?.registerAnimationCallback(object :
                Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                topLeftImageView.setImageDrawable(topLeftAnimationReverse)
                topLeftAnimationReverse?.start()

            }
        })
        topLeftAnimationReverse?.registerAnimationCallback(object :
                Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                topLeftImageView.setImageDrawable(topLeftAnimationForward)
                topLeftAnimationForward?.start()
            }
        })

        bottomRightAnimationForward?.registerAnimationCallback(object :
                Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                bottomRightImageView.setImageDrawable(bottomRightAnimationReverse)
                bottomRightAnimationReverse?.start()
            }
        })
        bottomRightAnimationReverse?.registerAnimationCallback(object :
                Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                bottomRightImageView.setImageDrawable(bottomRightAnimationForward)
                bottomRightAnimationForward?.start()
            }
        })

        topLeftAnimationForward?.start()
        bottomRightAnimationForward?.start()
    }
}