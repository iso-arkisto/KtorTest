package com.yourname.ktortest.data.local

import androidx.room.Entity

@Entity(tableName = "languages")
data class LanguageEntity(
    val id: Int,
    val shortName: String,
    val image: String,
    val creator: String,
    val inceptionYear: Int,
    val name: String
)