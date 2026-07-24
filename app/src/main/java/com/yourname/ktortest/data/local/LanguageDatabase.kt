package com.yourname.ktortest.data.local

import androidx.room.Database
import com.yourname.ktortest.data.dao.LanguageDao

@Database(
    version = 1,
    entities = [LanguageEntity::class],
    exportSchema = true
)
abstract class LanguageDatabase {
    abstract fun LanguageDao(): LanguageDao
}