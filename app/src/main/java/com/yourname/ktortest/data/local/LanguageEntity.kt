package com.yourname.ktortest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
data class LanguageEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val shortName: String,
    val image: String,
    val creator: String,
    val inceptionYear: Int,
    val name: String
)