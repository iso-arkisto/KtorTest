package com.yourname.ktortest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yourname.ktortest.domain.model.LanguageRemoteKey

@Dao
interface LanguageRemoteKeyDao {
    @Query("SELECT * FROM languageremotekey WHERE id = :id")
    suspend fun getRemoteKeys(id: Int): LanguageRemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(items: List<LanguageRemoteKey>)

    @Query("DELETE FROM languageremotekey")
    suspend fun deleteAllRemoteKeys()
}