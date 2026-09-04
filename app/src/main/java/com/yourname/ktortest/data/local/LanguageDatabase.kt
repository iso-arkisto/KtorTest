package com.yourname.ktortest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yourname.ktortest.data.dao.LanguageDao
import com.yourname.ktortest.data.dao.LanguageRemoteKeyDao
import com.yourname.ktortest.domain.model.LanguageRemoteKey

@Database(
    version = 1,
    entities = [LanguageEntity::class, LanguageRemoteKey::class],
    exportSchema = true
)
@TypeConverters(DatabaseConverter::class)
abstract class LanguageDatabase : RoomDatabase() {
    abstract fun languageDao(): LanguageDao
    abstract fun languageRemoteKeyDao(): LanguageRemoteKeyDao
}