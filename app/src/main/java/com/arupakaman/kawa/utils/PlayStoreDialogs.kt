package com.arupakaman.kawa.utils

/*
import android.app.Activity
import android.util.Log
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.testing.FakeReviewManager

fun Activity.showInAppReviewDialog(){
    val manager = FakeReviewManager(this)//ReviewManagerFactory.create(applicationContext)
    val request = manager.requestReviewFlow()
    request.addOnCompleteListener {task->
        if (task.isSuccessful){
            val reviewInfo = task.result
            val flow = manager.launchReviewFlow(this,reviewInfo)
            flow.addOnCompleteListener {
                if (it.isSuccessful)
                    Log.d("review","launch review successful")
                else
                    Log.d("review","launch review unsuccessful")
            }
        }
        else{
            Log.d("review","request unsuccessful")
        }
    }
}*/
