package com.yourname.ktortest.domain.repository

import androidx.paging.PagingData
import com.yourname.ktortest.domain.model.CreateLanguageRequest
import com.yourname.ktortest.domain.model.ProgrammingLanguage
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllLanguages(): Flow<PagingData<ProgrammingLanguage>>

    fun searchLanguages(query: String): Flow<PagingData<ProgrammingLanguage>>

    suspend fun createLanguage(
        request: CreateLanguageRequest,
        imageBytes: ByteArray,
        fileExtension: String
    ): ProgrammingLanguage
}