package com.arupakaman.kawa

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.arupakaman.kawa.data.pref.MyAppPref
import com.flavours.AdManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MyAppPref.init(applicationContext)

        AppCompatDelegate.setDefaultNightMode(MyAppPref.themeMode)

       // MobileAds.initialize(this)
        AdManager.initialize(applicationContext)
    }
}