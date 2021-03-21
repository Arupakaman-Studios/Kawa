package com.arupakaman.kawa.ui.koans.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.arupakaman.kawa.database.KoansDatabase

class KoansListingViewModel(application: Application) : AndroidViewModel(application) {

    private val koanDao = KoansDatabase.getKoanDao(application)

    val liveKoans = koanDao.getAllKoans()
}