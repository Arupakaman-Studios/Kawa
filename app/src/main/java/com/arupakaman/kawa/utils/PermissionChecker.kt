package com.arupakaman.kawa.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

/**
 * If any class needed activity's reference we can pass it via annotating @ActivityContext,
 * and this context can be later type casted into Activity.
 * Well if we do this we need to use @ActivityScoped annotation also
 */
@ActivityScoped
class PermissionChecker @Inject constructor(@ActivityContext private val activityContext: Context) {

    fun checkPermission(permission: String): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            val result = ContextCompat.checkSelfPermission(activityContext.applicationContext,permission)

            result == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    fun requestPermission(permission: String){
        if (activityContext is Activity)
            ActivityCompat.requestPermissions(activityContext,arrayOf(permission),200)
    }
}