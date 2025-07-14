package com.berlin.aflami.screens.mediadetails.screen

import android.widget.HorizontalScrollView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.berlin.aflami.screens.mediadetails.components.MediaCast
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme

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