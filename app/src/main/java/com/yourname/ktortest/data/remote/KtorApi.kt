package com.yourname.ktortest.data.remote

import com.yourname.ktortest.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KtorApi {
    @GET("/storage/languages")
    suspend fun getAllLanguages(
        @Query("page") page: Int = 1
    ): ApiResponse

    @GET("/storage/languages/search")
    suspend fun searchLanguages(
        @Query("name") name: String
    ): ApiResponse
}