package com.yourname.ktortest.presentation.screens.welcome.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yourname.ktortest.ui.theme.EXTRA_LARGE_PADDING
import com.yourname.ktortest.ui.theme.buttonBackgroundColor
import com.yourname.ktortest.utils.Constants.LAST_ONBOARDING_PAGE

@Composable
fun FinishButton(
    modifier: Modifier = Modifier,
    state: PagerState,
    onClick: () -> Unit
) {

    val isVisible = LAST_ONBOARDING_PAGE == state.currentPage

    Row(
        modifier = modifier
            .padding(horizontal = EXTRA_LARGE_PADDING),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = isVisible
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBackgroundColor(),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Finish")
            }
        }
    }
}