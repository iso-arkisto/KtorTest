package com.yourname.ktortest.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.ktortest.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    val useCases: UseCases
): ViewModel() {
    fun saveOnboardingState(isCompleted: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.saveOnboardingUseCase(isCompleted)
        }
    }
}