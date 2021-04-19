package com.arupakaman.kawa.utils

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class PermissionChecker(private val activity: AppCompatActivity) {

    fun checkPermission(permission: String): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            val result = ContextCompat.checkSelfPermission(activity.applicationContext,permission)

            result == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    fun requestPermission(permission: String){
        ActivityCompat.requestPermissions(activity,arrayOf(permission),200)
    }
}