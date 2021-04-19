package com.arupakaman.kawa.ui.koans

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arupakaman.kawa.BuildConfig
import com.arupakaman.kawa.R
import com.arupakaman.kawa.data.database.KoansDatabase
import com.arupakaman.kawa.data.database.entities.Koan
import com.arupakaman.kawa.data.pref.MyAppPref
import com.arupakaman.kawa.model.KoanImage
import com.arupakaman.kawa.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis


class KoansActivitySharedViewModel(application: Application) : AndroidViewModel(application) {

    private val myApplication = application

    private val _liveWallpaperResult = MutableLiveData<Event<Boolean>>()
    val liveWallpaperResult : LiveData<Event<Boolean>>
        get() = _liveWallpaperResult

    private val _liveCurrentKoan = MutableLiveData<Koan>()
    val liveCurrentKoan : LiveData<Koan>
        get() = _liveCurrentKoan

    val currentImage : KoanImage?
        get() = _liveCurrentKoan.value?.koanImage

    private val koanDao by lazy { KoansDatabase.getKoanDao(myApplication) }

    private val _liveKoanTextSize by lazy { MutableLiveData<Int>() }
    val liveKoanTextSize : LiveData<Int>
        get() = _liveKoanTextSize

    private val _liveKoanTypeFace by lazy { MutableLiveData<Typeface>() }
    val liveKoanTypeFace : LiveData<Typeface>
        get() = _liveKoanTypeFace


    init {
        setKoanTextSize(MyAppPref.koanTextSize)

        when(MyAppPref.koanTypeface){
            MyAppPref.TYPEFACE_SERIF-> setKoanTypeface(Typeface.SERIF)
            MyAppPref.TYPEFACE_BI_MINCHO-> setKoanTypeface(
                ResourcesCompat.getFont(
                myApplication,
                R.font.sawarabi_mincho_regular
            ))
            MyAppPref.TYPEFACE_SANS_SERIF-> setKoanTypeface(Typeface.SANS_SERIF)
        }

    }

    fun setKoanTextSize(textSize:Int) = viewModelScope.launch(Dispatchers.Default){
        _liveKoanTextSize.postValue(textSize)
    }

    fun setKoanTypeface(typeface: Typeface?)=viewModelScope.launch(Dispatchers.Default){
        if (typeface!=null)
            _liveKoanTypeFace.postValue(typeface)
    }

    /**
     * Set the given drawable resource as wallpaper
     *
     * @param imgResId : drawable resource to set as wallpaper
     */
    fun setWallpaper(@DrawableRes imgResId: Int){
        viewModelScope.launch(Dispatchers.Default){
            val timeTaken = measureTimeMillis {
                val isSuccess = myApplication.applicationContext.setResourceAsWallpaper(imgResId)
                _liveWallpaperResult.postValue(Event(isSuccess))
            }
            Log.d("setWallpaper, timeTaken",timeTaken.toString())
        }
    }

    /**
     * save the given drawable resource into external storage
     *
     * @param fileName: name of the file to be saved
     * @param imgResId: drawable resource to be saved as file
     */
    fun saveFileViaLegacyStorage(fileName: String, @DrawableRes imgResId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            if (ActivityCompat.checkSelfPermission(myApplication, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                val result =myApplication.savePhotoViaLegacyStorage(fileName, imgResId)
                showNotification(result, fileName)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun saveFileViaScopedStorage(fileName: String, @DrawableRes imgResId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val result = myApplication.savePhotoViaScopedStorage(fileName, imgResId)
            showNotification(result, fileName)
        }
    }

    private fun showNotification(result:Pair<Uri?,String>?, fileName: String){
        if (result!=null){
            val (fileUri,filePath) = result
            if (fileUri!=null)
            {
                val toLaunch = Intent()
                toLaunch.action = Intent.ACTION_VIEW
                toLaunch.setDataAndType(fileUri, MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpeg"))
                myApplication.showNotification(toLaunch, fileName, "saved at $filePath")
            }
        }
        else{
            // show error
        }
    }

    /**
     * share the koan image and koan text with other apps
     */
    fun shareKoan(koan: Koan){
        viewModelScope.launch(Dispatchers.IO){
            //val bitmap = BitmapFactory.decodeResource(myApplication.resources, koan.koanImage.imgResId)
            val bitmap = myApplication.getBitmapOfResourceViaGlide(koan.koanImage.imgResId)
            val koanInPlainText = koan.koan.toPlainText()
            val firstParagraphIndex = koanInPlainText.indexOf("\n")
            val firstParagraph = if (firstParagraphIndex!=-1) koanInPlainText.substring(0,firstParagraphIndex) else koanInPlainText
            Log.d("firstPara",firstParagraph)

            val trailingShareText = if (koanInPlainText.trim().length!=firstParagraph.trim().length)
            {
                myApplication.getString(R.string.download_app_to_read_complete_koan,BuildConfig.APPLICATION_ID)
            }
            else myApplication.getString(R.string.download_app_to_read_more_koan,BuildConfig.APPLICATION_ID)

            myApplication.shareData(bitmap, "${koan.title}:\n\n${firstParagraph}\n\n\n$trailingShareText")
        }
    }

    fun setCurrentKoan(koan: Koan){
        viewModelScope.launch(Dispatchers.Default){
            MyAppPref.currentKoanId=koan.id
            _liveCurrentKoan.postValue(koan)
        }
    }

    fun findCurrentKoan(){
        viewModelScope.launch(Dispatchers.IO){
            val currentKoanId = MyAppPref.currentKoanId
            val koan = if (currentKoanId!=-1L){
                koanDao.getKoanByKoanId(currentKoanId)
            }
            else{
                koanDao.getFirstKoan()
            }
            _liveCurrentKoan.postValue(koan)
        }
    }
}