package com.arupakaman.kawa.ui.koans

import android.app.Application
import android.content.Intent
import android.graphics.BitmapFactory
import android.webkit.MimeTypeMap
import androidx.annotation.DrawableRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arupakaman.kawa.database.entities.Koan
import com.arupakaman.kawa.model.KoanImage
import com.arupakaman.kawa.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class KoansActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val myApplication = application

    private val _liveWallpaperResult = MutableLiveData<Event<Boolean>>()
    val liveWallpaperResult : LiveData<Event<Boolean>>
        get() = _liveWallpaperResult

    fun setWallpaper(@DrawableRes imgResId: Int){
        viewModelScope.launch(Dispatchers.Default){
            val isSuccess = myApplication.applicationContext.setResourceAsWallpaper(imgResId)
            _liveWallpaperResult.postValue(Event(isSuccess))
        }
    }

    fun saveFile(fileName: String, @DrawableRes imgResId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val fileUri = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                myApplication.savePhotoViaScopedStorage(fileName, imgResId)
            }
            else{
                myApplication.savePhotoViaLegacyStorage(fileName, imgResId)
            }
            if (fileUri!=null)
            {
                val toLaunch = Intent()
                toLaunch.action = Intent.ACTION_VIEW
                toLaunch.setDataAndType(fileUri, MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpeg"))
                myApplication.showNotification(toLaunch,"File Saved","tap to open the file","")
            }
        }
    }

    fun shareKoan(koanImage: KoanImage){
        viewModelScope.launch(Dispatchers.IO){
            val bitmap = BitmapFactory.decodeResource(myApplication.resources, koanImage.imgResId)
            myApplication.shareData(bitmap,"sample text to search")
        }
    }
}