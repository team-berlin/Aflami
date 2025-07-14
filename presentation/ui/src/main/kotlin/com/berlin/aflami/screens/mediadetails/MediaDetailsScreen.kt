package com.berlin.aflami.screens.mediadetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.berlin.aflami.ui.theme.AflamiTheme

@Composable
fun MediaDetailsScreen() {

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