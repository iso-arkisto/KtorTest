package com.yourname.ktortest.presentation.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourname.ktortest.R
import com.yourname.ktortest.ui.theme.EXTRA_SMALL_PADDING
import com.yourname.ktortest.ui.theme.LightGray
import com.yourname.ktortest.ui.theme.StarColor
import com.yourname.ktortest.utils.Constants.MAX_STARS

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 3f,
    spaceBetween: Dp = EXTRA_SMALL_PADDING
) {
    val result = calculateStars(rating)
    val starPathString = stringResource(R.string.star_path)

    val starPath = remember {
        PathParser().parsePathString(starPathString).toPath()
    }

    val starPathBounds = remember {
        starPath.getBounds()
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        result["filledStars"]?.let { amount ->
            repeat(amount) {
                FilledStar(
                    starPath,
                    starPathBounds,
                    scaleFactor
                )
            }
        }

        result["halfFilledStars"]?.let { amount ->
            repeat(amount) {
                HalfFilledStar(
                    starPath,
                    starPathBounds,
                    scaleFactor
                )
            }
        }

        result["emptyStars"]?.let { amount ->
            repeat(amount) {
                EmptyStar(
                    starPath,
                    starPathBounds,
                    scaleFactor
                )
            }
        }
    }
}

@Composable
fun FilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(
        modifier = Modifier
            .size(24.dp)
    ) {
        val canvasSize = size

        scale(scale = scaleFactor) {
            val pathWidth = starPathBounds.width
            val pathLeft = (canvasSize.width / 2) - (pathWidth / 1.7f)
            val pathHeight = starPathBounds.height
            val pathTop = (canvasSize.height / 2) - (pathHeight / 1.7f)

            translate(
                left = pathLeft,
                top = pathTop
            ) { // moves the origin of coordinates
                drawPath(
                    path = starPath,
                    color = StarColor
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FilledStarPreview() {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    FilledStar(
        starPath = starPath,
        starPathBounds = starPathBounds,
        scaleFactor = 3f
    )
}

@Composable
fun HalfFilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
){
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        scale(scale = scaleFactor){
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (canvasSize.width / 2) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2) - (pathHeight / 1.7f)

            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = 0.5f)
                )
                clipPath(path = starPath){
                    drawRect(
                        color = StarColor,
                        size = Size(
                            width = starPathBounds.maxDimension / 1.7f,
                            height = starPathBounds.maxDimension * scaleFactor
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
){
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        scale(scale = scaleFactor){
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (canvasSize.width / 2) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2) - (pathHeight / 1.7f)

            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = 0.5f)
                )
            }
        }

    }
}

@Composable
@Preview(showBackground = true)
fun HalfFilledStarPreview(){
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    HalfFilledStar(
        starPath = starPath,
        starPathBounds = starPathBounds,
        scaleFactor = 3f
    )
}

@Composable
@Preview(showBackground = true)
fun EmptyStarPreview(){
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    EmptyStar(
        starPath = starPath,
        starPathBounds = starPathBounds,
        scaleFactor = 3f
    )
}



@Composable
fun calculateStars(rating: Double): Map<String, Int>{
    var filledStars by remember { mutableStateOf(value = 0) }
    var halfFilledStars by remember { mutableStateOf(value = 0) }
    var emptyStars by remember { mutableStateOf(value = 0) }

    LaunchedEffect(key1 = rating) {
        val (firstNumber, lastNumber) = rating.toString()
            .split(".")
            .map { it.toInt() }
        if ((firstNumber in 0..MAX_STARS && lastNumber in 0..9)){
            filledStars = firstNumber
            if (lastNumber in 1..MAX_STARS){
                halfFilledStars++
            }
            if (lastNumber in 6..9){
                filledStars++
            }
            if (firstNumber == MAX_STARS && lastNumber > 0){
                emptyStars = MAX_STARS
                filledStars = 0
                halfFilledStars = 0
            }
        } else {
            Log.d("RatingWidget", "Invalid rating number.")
        }
    }

    emptyStars = MAX_STARS - (filledStars + halfFilledStars)
    return mapOf(
        "filledStars" to filledStars,
        "halfFilledStars" to halfFilledStars,
        "emptyStars" to emptyStars
    )
}