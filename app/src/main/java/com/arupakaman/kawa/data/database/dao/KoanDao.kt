package com.arupakaman.kawa.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arupakaman.kawa.data.database.entities.Koan

@Dao
interface KoanDao {

    @Insert
    suspend fun insertAll(list:List<Koan>)

    @Query("SELECT * FROM koan")
    fun getAllKoans() : LiveData<List<Koan>>

    // indexing will not be used here, I need to check it later
    /*@Query("SELECT * FROM koan WHERE title LIKE '%'||:searchQuery||'%' OR koan LIKE '%'||:searchQuery||'%'")
    fun searchKoans(searchQuery:String) : LiveData<List<Koan>>*/

    @Query("SELECT * FROM koan JOIN koan_fts ON (koan.id=koan_fts.docId) WHERE koan_fts MATCH :searchQuery")
    fun searchKoansByFts(searchQuery: String) : LiveData<List<Koan>>

    @Query("SELECT * FROM koan WHERE title LIKE '%'||:searchQuery||'%'")
    fun searchKoansByTitle(searchQuery:String) : LiveData<List<Koan>>

    @Query("SELECT * FROM koan WHERE id=:koanId")
    fun getKoanByKoanId(koanId:Long):Koan

    @Query("SELECT * FROM Koan WHERE id=(SELECT min(id) FROM Koan)")
    fun getFirstKoan():Koan

    @Query("SELECT * FROM Koan ORDER BY RANDOM() LIMIT 1")
    fun getRandomKoan():Koan
}