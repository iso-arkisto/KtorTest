package com.yourname.ktortest.domain.usecase.search_languages

import androidx.paging.PagingData
import com.yourname.ktortest.data.repository.Repository
import com.yourname.ktortest.domain.model.ProgrammingLanguage
import kotlinx.coroutines.flow.Flow

class SearchLanguagesUseCase(
    val repository: Repository
) {
    operator fun invoke(query: String): Flow<PagingData<ProgrammingLanguage>> {
        return repository.searchLanguages(query)
    }
}