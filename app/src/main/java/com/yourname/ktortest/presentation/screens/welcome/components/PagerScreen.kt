package com.yourname.ktortest.presentation.screens.welcome.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.yourname.ktortest.domain.model.OnboardingPage
import com.yourname.ktortest.ui.theme.EXTRA_LARGE_PADDING
import com.yourname.ktortest.ui.theme.SMALL_PADDING
import com.yourname.ktortest.ui.theme.descriptionColor
import com.yourname.ktortest.ui.theme.titleColor
import com.yourname.ktortest.ui.theme.welcomeScreenBackgroundColor

@Composable
fun PagerScreen(
    currentPage: OnboardingPage
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(welcomeScreenBackgroundColor()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.7f),
            painter = painterResource(currentPage.image),
            contentDescription = null
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = currentPage.title,
            color = titleColor(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = EXTRA_LARGE_PADDING)
                .padding(top = SMALL_PADDING),
            text = currentPage.description,
            color = descriptionColor(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
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
fun PreviewPagerScreen() {
    PagerScreen(
        OnboardingPage.First
    )
}