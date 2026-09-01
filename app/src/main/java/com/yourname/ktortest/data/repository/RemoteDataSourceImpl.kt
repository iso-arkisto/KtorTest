package com.yourname.ktortest.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.yourname.ktortest.data.local.LanguageDatabase
import com.yourname.ktortest.data.mappers.toDomain
import com.yourname.ktortest.data.paging_source.LanguageRemoteMediator
import com.yourname.ktortest.data.remote.KtorApi
import com.yourname.ktortest.domain.model.ProgrammingLanguage
import com.yourname.ktortest.domain.repository.RemoteDataSource
import com.yourname.ktortest.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun searchLanguages(): Flow<PagingData<ProgrammingLanguage>> {
        TODO("Not yet implemented")
    }
}