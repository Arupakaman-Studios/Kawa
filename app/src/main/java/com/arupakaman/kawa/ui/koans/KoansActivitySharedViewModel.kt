package com.arupakaman.kawa.ui.koans

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.*
import com.arupakaman.kawa.BuildConfig
import com.arupakaman.kawa.R
import com.arupakaman.kawa.data.database.KoansDatabase
import com.arupakaman.kawa.data.database.dao.KoanDao
import com.arupakaman.kawa.data.database.entities.Koan
import com.arupakaman.kawa.data.pref.MyAppPref
import com.arupakaman.kawa.model.HighlightedKoans
import com.arupakaman.kawa.model.KoanImage
import com.arupakaman.kawa.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class KoansActivitySharedViewModel @Inject constructor(@ApplicationContext application: Context, val koanDao: KoanDao) : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    private val myApplication = application

    private val _liveWallpaperResult = MutableLiveData<Event<Boolean>>()
    val liveWallpaperResult : LiveData<Event<Boolean>>
        get() = _liveWallpaperResult

    private val _liveCurrentKoan = MutableLiveData<Koan>()
    val liveCurrentKoan : LiveData<Koan>
        get() = _liveCurrentKoan

    val currentImage : KoanImage?
        get() = _liveCurrentKoan.value?.koanImage

    //private val koanDao by lazy { KoansDatabase.getKoanDao(myApplication) }

    private val _liveKoanTextSize by lazy { MutableLiveData<Int>() }
    val liveKoanTextSize : LiveData<Int>
        get() = _liveKoanTextSize

    private val _liveKoanTypeFace by lazy { MutableLiveData<Typeface>() }
    val liveKoanTypeFace : LiveData<Typeface>
        get() = _liveKoanTypeFace

    private val _liveKoanListForDetail by lazy { MediatorLiveData<List<Any>>() }
    val liveKoanListForDetail : LiveData<List<Any>>
        get() = _liveKoanListForDetail

    private val _liveOpenDrawer by lazy { MutableLiveData<Event<Unit>>() }
    val liveOpenDrawer : LiveData<Event<Unit>>
        get() = _liveOpenDrawer


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

        setAllKoansForDetail()
    }

    fun setAllKoansForDetail(){
        _liveKoanListForDetail.addSource(koanDao.getAllKoans()){
            _liveKoanListForDetail.postValue(it)
        }
    }

    fun setKoanListForDetailByHighlightedKoan(list: List<HighlightedKoans>)=viewModelScope.launch(Dispatchers.Default){
        _liveKoanListForDetail.postValue(list.map { it.koan })
    }

    fun setKoanListForDetail(list: List<Any>)=viewModelScope.launch(Dispatchers.Default){
        _liveKoanListForDetail.postValue(list)
    }



    fun setKoanTextSize(textSize:Int) = viewModelScope.launch(Dispatchers.Default){
        _liveKoanTextSize.postValue(textSize)
    }

    fun setKoanTypeface(typeface: Typeface?)=viewModelScope.launch(Dispatchers.Default){
        if (typeface!=null)
            _liveKoanTypeFace.postValue(typeface)
    }

    fun setOpenDrawerEvent(){
        _liveOpenDrawer.postValue(Event(Unit))
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
                myApplication.showNotification(NOTIFICATION_CHANNEL_ESSENTIAL, toLaunch, fileName, "saved at $filePath")
            }
        }
        else{
            // show error
        }
    }

    private val _liveShareData = MutableLiveData<Event<Pair<Bitmap,String>>>()
    val liveShareData : LiveData<Event<Pair<Bitmap,String>>>
        get() = _liveShareData
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

            _liveShareData.postValue(Event(Pair(bitmap, "${koan.title}:\n\n${firstParagraph}\n\n\n$trailingShareText")))
            //myApplication.shareData()
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