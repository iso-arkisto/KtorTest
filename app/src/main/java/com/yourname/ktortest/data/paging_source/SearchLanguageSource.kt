package com.yourname.ktortest.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yourname.ktortest.data.remote.KtorApi
import com.yourname.ktortest.domain.model.ProgrammingLanguage

class SearchLanguageSource(
    val ktorApi: KtorApi,
    val query: String,
    val cacheLanguages: suspend (List<ProgrammingLanguage>) -> Unit = {}
): PagingSource<Int, ProgrammingLanguage>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProgrammingLanguage> {
        return try {
            if(query.isBlank()) {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

            val response = ktorApi.searchLanguages(query)
            val languages = response.languages

            if(languages.isNotEmpty()) {
                cacheLanguages(languages)
            }

            LoadResult.Page(
                data = languages,
                prevKey = response.prevPage,
                nextKey = response.nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProgrammingLanguage>): Int? = state.anchorPosition
}