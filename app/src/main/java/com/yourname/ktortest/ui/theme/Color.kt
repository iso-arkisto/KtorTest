package com.yourname.ktortest.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val LightGray = Color(0xFFD8D8D8)
val DarkGray = Color(0xFF2A2A2A)

@Composable
fun welcomeScreenBackgroundColor(): Color {
    return if(isSystemInDarkTheme()) Color.Black else Color.White
}

@Composable
fun titleColor(): Color {
    return if(isSystemInDarkTheme()) LightGray else DarkGray
}

@Composable
fun descriptionColor(): Color {
    return if(isSystemInDarkTheme()) LightGray.copy(alpha = 0.5f) else DarkGray.copy(alpha = 0.5f)
}

@Composable
fun activeIndicatorColor(): Color {
    return if(isSystemInDarkTheme()) Purple40 else Purple80
}

@Composable
fun inactiveIndicatorColor(): Color {
    return if(isSystemInDarkTheme()) LightGray.copy(alpha = 0.3f) else DarkGray.copy(alpha = 0.3f)
}

@Composable
fun buttonBackgroundColor(): Color {
    return if(isSystemInDarkTheme()) Purple40 else Purple80
}