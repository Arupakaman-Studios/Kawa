package com.arupakaman.kawa

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.arupakaman.kawa.data.pref.MyAppPref

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MyAppPref.init(applicationContext)

        AppCompatDelegate.setDefaultNightMode(MyAppPref.themeMode)
    }
}