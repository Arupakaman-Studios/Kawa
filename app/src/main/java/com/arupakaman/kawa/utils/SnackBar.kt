package com.arupakaman.kawa.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Color.BLACK
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.arupakaman.kawa.R
import com.google.android.material.snackbar.Snackbar

const val FONT_PATH="libre_baskerville_regular.otf"

fun Context.showSnackBarMessage(rootView: View, message:String, showDismissButton:Boolean=false, length:Int=Snackbar.LENGTH_SHORT){

    val snackbar= Snackbar.make(rootView, message, length).changeFont()
    snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
    if (showDismissButton)
        snackbar.setAction(getString(R.string.dismiss)) { snackbar.dismiss() }
    snackbar.show()
}

fun Snackbar.changeFont():Snackbar
{
    val tv = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    tv.setTextColor(Color.WHITE)
    val font = ResourcesCompat.getFont(context,R.font.libre_baskerville_regular)// Typeface.createFromAsset(context.assets, FONT_PATH)
    tv.typeface = font
    return this
}