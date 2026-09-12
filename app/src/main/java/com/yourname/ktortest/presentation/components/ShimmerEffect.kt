package com.yourname.ktortest.presentation.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourname.ktortest.ui.theme.LARGE_CORNER
import com.yourname.ktortest.ui.theme.MEDIUM_PADDING
import com.yourname.ktortest.ui.theme.SMALL_CORNER
import com.yourname.ktortest.ui.theme.SMALL_PADDING
import com.yourname.ktortest.ui.theme.shimmerComponentItemColor
import com.yourname.ktortest.ui.theme.shimmerItemColor

@Composable
fun ShimmerEffect() {
    LazyColumn(
        contentPadding = PaddingValues(SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        items(2) {
            AnimatedShimmerItem()
        }
    }
}

@Composable
fun AnimatedShimmerItem() {
    val transition = rememberInfiniteTransition()
    val alphaAnim by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 900,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    ShimmerItem(alphaAnim)
}

@Composable
fun ShimmerItem(
    alpha: Float
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        color = shimmerItemColor(),
        shape = RoundedCornerShape(LARGE_CORNER)
    ) {
        Column(
            modifier = Modifier.padding(MEDIUM_PADDING),
            verticalArrangement = Arrangement.Bottom
        ) {
            Surface(
                modifier = Modifier
                    .alpha(alpha)
                    .fillMaxWidth(0.5f)
                    .height(30.dp),
                color = shimmerComponentItemColor(),
                shape = RoundedCornerShape(SMALL_CORNER)
            ) {}

            Spacer(modifier = Modifier.padding(SMALL_PADDING))

            repeat(3) {
                Surface(
                    modifier = Modifier
                        .alpha(alpha)
                        .fillMaxWidth()
                        .height(15.dp),
                    color = shimmerComponentItemColor(),
                    shape = RoundedCornerShape(SMALL_CORNER)
                ) { }

                Spacer(modifier = Modifier.padding(SMALL_PADDING))
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(5) {
                    Surface(
                        modifier = Modifier
                            .alpha(alpha)
                            .height(20.dp),
                        color = shimmerComponentItemColor(),
                        shape = RoundedCornerShape(SMALL_CORNER)
                    ) { }

                    Spacer(modifier = Modifier.padding(SMALL_PADDING))
                }
            }
        }
    }
}

@Composable
@Preview
fun AnimatedShimmerItemPreview() {
    ShimmerEffect()
}