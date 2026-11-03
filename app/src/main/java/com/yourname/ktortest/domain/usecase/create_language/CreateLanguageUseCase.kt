package com.yourname.ktortest.domain.usecase.create_language

import com.yourname.ktortest.data.repository.Repository
import com.yourname.ktortest.domain.model.CreateLanguageRequest
import com.yourname.ktortest.domain.model.ProgrammingLanguage
import javax.inject.Inject

class CreateLanguageUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(
        request: CreateLanguageRequest,
        imageBytes: ByteArray,
        fileExtension: String
    ): ProgrammingLanguage {
        return repository.createLanguage(request, imageBytes, fileExtension)
    }
}