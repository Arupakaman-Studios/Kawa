package com.arupakaman.kawa.utils

import android.view.View
import kotlinx.coroutines.*

fun View.onClick(throttleDelay:Long=500L,perform:(view:View)->Unit){

    val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    var job: Job?=null

    setOnClickListener(View.OnClickListener {
        if (job?.isActive == true)
            return@OnClickListener
        job= uiScope.launch {
            perform(it)
            delay(throttleDelay)
        }
    })
}

fun View.combineClick(vararg view: View,throttleDelay:Long=500L,perform:(view:View)->Unit){

    val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    var job: Job?=null

    val listener = View.OnClickListener {
        if (job?.isActive == true)
            return@OnClickListener
        job= uiScope.launch {
            perform(it)
            delay(throttleDelay)
        }
    }

    setOnClickListener(listener)
    view.forEach {
        it.setOnClickListener(listener)
    }
}