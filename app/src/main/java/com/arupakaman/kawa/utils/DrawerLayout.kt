package com.arupakaman.kawa.utils

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import java.lang.reflect.Field

fun DrawerLayout.onDrawerOpened(doThis: () -> Unit){
    addDrawerListener(object : DrawerLayout.DrawerListener {
        override fun onDrawerStateChanged(newState: Int) {

        }

        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

        }

        override fun onDrawerClosed(drawerView: View) {

        }

        override fun onDrawerOpened(drawerView: View) {
            doThis.invoke()
        }
    })
}