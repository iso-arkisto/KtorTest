package com.yourname.ktortest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "programming_languages")
@Serializable
data class ProgrammingLanguage(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val shortName: String,
    val image: String,
    val creator: String,
    val inceptionYear: Int,
    val name: String
)