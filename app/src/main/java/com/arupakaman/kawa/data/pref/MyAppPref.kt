package com.arupakaman.kawa.data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.arupakaman.kawa.utils.FONT_SIZE_MIN
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object MyAppPref {

    const val TYPEFACE_SANS_SERIF=1
    const val TYPEFACE_SERIF=2
    const val TYPEFACE_BI_MINCHO=3

    private var pref: SharedPreferences?=null
    fun init(context: Context)
    {
        pref= context.getSharedPreferences("AppPref", Context.MODE_PRIVATE)
    }

    var currentKoanId by long()

    var koanTextSize by int(FONT_SIZE_MIN)

    var koanTypeface by int(TYPEFACE_BI_MINCHO)

    var themeMode by int(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

    var isKoanDetailShown by boolean(false)

    var isNotificationEnabled by boolean(true)

    //var isKoanDetailShownFromListing by boolean(false)

    // delegates for string preference
    fun string(defaultValue:String="",key:(KProperty<*>)->String = KProperty<*>::name): ReadWriteProperty<Any, String> =

        object : ReadWriteProperty<Any, String> {

            override fun getValue(thisRef: Any, property: KProperty<*>): String {
                return pref?.getString(key(property),defaultValue) ?:""

            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
                pref?.edit()?.putString(key(property),value)?.apply()
            }
     }

    // delegates for int preference
    private fun long(defaultValue: Long=-1, key:(KProperty<*>)->String = KProperty<*>::name) : ReadWriteProperty<Any, Long> = object : ReadWriteProperty<Any, Long>{

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
            pref?.edit()?.putLong(key(property),value)?.apply()
        }

        override fun getValue(thisRef: Any, property: KProperty<*>): Long {
            return pref?.getLong(key(property),defaultValue)?:-1
        }
    }

    fun int(defaultValue: Int=-1, key:(KProperty<*>)->String = KProperty<*>::name) : ReadWriteProperty<Any, Int> = object : ReadWriteProperty<Any, Int>{

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
            pref?.edit()?.putInt(key(property),value)?.apply()
        }

        override fun getValue(thisRef: Any, property: KProperty<*>): Int {
            return pref?.getInt(key(property),defaultValue)?:-1
        }
    }

    fun boolean(defaultValue: Boolean=false, key:(KProperty<*>)->String = KProperty<*>::name) : ReadWriteProperty<Any, Boolean> = object : ReadWriteProperty<Any, Boolean>{

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
            pref?.edit()?.putBoolean(key(property),value)?.apply()
        }

        override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
            return pref?.getBoolean(key(property),defaultValue)?:false
        }
    }
}