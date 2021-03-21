package com.arupakaman.kawa.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arupakaman.kawa.database.dao.KoanDao
import com.arupakaman.kawa.database.entities.Koan
import com.arupakaman.kawa.database.entities.KoanFts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Koan::class, KoanFts::class],version = 1, exportSchema = false)
abstract class KoansDatabase : RoomDatabase() {

    abstract val koanDao: KoanDao

    companion object{

        fun getKoanDao(context: Context) = getInstance(context).koanDao



        @Volatile
        private var INSTANCE: KoansDatabase?= null

        fun getInstance(context: Context): KoansDatabase {

            synchronized(this){

                var instance = INSTANCE
                if (instance==null){
                    instance = Room.databaseBuilder(context,KoansDatabase::class.java,"color_database")
                        //.createFromAsset("color_database.db")
                        .addCallback(RoomDatabaseCallback(context))
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance

                    // RoomDatabaseCallback not called until an operation is performed on database
                    // just to trigger the RoomDatabaseCallback we calling getRandomColor
                    GlobalScope.launch (Dispatchers.IO){
                        val listOfKoan = instance.koanDao.getAllKoans()

                        Log.d("getInstance","listOfKoan: ${listOfKoan.value?.size}")
                    }
                }
                return instance
            }

        }
    }
}