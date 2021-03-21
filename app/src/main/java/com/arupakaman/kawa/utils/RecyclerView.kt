package com.arupakaman.kawa.utils

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addOnScrollListenerToHideKeyboard(act: Activity, fragmentView: View?=null){

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy > 0)
                act.hideKeyboard(fragmentView)
        }
    })
}