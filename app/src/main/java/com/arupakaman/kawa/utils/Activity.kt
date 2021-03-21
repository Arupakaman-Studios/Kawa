package com.arupakaman.kawa.utils

import android.app.Activity
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.arupakaman.kawa.R

fun Activity.hideKeyboard(fragView: View?=null){

    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view: View? = currentFocus
    if (view == null) {
        view = if (fragView!=null)
            fragView.rootView
        else
            View(this)
    }
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
    view?.clearFocus()
}

fun AppCompatActivity.makeItFullScreenStatusBarHidden(){
    supportActionBar?.hide()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.hide(WindowInsets.Type.statusBars())
    } else {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}

fun Drawable.setColorFilter(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
    } else {
        setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}

fun AppCompatActivity.setupToolbar(title: String, backStatus: Boolean, showNavigationIcon: Boolean = false) {
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    supportActionBar?.setDisplayHomeAsUpEnabled(backStatus)
    supportActionBar?.setDisplayShowTitleEnabled(false)
    toolbar.title = title

    when {
        showNavigationIcon -> toolbar.setNavigationIcon(R.drawable.ic_circle_boundary)
        backStatus -> {
            toolbar.setContentInsetsRelative(0, 0)
            toolbar.contentInsetStartWithNavigation = 0
            val drawable = toolbar.navigationIcon
            drawable?.setColorFilter(android.R.color.white)
        }
    }
}