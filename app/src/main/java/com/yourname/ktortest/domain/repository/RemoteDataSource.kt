package com.yourname.ktortest.domain.repository

import androidx.paging.PagingData
import com.yourname.ktortest.domain.model.ProgrammingLanguage
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllLanguages(): Flow<PagingData<ProgrammingLanguage>>
    fun searchLanguages(): Flow<PagingData<ProgrammingLanguage>>
}