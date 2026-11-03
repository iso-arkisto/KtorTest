package com.yourname.ktortest.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.yourname.ktortest.data.local.LanguageDatabase
import com.yourname.ktortest.data.mappers.toDomain
import com.yourname.ktortest.data.mappers.toEntity
import com.yourname.ktortest.data.paging_source.LanguageRemoteMediator
import com.yourname.ktortest.data.paging_source.SearchLanguageSource
import com.yourname.ktortest.data.remote.KtorApi
import com.yourname.ktortest.domain.model.CreateLanguageRequest
import com.yourname.ktortest.domain.model.ProgrammingLanguage
import com.yourname.ktortest.domain.repository.RemoteDataSource
import com.yourname.ktortest.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val ktorApi: KtorApi,
    private val database: LanguageDatabase
) : RemoteDataSource {

    private val languageDao = database.languageDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllLanguages(): Flow<PagingData<ProgrammingLanguage>> {
        val pagingSourceMemory = {
            languageDao.getAllLanguages() // paging source cannot update when the database changes
        }

        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            remoteMediator = LanguageRemoteMediator(ktorApi, database),
            pagingSourceFactory = pagingSourceMemory
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                entity.toDomain()
            }
        }
    }

    override fun searchLanguages(
        query: String
    ): Flow<PagingData<ProgrammingLanguage>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchLanguageSource(
                    ktorApi = ktorApi,
                    query = query,
                    cacheLanguages = { languageDao.addLanguages(it.map { item -> item.toEntity() }) }
                )
            }
        ).flow
    }

    override suspend fun createLanguage(
        request: CreateLanguageRequest,
        imageBytes: ByteArray,
        fileExtension: String
    ): ProgrammingLanguage {
        val json = Json.encodeToString(request)

        val dataPart = MultipartBody.Part.createFormData(
            name = "data",
            filename = null,
            body = json.toRequestBody("application/json".toMediaType())
        )

        val imageBody = imageBytes.toRequestBody(
            contentType = "image/*".toMediaType(),
        )

        val imagePart = MultipartBody.Part.createFormData(
            name = "image",
            filename = fileExtension,
            body = imageBody
        )

        val response = ktorApi.createLanguage(
            data = dataPart,
            image = imagePart
        )

        if(!response.success || response.languages.isEmpty()) {
            throw IllegalStateException(response.message ?: "Failed to create language")
        }

        val language = response.languages.firstOrNull()

        if(language != null) {
            database.withTransaction {
                languageDao.addLanguages(items = listOf(language.toEntity()))
            }

            return language
        } else {
            throw IllegalStateException(response.message ?: "Failed to create language")
        }
    }
}