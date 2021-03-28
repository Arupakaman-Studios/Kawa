package com.arupakaman.kawa.utils

import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.arupakaman.kawa.BuildConfig
import com.arupakaman.kawa.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.random.Random
import kotlin.Result as Result1


suspend fun Context.setResourceAsWallpaper(@DrawableRes imgRes: Int): Boolean = withContext(
    Dispatchers.Default
) {
        val wallpaperManager = WallpaperManager.getInstance(this@setResourceAsWallpaper)

        val result = kotlin.runCatching {
            val bitmap = BitmapFactory.decodeResource(resources, imgRes)
            wallpaperManager.setBitmap(bitmap)
            true
        }

        Log.e("setWallpaper: %s", result.exceptionOrNull().toString())
        return@withContext result.getOrNull() ?: false
    }

@RequiresApi(Build.VERSION_CODES.Q)
suspend fun Context.savePhotoViaScopedStorage(fileName: String, @DrawableRes resourceId: Int): Uri? = withContext(
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

        val bitmap = BitmapFactory.decodeResource(resources, resourceId)

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
            //5
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
        val newImageUri = contentResolver.insert(collection, newImage)
        //6
        contentResolver.openOutputStream(newImageUri!!, "w").use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        newImage.clear()
        //7
        newImage.put(MediaStore.Images.Media.IS_PENDING, 0)
        //8
        contentResolver.update(newImageUri, newImage, null, null)

        newImageUri
    }
    result.printResult("savePhotoViaScopedStorage")
    return@withContext result.getOrNull()
}

@Suppress("DEPRECATION")
suspend fun Context.savePhotoViaLegacyStorage(fileName: String, @DrawableRes resourceId: Int):Uri? = withContext(
    Dispatchers.IO
){
    val result = kotlin.runCatching {
        val bm = BitmapFactory.decodeResource(resources, resourceId)

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

        file.getImageContentUri(this@savePhotoViaLegacyStorage)
    }
    result.printResult("savePhotoViaLegacyStorage")

    return@withContext result.getOrNull()
}


fun Context.showNotification(intent: Intent, title: String, message: String, type: String)
{
    val notificationManager=applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val  notificationBuilder: NotificationCompat.Builder

    /*val intent = Intent(applicationContext, HomeActivity::class.java)
    intent.putExtra(StaticKeysClass.COMING_FROM,StaticKeysClass.COMING_FROM_NOTIFICATION)
    intent.putExtra(StaticKeysClass.PUSH_NOTIFICATION_TYPE, type)*/
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

    val pendingIntent= PendingIntent.getActivity(
        applicationContext, Random.nextInt(), intent,
        PendingIntent.FLAG_ONE_SHOT
    )

    if (Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O)
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
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

    notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
}




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

        /*val intent: Intent = ShareCompat.IntentBuilder.from(this)
            .setType("image/jpg")
            .setSubject(getString(R.string.share_subject))
            .setStream(contentUri)
            .setChooserTitle(R.string.share_title)
            .createChooserIntent()
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(intent)*/

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


fun Result1<*>.printResult(tag: String) {
    if (isSuccess)
        Log.d(tag, "success, ${this.getOrNull()}")
    else
        Log.e(tag, "failure, ${this.exceptionOrNull()}")

}

