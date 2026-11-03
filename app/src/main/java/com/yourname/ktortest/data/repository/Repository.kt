package com.yourname.ktortest.data.repository

import androidx.paging.PagingData
import com.yourname.ktortest.domain.model.CreateLanguageRequest
import com.yourname.ktortest.domain.model.ProgrammingLanguage
import com.yourname.ktortest.domain.repository.DatastoreOperations
import com.yourname.ktortest.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    val datastoreOperations: DatastoreOperations,
    val remoteDataSource: RemoteDataSource
) {
    suspend fun saveOnboardingState(isCompleted: Boolean) {
        datastoreOperations.saveOnboardingState(isCompleted)
    }

    fun readOnboardingState(): Flow<Boolean> {
        return datastoreOperations.readOnboardingState()
    }

    fun getAllLanguages(): Flow<PagingData<ProgrammingLanguage>> {
        return remoteDataSource.getAllLanguages()
    }

    fun searchLanguages(query: String): Flow<PagingData<ProgrammingLanguage>> {
        return remoteDataSource.searchLanguages(query)
    }

    suspend fun createLanguage(
        request: CreateLanguageRequest,
        imageBytes: ByteArray,
        fileExtension: String
    ): ProgrammingLanguage {
        return remoteDataSource.createLanguage(request, imageBytes, fileExtension)
    }
}