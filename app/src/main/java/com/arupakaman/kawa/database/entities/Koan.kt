package com.arupakaman.kawa.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arupakaman.kawa.database.*
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
    var koanImagePath:String=""

): Parcelable