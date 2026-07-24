package com.yourname.ktortest.data.local

import androidx.room.Database

@Database(
    version = 1,
    entities = [LanguageEntity::class],
    exportSchema = true
)
abstract class LanguageDatabase {
    abstract fun LanguageDao(): LanguageDao
}