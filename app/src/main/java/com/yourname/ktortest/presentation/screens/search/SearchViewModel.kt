package com.yourname.ktortest.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yourname.ktortest.domain.model.ProgrammingLanguage
import com.yourname.ktortest.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchLanguages = MutableStateFlow<PagingData<ProgrammingLanguage>>(PagingData.empty())
    val searchLanguages = _searchLanguages.asStateFlow()

    private val _hasSearched = mutableStateOf(false)
    val hasSearched = _hasSearched

    private var searchJob: Job? = null

    fun updateSearchQuery(
        query: String
    ) {
        if(query.isBlank()) {
            searchJob?.cancel()
            _hasSearched.value = false
            _searchLanguages.value = PagingData.empty()
        }

        _searchQuery.value = query
    }

    fun searchLanguages(
        query: String
    ) {
        val trimmedQuery = query.trim()
        _searchQuery.value = trimmedQuery

        if(trimmedQuery.isBlank()) {
            updateSearchQuery("")
            return
        }

        _hasSearched.value = true
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            useCases.searchLanguagesUseCase(trimmedQuery).cachedIn(viewModelScope).collect { languages ->
                _searchLanguages.value = languages
            }
        }
    }
}