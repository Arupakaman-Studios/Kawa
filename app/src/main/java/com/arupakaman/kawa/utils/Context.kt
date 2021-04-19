package com.arupakaman.kawa.utils

import android.app.*
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.arupakaman.kawa.BuildConfig
import com.arupakaman.kawa.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random
import kotlin.Result as Result1

fun Context.isNightMode(): Boolean {
    val defaultNightMode = AppCompatDelegate.getDefaultNightMode()
    if (defaultNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
        return true
    }
    if (defaultNightMode == AppCompatDelegate.MODE_NIGHT_NO) {
        return false
    }
    val currentNightMode = (resources.configuration.uiMode
            and Configuration.UI_MODE_NIGHT_MASK)
    when (currentNightMode) {
        Configuration.UI_MODE_NIGHT_NO -> return false
        Configuration.UI_MODE_NIGHT_YES -> return true
        Configuration.UI_MODE_NIGHT_UNDEFINED -> return false
    }
    return false
}

@Suppress("unused")
fun Context.getColorFromAttribute(@AttrRes attrRes:Int):Int{
    val typedValue = TypedValue()
    val theme: Resources.Theme = theme
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}

fun Context.openAppSettings(){
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}

/**
 * Set the given drawable resource as wallpaper
 *
 * @param imgRes : drawable resource to set as wallpaper
 * @return true if succeed and false for failure
 */
suspend fun Context.setResourceAsWallpaper(@DrawableRes imgRes: Int): Boolean = withContext(
    Dispatchers.Default
) {
        val wallpaperManager = WallpaperManager.getInstance(this@setResourceAsWallpaper)

        val result = kotlin.runCatching {
            val bitmap = getBitmapOfResourceViaGlide(imgRes)
            wallpaperManager.setBitmap(bitmap)
            true
        }

        result.printResult("setWallpaper")

        return@withContext result.getOrNull() ?: false
    }


/**
 * save the given drawable resource into Pictures/kawa directory via scoped storage
 *
 * @param fileName: name of the file to be saved
 * @param resourceId: drawable resource to be saved as file
 *
 * @return Uri of the saved file
 */
@RequiresApi(Build.VERSION_CODES.Q)
suspend fun Context.savePhotoViaScopedStorage(fileName: String, @DrawableRes resourceId: Int): Pair<Uri?,String>? = withContext(
    Dispatchers.IO
){

    val result = kotlin.runCatching {
        val collection =
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val dirDest =
            File(Environment.DIRECTORY_PICTURES, getString(R.string.app_name))
        val date = System.currentTimeMillis()
        val extension = "jpeg"//Utils.getImageExtension(format)
        //3

        //val bitmap = BitmapFactory.decodeResource(resources, resourceId)
        val bitmap = getBitmapOfResourceViaGlide(resourceId)

        val newImage = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.$extension")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/$extension")
            put(MediaStore.MediaColumns.DATE_ADDED, date)
            put(MediaStore.MediaColumns.DATE_MODIFIED, date)
            put(MediaStore.MediaColumns.SIZE, bitmap.byteCount)
            put(MediaStore.MediaColumns.WIDTH, bitmap.width)
            put(MediaStore.MediaColumns.HEIGHT, bitmap.height)
            //4
            put(MediaStore.MediaColumns.RELATIVE_PATH, "$dirDest${File.separator}")
            //set is pending when writing data so other apps can't use this file at this time
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
        val newImageUri = contentResolver.insert(collection, newImage)
        //6
        contentResolver.openOutputStream(newImageUri!!, "w").use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        newImage.clear()
        //once writing is done reset the value
        newImage.put(MediaStore.Images.Media.IS_PENDING, 0)
        //8
        contentResolver.update(newImageUri, newImage, null, null)

        val filePath = getFullPathFromContentUri(this@savePhotoViaScopedStorage,newImageUri)?:""
        Log.d("newImageUri filePath", filePath)


        Pair(newImageUri,filePath)
    }
    result.printResult("savePhotoViaScopedStorage")
    return@withContext result.getOrNull()
}


/**
 * save the given drawable resource into Pictures/kawa directory via legacy storage
 *
 * @param fileName: name of the file to be saved
 * @param resourceId: drawable resource to be saved as file
 *
 * @return Uri of the saved file
 */
