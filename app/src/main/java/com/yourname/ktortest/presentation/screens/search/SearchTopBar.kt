package com.yourname.ktortest.presentation.screens.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourname.ktortest.ui.theme.KtorTestTheme
import com.yourname.ktortest.ui.theme.topAppBarBackgroundColor
import com.yourname.ktortest.ui.theme.topAppBarContentColor

@Composable
fun SearchTopBar(
    onCloseClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    text: String,
    onTextChange: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shadowElevation = 6.dp,
            color = topAppBarBackgroundColor()
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { onTextChange(it) },
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(0.6f),
                        text = "Search...",
                        color = Color.White
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        onClick = { onSearchClick(text) },
                        modifier = Modifier.alpha(0.6f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = topAppBarContentColor()
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if(text.isNotBlank()) {
                                onTextChange("")
                            } else {
                                onCloseClick()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = topAppBarContentColor()
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchClick(text) }
                )
            )
        }
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
fun SearchTopBarDarkPreview() {
    KtorTestTheme {
        SearchTopBar(
            onSearchClick = {},
            onCloseClick = {},
            onTextChange = {},
            text = ""
        )
    }
}