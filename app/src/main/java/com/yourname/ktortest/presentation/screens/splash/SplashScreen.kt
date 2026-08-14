package com.yourname.ktortest.presentation.screens.splash

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yourname.ktortest.R
import com.yourname.ktortest.navigation.Screen
import com.yourname.ktortest.ui.theme.Pink40
import com.yourname.ktortest.ui.theme.Purple40
import com.yourname.ktortest.ui.theme.Purple80
import com.yourname.ktortest.ui.theme.PurpleGrey40
import com.yourname.ktortest.utils.Constants.SPLASH_DELAY
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val onboardingCompleted by viewModel.onboardingCompleted.collectAsState()

    Splash()

    LaunchedEffect(Unit) {
        delay(SPLASH_DELAY)
        navController.popBackStack()
        if(onboardingCompleted) {
            navController.navigate(Screen.Home.route)
        } else {
            navController.navigate(Screen.Welcome.route)
        }
    }
}

@Composable
fun Splash() {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val logoAlpha by animateFloatAsState(
        targetValue = if(isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 200),
        label = "LogoAlpha"
    )

    var boxModifier = Modifier
        .fillMaxSize()

    boxModifier = if(isSystemInDarkTheme()) {
        boxModifier.background(Brush.verticalGradient(listOf(PurpleGrey40, Pink40)))
    } else {
        boxModifier.background(Brush.verticalGradient(listOf(Purple40, Purple80)))
    }

    Box(
        modifier = boxModifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.splash_image),
            contentDescription = "Logo",
            modifier = Modifier.alpha(logoAlpha)
        )
    }
}

@Composable
@Preview(
    name = "Light Theme",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Theme",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun SplashPreview() {
    Splash()
}