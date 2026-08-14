package com.yourname.ktortest.presentation.screens.welcome.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.yourname.ktortest.ui.theme.PAGING_INDICATOR_SPACING
import com.yourname.ktortest.ui.theme.PAGING_INDICATOR_WIDTH

@Composable
fun HorizontalPagerIndicator(
    modifier: Modifier = Modifier,
    state: PagerState,
    pageCount: Int,
    activeColor: Color,
    inactiveColor: Color,
    indicatorWidth: Dp = PAGING_INDICATOR_WIDTH,
    spacing: Dp = PAGING_INDICATOR_SPACING
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val boxBackground = if(state.currentPage == index) activeColor else inactiveColor

            Box(
                modifier = Modifier
                    .size(indicatorWidth)
                    .clip(CircleShape)
                    .background(color = boxBackground)
            )
        }
    }
}