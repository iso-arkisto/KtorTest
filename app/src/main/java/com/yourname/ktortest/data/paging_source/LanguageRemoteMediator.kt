package com.yourname.ktortest.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.yourname.ktortest.data.local.LanguageDatabase
import com.yourname.ktortest.data.local.LanguageEntity
import com.yourname.ktortest.data.remote.KtorApi
import com.yourname.ktortest.domain.model.LanguageRemoteKey
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class LanguageRemoteMediator @Inject constructor(
    val ktorApi: KtorApi,
    val database: LanguageDatabase
): RemoteMediator<Int, LanguageEntity>() {
    private val languageDao = database.languageDao()
    private val languageRemoteKeyDao = database.languageRemoteKeyDao()

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LanguageEntity>
    ): LanguageRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                languageRemoteKeyDao.getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, LanguageEntity>
    ): LanguageRemoteKey? {
        return state.pages.firstOrNull { page ->
            page.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { item ->
            languageRemoteKeyDao.getRemoteKeys(item.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, LanguageEntity>
    ): LanguageRemoteKey? {
        return state.pages.lastOrNull() { page ->
            page.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { item ->
            languageRemoteKeyDao.getRemoteKeys(item.id)
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LanguageEntity>
    ): MediatorResult {
        return try {
            val page = when(loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKey?.prevPage ?: return MediatorResult.Success(remoteKey != null)
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKey?.nextPage ?: return MediatorResult.Success(remoteKey != null)
                    nextPage
                }
            }

            val response = ktorApi.getAllLanguages(page)

            if(response.languages.isNotEmpty()) {
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        languageDao.deleteAllLanguages()
                        languageRemoteKeyDao.deleteAllRemoteKeys()
                    }

                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys = response.languages.map { language ->
                        LanguageRemoteKey(
                            id = language.id,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }

                    languageRemoteKeyDao.addAllRemoteKeys(keys)
                    languageDao.addLanguages(response.languages.map { language ->
                        language.toEntity()
                    })

                } // either all operations are executed, or none are
            }

            MediatorResult.Success(response.nextPage == null)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}