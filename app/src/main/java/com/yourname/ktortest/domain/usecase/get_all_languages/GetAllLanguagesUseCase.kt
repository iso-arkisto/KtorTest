package com.yourname.ktortest.domain.usecase.get_all_languages

import androidx.paging.PagingData
import com.yourname.ktortest.data.repository.Repository
import com.yourname.ktortest.domain.model.ProgrammingLanguage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllLanguagesUseCase @Inject constructor(
    val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<ProgrammingLanguage>> {
        return repository.getAllLanguages()
    }
}