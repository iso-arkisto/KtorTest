package com.yourname.ktortest.domain.usecase.read_onboarding

import com.yourname.ktortest.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnboardingUseCase(
    val repository: Repository
) {
    operator fun invoke(): Flow<Boolean> = repository.readOnboardingState()
}