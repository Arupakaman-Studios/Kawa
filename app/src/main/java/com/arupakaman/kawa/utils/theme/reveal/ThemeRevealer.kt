package com.arupakaman.kawa.utils.theme.reveal

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import com.arupakaman.kawa.ui.revealer.ThemeRevealerScreenshotActivity
import com.arupakaman.kawa.utils.Falcon

object ThemeRevealer {

        const val SCREENSHOT_X="SCREENSHOT_X"
        const val SCREENSHOT_Y="SCREENSHOT_Y"
        const val TO_DARK="TO_DARK"
        var screenshot : Bitmap?= null

    private fun Activity.getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun newThemeSelected(activity:Activity, posX:Int, posY:Int, toDark:Boolean) {
        val windowBitmap = Falcon.takeScreenshotBitmap(activity)
        val statusBarHeight = activity.getStatusBarHeight()
        val bitmap = Bitmap.createBitmap(windowBitmap, 0, statusBarHeight, windowBitmap.width, windowBitmap.height - statusBarHeight, null, true)
        screenshot= bitmap
        // From the calling activity.
        val intent = Intent(activity, ThemeRevealerScreenshotActivity::class.java).apply {
            putExtra(SCREENSHOT_X, posX)
            putExtra(SCREENSHOT_Y, posY)
            putExtra(TO_DARK,toDark)
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }
        activity.startActivity(intent)
        // 0 param means no animation.
        activity.overridePendingTransition(0, 0)
    }
}