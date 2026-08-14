package com.yourname.ktortest.domain.usecase.save_onboarding

import com.yourname.ktortest.data.repository.Repository

class SaveOnboardingUseCase(
    val repository: Repository
) {
    suspend operator fun invoke(isCompleted: Boolean) {
        repository.saveOnboardingState(isCompleted)
    }
}