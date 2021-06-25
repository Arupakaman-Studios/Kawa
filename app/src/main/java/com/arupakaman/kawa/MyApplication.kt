package com.arupakaman.kawa

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.arupakaman.kawa.data.pref.MyAppPref
import com.arupakaman.kawa.work.NotificationWork
import com.flavours.AdManager
import com.google.firebase.messaging.FirebaseMessaging
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MyAppPref.init(applicationContext)

        AppCompatDelegate.setDefaultNightMode(MyAppPref.themeMode)

       // MobileAds.initialize(this)
        AdManager.initialize(applicationContext)

        scheduleWork(applicationContext)

        FirebaseMessaging.getInstance().subscribeToTopic("all");
    }

    private fun scheduleWork(context: Context){

        val constraint = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicRequest = PeriodicWorkRequestBuilder<NotificationWork>(20, TimeUnit.MINUTES)
            .setInitialDelay(20, TimeUnit.MINUTES)
            .setConstraints(constraint).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(NotificationWork.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,periodicRequest)
    }
}