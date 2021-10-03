package com.arupakaman.kawa.work

import android.content.Context
import android.content.Intent
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.arupakaman.kawa.data.database.KoansDatabase
import com.arupakaman.kawa.data.database.dao.KoanDao
import com.arupakaman.kawa.ui.splash.SplashActivity
import com.arupakaman.kawa.utils.NOTIFICATION_CHANNEL_KOAN_REMINDER
import com.arupakaman.kawa.utils.showNotification
import com.arupakaman.kawa.utils.toPlainText
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWork @AssistedInject constructor(@Assisted val appContext: Context
                ,@Assisted params: WorkerParameters, val koanDao: KoanDao) : CoroutineWorker(appContext, params) {

    companion object{
        const val KEY_KOAN_ID="KEY_KOAN_ID"
        const val WORK_NAME="NotificationWork"
    }

    override suspend fun doWork(): Result {
        return try {
            //val koansDatabase = KoansDatabase.getKoanDao(appContext)
            val koan = koanDao.getRandomKoan()
            val intent = Intent(appContext,SplashActivity::class.java)
            intent.putExtra(KEY_KOAN_ID,koan.id)
            appContext.showNotification(NOTIFICATION_CHANNEL_KOAN_REMINDER, intent,koan.title,koan.koan.toPlainText())
            Result.success()
        }catch (e:Exception){
            Result.failure()
        }
    }

}