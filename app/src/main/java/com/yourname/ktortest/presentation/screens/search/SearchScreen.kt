package com.yourname.ktortest.presentation.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.yourname.ktortest.presentation.screens.home.ListContent
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val searchQuery by viewModel.searchQuery
    val hasSearched by viewModel.hasSearched
    val searchResult = viewModel.searchLanguages.collectAsLazyPagingItems()

    LaunchedEffect(searchQuery) {
        if(searchQuery.isBlank()) return@LaunchedEffect

        delay(500)

        viewModel.searchLanguages(searchQuery)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            SearchTopBar(
                onCloseClick = { navHostController.popBackStack() },
                onTextChange = { viewModel.updateSearchQuery(it) },
                onSearchClick = { viewModel.searchLanguages(it) },
                text = searchQuery
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if(hasSearched) {
                ListContent(
                    navController = navHostController,
                    languages = searchResult
                )
            }
        }
    }
}