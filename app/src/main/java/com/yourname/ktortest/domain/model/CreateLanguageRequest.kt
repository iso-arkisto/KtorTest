package com.yourname.ktortest.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateLanguageRequest(
    val shortName: String,
    val creator: String,
    val inceptionYear: Int,
    val name: String,
    val rating: Double,
    val description: String
)