package com.arupakaman.kawa.ui.splash

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.arupakaman.kawa.R
import com.arupakaman.kawa.data.database.KoansDatabase
import com.arupakaman.kawa.ui.koans.KoansActivity
import com.arupakaman.kawa.utils.makeItFullScreenStatusBarHidden
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        makeItFullScreenStatusBarHidden()



        //MediaPlayerManager.init(applicationContext,this)

        lifecycleScope.launch(Dispatchers.Default){

            setFluidAnimation(applicationContext)

            delay(2500)
            KoansDatabase.getKoanDao(applicationContext).getFirstKoan()
            withContext(Dispatchers.Main){
                finish()
                val intent = Intent(applicationContext,KoansActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private suspend fun setFluidAnimation(context: Context) = withContext(Dispatchers.Default){
        val topLeftAnimationForward =
                AnimatedVectorDrawableCompat.create(context,
                    R.drawable.top_left_liquid_forward
                )
        val topLeftAnimationReverse =
                AnimatedVectorDrawableCompat.create(context,
                    R.drawable.top_left_liquid_reverse
                )

        val bottomRightAnimationForward =
                AnimatedVectorDrawableCompat.create(context,
                    R.drawable.bottom_right_liquid_forward
                )
        val bottomRightAnimationReverse =
                AnimatedVectorDrawableCompat.create(context,
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

        withContext(Dispatchers.Main){
            topLeftAnimationForward?.start()
            bottomRightAnimationForward?.start()
        }
    }
}