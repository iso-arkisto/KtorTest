package com.yourname.ktortest.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.yourname.ktortest.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    val getAllLanguages = useCases.getAllLanguagesUseCase
}