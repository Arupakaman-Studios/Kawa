package com.arupakaman.kawa.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import com.arupakaman.kawa.database.KOAN
import com.arupakaman.kawa.database.KOAN_TITLE
import com.arupakaman.kawa.database.TABLE_KOAN_FTS

@Entity(tableName = TABLE_KOAN_FTS)
@Fts4(contentEntity = Koan::class)
data class KoanFts(

        /*@ColumnInfo(name = KOAN_TITLE)
        val title:String,*/

        @ColumnInfo(name = KOAN)
        val koan:String
)