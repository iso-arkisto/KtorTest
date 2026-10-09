package com.yourname.ktortest.presentation.screens.home

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.yourname.ktortest.R
import com.yourname.ktortest.ui.theme.DarkGray
import com.yourname.ktortest.ui.theme.LightGray
import com.yourname.ktortest.ui.theme.SMALL_PADDING
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun ErrorScreen(error: LoadState.Error) {
    val message by remember { mutableStateOf(parseErrorMessage(error)) }
    val icon by remember { mutableIntStateOf(R.drawable.ic_network_error) }
    var startAnimation by remember { mutableStateOf(false) }

    val alphaAnim by animateFloatAsState(
        targetValue = if(startAnimation) ContentAlpha.disabled else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    LaunchedEffect(true) {
        startAnimation = true
    }

    ErrorContent(alphaAnim, icon, message)
}

@Composable
fun ErrorContent(
    alphaAnim: Float,
    icon: Int,
    message: String
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .then(if(isDarkTheme()) Modifier.background(Color.Black) else Modifier.background(Color.White)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .size(120.dp)
                .alpha(alphaAnim),
            painter = painterResource(icon),
            contentDescription = null,
            tint = if(isDarkTheme()) LightGray else DarkGray
        )

        Text(
            modifier = Modifier
                .padding(top = SMALL_PADDING)
                .alpha(alphaAnim),
            text = message,
            color = if(isDarkTheme()) LightGray else DarkGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Theme"
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Theme"
)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        error = LoadState.Error(ConnectException())
    )
}

@Composable
fun isDarkTheme(): Boolean {
    return (LocalConfiguration.current.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
}

private fun parseErrorMessage(error: LoadState.Error): String {
    return when(error.error) {
        is SocketTimeoutException -> { "Server is unavailable" }
        is ConnectException -> { "No internet connection" }
        else -> { "Unknown error" }
    }
}