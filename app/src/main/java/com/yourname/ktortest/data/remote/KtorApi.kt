package com.yourname.ktortest.data.remote

import com.yourname.ktortest.domain.model.ApiResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @Multipart
    @POST
    suspend fun createLanguage(
        @Part data: MultipartBody.Part,
        @Part image: MultipartBody.Part
    ): ApiResponse
}