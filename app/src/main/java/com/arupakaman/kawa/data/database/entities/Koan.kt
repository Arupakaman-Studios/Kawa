package com.arupakaman.kawa.data.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.arupakaman.kawa.data.database.*
import com.arupakaman.kawa.model.KoanImage
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_KOAN)
data class Koan(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    var id:Long=0,

    @ColumnInfo(name = KOAN_TITLE, index = true, collate = ColumnInfo.NOCASE)
    var title:String="",

    @ColumnInfo(name = KOAN, index = true, collate = ColumnInfo.NOCASE)
    var koan:String="",

    @ColumnInfo(name = KOAN_IMAGE)
    var koanImagePath:String="",

    @Ignore
    val koanImage:KoanImage = KoanImage.getRandomKoanImage()

): Parcelable