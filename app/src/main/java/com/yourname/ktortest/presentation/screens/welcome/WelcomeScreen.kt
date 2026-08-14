package com.yourname.ktortest.presentation.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yourname.ktortest.domain.model.OnboardingPage
import com.yourname.ktortest.navigation.Screen
import com.yourname.ktortest.presentation.screens.welcome.components.FinishButton
import com.yourname.ktortest.presentation.screens.welcome.components.HorizontalPagerIndicator
import com.yourname.ktortest.presentation.screens.welcome.components.PagerScreen
import com.yourname.ktortest.ui.theme.SMALL_PADDING
import com.yourname.ktortest.ui.theme.activeIndicatorColor
import com.yourname.ktortest.ui.theme.inactiveIndicatorColor
import com.yourname.ktortest.ui.theme.welcomeScreenBackgroundColor
import com.yourname.ktortest.utils.Constants.ONBOARDING_PAGE_COUNT

@Composable
fun WelcomeScreen(
    navController: NavHostController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    val pages = listOf<OnboardingPage>(
        OnboardingPage.First,
        OnboardingPage.Second,
        OnboardingPage.Third
    )

    val pageState = rememberPagerState(pageCount = { ONBOARDING_PAGE_COUNT })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(welcomeScreenBackgroundColor())
    ) {
        HorizontalPager(
            modifier = Modifier
                .weight(10f),
            state = pageState,
            verticalAlignment = Alignment.CenterVertically
        ) { position -> // index
            PagerScreen(pages[position])
        }

        HorizontalPagerIndicator(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = SMALL_PADDING),
            state = pageState,
            pageCount = ONBOARDING_PAGE_COUNT,
            activeColor = activeIndicatorColor(),
            inactiveColor = inactiveIndicatorColor()
        )

        FinishButton(
            modifier = Modifier.weight(1f),
            state = pageState,
            onClick = {
                viewModel.saveOnboardingState(true)
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        )
    }
}