package com.arupakaman.kawa.utils

import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

const val KEYBOARD_SHOWN=1
const val KEYBOARD_HIDDEN=2

fun RecyclerView.addOnScrollListenerToKeyboardHandling(act: Activity, fragmentView: View?=null, editText: EditText,showKeyboard:Boolean){

    val linearLayoutManager = if (layoutManager is LinearLayoutManager) layoutManager as LinearLayoutManager else null
    var lastState = 0
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            if (showKeyboard){
                if (abs(dy)<10)
                {
                    return
                }
            }

            if (dy > 0) {
                act.hideKeyboard(fragmentView)
                lastState = KEYBOARD_HIDDEN
            }
            else{
                if (showKeyboard){
                    if(linearLayoutManager?.findFirstCompletelyVisibleItemPosition()==0)
                    {
                        if (lastState!=KEYBOARD_SHOWN)
                        {
                            editText.showKeyboard()
                            lastState = KEYBOARD_SHOWN
                        }

                    }
                }
            }
        }
    })
}


fun RecyclerView.applyAnimation(@AnimRes animRes:Int){
    if (tag!="1") {
        tag = "1"
        val controller =
            AnimationUtils.loadLayoutAnimation(context, animRes)
        layoutAnimation = controller
        scheduleLayoutAnimation()
    }

}