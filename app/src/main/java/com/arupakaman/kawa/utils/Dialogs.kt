@file:Suppress("unused")

package com.arupakaman.kawa.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.arupakaman.kawa.R
import com.arupakaman.kawa.data.pref.MyAppPref
import com.arupakaman.kawa.databinding.DialogChangeThemeBinding
import com.arupakaman.kawa.ui.koans.KoansActivitySharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.hypot


class MyDialog(context: Context) : Dialog(context) {
    private var tapOutside:()->Unit={}

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isShowing && event.action == MotionEvent.ACTION_DOWN && isOutOfBounds(context, event) && window!!.peekDecorView() != null) {
            tapOutside.invoke()
        }
        return false
    }

    private fun isOutOfBounds(context: Context, event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        val slop = ViewConfiguration.get(context).scaledWindowTouchSlop
        val decorView = window!!.decorView
        return (x < -slop || y < -slop
                || x > decorView.width + slop
                || y > decorView.height + slop)
    }

    fun onTapOutside(tapOutside: () -> Unit){
        this.tapOutside=tapOutside
    }
}

fun Activity.getMyDialog(root: View): MyDialog {
    val d = MyDialog(this)
    d.window?.requestFeature(Window.FEATURE_NO_TITLE)
    d.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    d.setContentView(root)
    return d
}

const val FONT_SIZE_MIN = 16
const val FONT_SIZE_MAX= 20

fun AppCompatActivity.showChangeThemeDialog(koansActivitySharedViewModel: KoansActivitySharedViewModel){
    val binding = DialogChangeThemeBinding.inflate(LayoutInflater.from(this))
    val dialog = getMyDialog(binding.root)

    //dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation



    binding.run {

        var viewPreviousTypeface:View = when(MyAppPref.koanTypeface){
            MyAppPref.TYPEFACE_SANS_SERIF -> txtSansSerif
            MyAppPref.TYPEFACE_BI_MINCHO -> txtBiMincho
            else->txtSerif
        }
        fun setTypeface(typeface: Typeface, type: Int){
            txtFontSizePreview.typeface = typeface
            MyAppPref.koanTypeface=type
            koansActivitySharedViewModel.setKoanTypeface(typeface)
        }

        txtSansSerif.combineClick(txtBiMincho, txtSerif){
            val (typeface, type) = when (it) {
                txtSansSerif -> Pair(Typeface.SANS_SERIF, MyAppPref.TYPEFACE_SANS_SERIF)
                txtBiMincho -> Pair(
                    ResourcesCompat.getFont(
                        applicationContext,
                        R.font.sawarabi_mincho_regular
                    ), MyAppPref.TYPEFACE_BI_MINCHO
                )
                else -> Pair(Typeface.SERIF, MyAppPref.TYPEFACE_SERIF)
            }
            viewPreviousTypeface.isSelected=false
            it.isSelected=true

            viewPreviousTypeface=it

            if (typeface!=null)
                setTypeface(typeface, type)
        }

        viewPreviousTypeface.performClick()

        suspend fun setKoanTextSize(textSize: Int) = withContext(Dispatchers.Default){
            koansActivitySharedViewModel.setKoanTextSize(textSize)

            withContext(Dispatchers.Main){
                txtFontSizePreview.textSize = getDimenFromSize(textSize)
            }
        }

        var currentSize = MyAppPref.koanTextSize
        lifecycleScope.launch(Dispatchers.Default){
            setKoanTextSize(currentSize)
        }


        viewPlus.combineClick(viewMinus){

            lifecycleScope.launch(Dispatchers.Default){
                if (it==viewPlus)
                {
                    if (currentSize>=FONT_SIZE_MAX)
                        return@launch
                    currentSize++
                }
                else{
                    if (currentSize<=FONT_SIZE_MIN)
                        return@launch
                    currentSize--
                }
                MyAppPref.koanTextSize=currentSize
                setKoanTextSize(currentSize)
            }
        }

        viewDay.combineClick(viewNight){
            if (dialog.isShowing)
                dialog.dismiss()
            when (it){
                viewDay-> updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
                viewNight-> updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
        /*viewDay.onClick {
            //ThemeRevealer.newThemeSelected(this@showChangeThemeDialog, viewDay.x.roundToInt(),viewDay.y.roundToInt(),false)

            updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
        }*/

        /*viewNight.onClick {
            //ThemeRevealer.newThemeSelected(this@showChangeThemeDialog, viewNight.x.roundToInt(),viewNight.y.roundToInt(),true)
            updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
        }*/
    }

    dialog.setOnShowListener {
        revealShow(binding.root, true, null)
    }

    dialog.setOnKeyListener(DialogInterface.OnKeyListener { _, i, _ ->
        if (i == KeyEvent.KEYCODE_BACK) {
            revealShow(binding.root, false, dialog)
            return@OnKeyListener true
        }
        false
    })

    dialog.onTapOutside{
        revealShow(binding.root, false, dialog)
    }

    dialog.show()


}

fun Context.getDimenFromSize(size: Int): Float {
    return size.toFloat()
   /* val dimenName = "_${size}ssp"
    val resourceId = resources.getIdentifier(dimenName, "dimen", packageName)
    return resources.getDimension(resourceId)*/
}

fun AppCompatActivity.updateTheme(mode: Int){
    AppCompatDelegate.setDefaultNightMode(mode)
    delegate.applyDayNight()
    MyAppPref.themeMode=mode
    //recreate()
}

private fun revealShow(view: View, b: Boolean, dialog: Dialog?) {
    val w = view.width
    val h = view.height
    val endRadius = hypot(w.toDouble(), h.toDouble()).toInt()
    val cx = (view.x + view.width / 2).toInt()
    val cy: Int = (view.y + view.height / 2).toInt() //view.y.toInt() + view.height + 56
    if (b) {
        val revealAnimator: Animator =
            ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, endRadius.toFloat())
        view.visibility = View.VISIBLE
        revealAnimator.duration = 700
        revealAnimator.start()
    } else {
        val anim: Animator =
            ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius.toFloat(), 0f)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                if (dialog != null) {
                    dialog.dismiss()
                    view.visibility = View.INVISIBLE
                }

            }
        })
        anim.duration = 700
        anim.start()
    }
}