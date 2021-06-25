package com.arupakaman.kawa.fcm

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.arupakaman.kawa.utils.NOTIFICATION_CHANNEL_UPDATES
import com.arupakaman.kawa.utils.showNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class KawaFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("newToken: %s", p0)
    }

    override fun onMessageReceived(remoteMsg: RemoteMessage) {
        super.onMessageReceived(remoteMsg)

        try {
            Log.d("notification: %s", remoteMsg.notification.toString())
            Log.d("notification: title", remoteMsg.notification?.title?:"")
            Log.d("notification: body", remoteMsg.notification?.body?:"")

            Log.d("ContentData: %s", remoteMsg.data.toString())
            Log.d("----", "------------------------------------------")
            val dataMap = remoteMsg.data
            for (entry in dataMap.entries) {
                val key = entry.key
                val value = entry.value
                Log.d("Key_value: %s", "Key: $key\nValue: $value")
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${remoteMsg.data["id"]}"))
            applicationContext.showNotification(NOTIFICATION_CHANNEL_UPDATES,intent,remoteMsg.data["title"].toString(),remoteMsg.data["body"].toString())

            Log.d("push: title: %s",remoteMsg.data["title"].toString())
            Log.d("push: body: %s",remoteMsg.data["body"].toString())
        }catch (e:Exception){
            Log.e("onMessageReceived",e.toString())
        }

    }
}