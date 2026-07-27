package com.yourname.ktortest.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yourname.ktortest.data.local.LanguageEntity

@Dao
interface LanguageDao {
    @Query("SELECT * FROM languages ORDER BY id ASC")
    fun getAllLanguages(): PagingSource<Int, LanguageEntity>

    @Query("SELECT * FROM languages WHERE id = :id")
    fun getLanguageById(id: Int): LanguageEntity

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addLanguage(item: LanguageEntity)

    @Query("DELETE FROM languages")
    suspend fun deleteAllLanguages()
}