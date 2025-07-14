package com.berlin.aflami.screens.search.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme

@Composable
fun ErrorMessage(modifier: Modifier = Modifier, error: String) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = error,
            color = Theme.color.statusColors.redAccent,
            textAlign = TextAlign.Center,
            style = Theme.textStyle.title.small
        )
    }
}

@Preview
@Composable
fun ErrorMessagePreview() {
    AflamiTheme {
        ErrorMessage(Modifier,"No internet connection")
    }
}