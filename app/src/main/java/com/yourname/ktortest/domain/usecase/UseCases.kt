package com.yourname.ktortest.domain.usecase

import com.yourname.ktortest.domain.usecase.read_onboarding.ReadOnboardingUseCase
import com.yourname.ktortest.domain.usecase.save_onboarding.SaveOnboardingUseCase

data class UseCases(
    val readOnboardingUseCase: ReadOnboardingUseCase,
    val saveOnboardingUseCase: SaveOnboardingUseCase
)
