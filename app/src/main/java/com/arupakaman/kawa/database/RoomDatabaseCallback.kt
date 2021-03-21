package com.arupakaman.kawa.database

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.arupakaman.kawa.R
import com.arupakaman.kawa.database.entities.Koan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.BufferedReader

class RoomDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        Log.d("RoomDatabaseCallback", "onCreate called")

        GlobalScope.launch(Dispatchers.IO) {
            val list = initColorEntryList(context.resources)
            Log.d("RoomDatabaseCallback","list size: ${list.size}")
            KoansDatabase.getInstance(context).koanDao.insertAll(list)
            Log.d("RoomDatabaseCallback","all koans inserted")

            db.execSQL("INSERT INTO koans_fts(koans_fts) VALUES('rebuild')")
        }
    }

    private fun initColorEntryList(resources: Resources): List<Koan> {
        val inputStream = resources.openRawResource(R.raw.koans)
        val jsonProductsString = inputStream.bufferedReader().use(BufferedReader::readText)

        val arrKoans = ArrayList<Koan>()

        val jar = JSONArray(jsonProductsString)
        repeat(jar.length()){index->
            val job=jar.getJSONObject(index)
            Koan().apply {
                title=job.getString("title")
                koan=job.getString("koan")
//                koanImagePath=job.getString("image")
            }.also {
                arrKoans.add(it)
            }
        }
        return arrKoans
    }
}