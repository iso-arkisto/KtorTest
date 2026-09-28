package com.yourname.ktortest.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.yourname.ktortest.navigation.Screen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val languages = viewModel.getAllLanguages().collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            HomeTopBar(
                onSearchClicked = { navController.navigate(Screen.Search.route) }
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                ListContent(
                    languages = languages,
                    navController = navController
                )
            }
        }
    )
}