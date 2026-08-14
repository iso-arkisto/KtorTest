package com.yourname.ktortest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yourname.ktortest.presentation.screens.splash.SplashScreen
import com.yourname.ktortest.presentation.screens.welcome.WelcomeScreen
import com.yourname.ktortest.utils.Constants

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navHostController)
        }
        composable(Screen.Welcome.route) {
            WelcomeScreen(navHostController)
        }
        composable(Screen.Home.route) {

        }
        composable(
            Screen.Details.route,
            arguments = listOf(navArgument(Constants.DETAILS_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {

        }
        composable(Screen.Search.route) {

        }
    }
}