@Suppress("DEPRECATION")
@RequiresPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
suspend fun Context.savePhotoViaLegacyStorage(fileName: String, @DrawableRes resourceId: Int):Pair<Uri?,String>? = withContext(
    Dispatchers.IO
){
    val result = kotlin.runCatching {
        //val bm = BitmapFactory.decodeResource(resources, resourceId)

        val bm = getBitmapOfResourceViaGlide(resourceId)

        val directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()+File.separator+ getString(
            R.string.app_name
        )
        val dirDest = File(directoryPath)

        if (!dirDest.exists())
        {
            dirDest.mkdirs()
        }

        val extension = "jpeg"

        val file = File(dirDest, "$fileName.$extension")
        val outStream = FileOutputStream(file)
        bm.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        outStream.flush()
        outStream.close()

        Pair(file.getImageContentUri(this@savePhotoViaLegacyStorage),file.path)
    }
    result.printResult("savePhotoViaLegacyStorage")

    return@withContext result.getOrNull()
}

/**
 * Fire a notification for the given title and message
 *
 * @param intent this intent will be used as pending intent
 * @param title notification title
 * @param message notification text
 *
 */
@Suppress("DEPRECATION")
fun Context.showNotification(intent: Intent, title: String, message: String)
{
    val notificationManager=applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val  notificationBuilder: NotificationCompat.Builder

    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

    val pendingIntent= PendingIntent.getActivity(
        applicationContext, Random.nextInt(), intent,
        PendingIntent.FLAG_ONE_SHOT
    )

    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
    {
        notificationBuilder= NotificationCompat.Builder(applicationContext, title)
        val notificationChannel= NotificationChannel(
            title,
            title,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val audioAttributes= AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION).build()

        notificationChannel.setSound(
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
            audioAttributes
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }
    else
    {
        notificationBuilder= NotificationCompat.Builder(applicationContext)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
    }

    val textStyle= NotificationCompat.BigTextStyle()
    textStyle.bigText(message)

    notificationBuilder
        .setContentTitle(title)
        .setContentText(message)
        .setContentIntent(pendingIntent)
        .setStyle(textStyle)
        .setAutoCancel(true)
        .setColor(Color.parseColor("#FF5D3E"))
        .setSmallIcon(R.drawable.ic_logo)


    notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
}


/**
 * shares text and image with other apps.
 *
 * <p> Saves the bitmap in cache directory and share it's content uri with other apps, via file provider {@link R.xml#fileprovider}
 *
 * @param bitmap file as bitmap which need to be shared
 * @param text content need to be shared
 *
 */
fun Context.shareData(bitmap: Bitmap, text:String) {
    // save bitmap to cache directory
    try {
        val cachePath = File(cacheDir, "image")
        cachePath.mkdirs() // don't forget to make the directory
        val stream = FileOutputStream("$cachePath/image.png") // overwrites this image every time
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    val imagePath = File(cacheDir, "image")
    val newFile = File(imagePath, "image.png")
    val contentUri = FileProvider.getUriForFile(
        this,
        BuildConfig.APPLICATION_ID + ".provider",
        newFile
    )
    if (contentUri != null) {

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
        shareIntent.setDataAndType(contentUri, contentResolver.getType(contentUri))
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        shareIntent.putExtra(Intent.EXTRA_TEXT,text)
        shareIntent.type = "image/png"
        startActivity(Intent.createChooser(shareIntent, "Choose an app"))
    }
}

suspend fun Context.getBitmapOfResourceViaGlide(imageResource:Int):Bitmap = suspendCoroutine{cont->
    Glide.with(this@getBitmapOfResourceViaGlide)
        .asBitmap()
        .load(imageResource)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                cont.resume(resource)
            }
            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })
}

/**
 * Just prints the success or failure of Kotlin Result
 */
fun Result1<*>.printResult(tag: String) {
    if (isSuccess)
        Log.d(tag, "success, ${this.getOrNull()}")
    else
        Log.e(tag, "failure, ${this.exceptionOrNull()}")

}



/**
 * Share this App
 */
fun Activity.shareApp() {
    ShareCompat.IntentBuilder.from(this).run {
        setText(getString(R.string.msg_share_app,BuildConfig.APPLICATION_ID))
        setSubject(getString(R.string.app_name))
        setChooserTitle(R.string.title_share_via)
        setType("text/plain")
        intent
    }.also {shareIntent->
        fireSafeIntent(shareIntent,getString(R.string.err_no_app_can_share_it))
    }
}


/**
 * Redirect to paid version of app
 */
fun Context.openDonationVersion(){
    openAppInPlayStore(getString(R.string.donate_version_pkg_name))
}

/**
 * Will open play store page of any application whose id is given as parameter
 */
fun Context.openAppInPlayStore(id: String){
    kotlin.runCatching {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$id"))
        startActivity(intent)
    }.onFailure {
        val optionalIntent =  Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$id"))
        startActivity(optionalIntent)
    }
}


/**
 * Fire the intent and show error message in toast
 */
fun Context.fireSafeIntent(intent: Intent,failureMessage:String){
    try {
        startActivity(intent)
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(this, failureMessage, Toast.LENGTH_LONG).show()
    }
}