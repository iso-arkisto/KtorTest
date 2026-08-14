package com.yourname.ktortest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languageremotekey")
data class LanguageRemoteKey(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)