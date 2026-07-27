package com.yourname.ktortest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LanguageRemoteKey(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)