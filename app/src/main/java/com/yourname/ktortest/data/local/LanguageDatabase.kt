package com.yourname.ktortest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yourname.ktortest.data.dao.LanguageDao
import com.yourname.ktortest.data.dao.LanguageRemoteKeyDao

@Database(
    version = 1,
    entities = [LanguageEntity::class],
    exportSchema = true
)
@TypeConverters(DatabaseConverter::class)
abstract class LanguageDatabase : RoomDatabase() {
    abstract fun LanguageDao(): LanguageDao
    abstract fun LanguageRemoteKeyDao(): LanguageRemoteKeyDao
}