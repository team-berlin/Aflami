package com.berlin.aflami.screens.search.mediadetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.berlin.aflami.ui.theme.AflamiTheme

@Composable
fun MediaDetailsScreen(navController : NavController) {
    MediaDetailsContent()
}

@Composable
fun MediaDetailsContent() {
    Text("Hello Media!")
}

@Preview
@Composable
fun MediaDetailsContentPreview() {
    AflamiTheme {
        MediaDetailsContent()
    }
}