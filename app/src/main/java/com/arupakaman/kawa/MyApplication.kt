package com.arupakaman.kawa

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.arupakaman.kawa.data.pref.MyAppPref
import com.arupakaman.kawa.work.NotificationWork
import com.flavours.AdManager
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

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

        val periodicRequest = PeriodicWorkRequestBuilder<NotificationWork>(7, TimeUnit.DAYS)
            .setInitialDelay(7, TimeUnit.DAYS)
            .setConstraints(constraint).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(NotificationWork.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,periodicRequest)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}