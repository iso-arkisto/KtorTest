package com.yourname.ktortest.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.wear.compose.material.ContentAlpha
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.yourname.ktortest.R
import com.yourname.ktortest.domain.model.ProgrammingLanguage
import com.yourname.ktortest.navigation.Screen
import com.yourname.ktortest.presentation.components.RatingWidget
import com.yourname.ktortest.presentation.components.ShimmerEffect
import com.yourname.ktortest.ui.theme.LARGE_PADDING
import com.yourname.ktortest.ui.theme.MEDIUM_PADDING
import com.yourname.ktortest.ui.theme.SMALL_PADDING
import com.yourname.ktortest.ui.theme.itemContentColor
import com.yourname.ktortest.utils.Constants.BASE_URL
import kotlinx.coroutines.delay

@Composable
fun ListContent(
    navController: NavHostController,
    languages: LazyPagingItems<ProgrammingLanguage>
) {
    val result = handlePagingResult(languages)

    if(result) {
        LazyColumn(
            contentPadding = PaddingValues(SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(languages.itemCount, key = { index ->
                languages[index]?.id ?: index
            }) { index ->

                val language = languages[index]

                language?.let { item ->
                    LanguageItem(
                        item = item,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(
    languages: LazyPagingItems<ProgrammingLanguage>
): Boolean {
    var minShimmerElapsed by remember { mutableStateOf(true) }

    LaunchedEffect(languages) {
        snapshotFlow { // turns compose state into flow
            languages.loadState.refresh
        }.collect { state ->
            if(state is LoadState.Loading) {
                minShimmerElapsed = false
                delay(10_000L)
                minShimmerElapsed = true
            }
        }
    }

    val isLoading = languages.loadState.refresh is LoadState.Loading
    val error = when {
        languages.loadState.refresh is LoadState.Error -> {
            languages.loadState.refresh as LoadState.Error
        }
        languages.loadState.append is LoadState.Error -> {
            languages.loadState.append as LoadState.Error
        }
        languages.loadState.prepend is LoadState.Error -> {
            languages.loadState.prepend as LoadState.Error
        }
        else -> null
    }

    val showShimmer = (isLoading || error!=null) && !minShimmerElapsed

    return when {
        showShimmer -> {
            ShimmerEffect()
            false
        }
        isLoading && minShimmerElapsed -> false
        error != null -> {
            ErrorScreen(error)
            false
        }
        else -> true
    }
}

@Composable
fun LanguageItem(
    item: ProgrammingLanguage,
    navController: NavHostController
) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(context = context)
            .data("$BASE_URL${item.image}")
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .build()
    )

    Box(
        modifier = Modifier
            .height(400.dp)
            .clickable {
                navController.navigate(Screen.Details.passId(item.id))
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(20.dp)) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MEDIUM_PADDING)
            ) {
                Text(
                    text = item.name,
                    color = itemContentColor(),
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = item.description,
                    color = Color.Gray,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier
                        .padding(SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(
                        rating = item.rating,
                        modifier = Modifier.padding(SMALL_PADDING)
                    )

                    Text(
                        text = "(${item.rating})",
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )

                    Text(
                        text = item.inceptionYear.toString(),
                        textAlign = TextAlign.Right,
                        color = Color.Gray,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }

}

@Composable
@Preview
fun LanguageItemPreview() {
    LanguageItem(
        item = ProgrammingLanguage(
            id = 1,
            name = "Nikita",
            image = "",
            description = "Some text...",
            rating = 4.1,
            shortName = "rs",
            inceptionYear = 1986,
            creator = "Nikita"
        ),
        navController = rememberNavController()
    )
}