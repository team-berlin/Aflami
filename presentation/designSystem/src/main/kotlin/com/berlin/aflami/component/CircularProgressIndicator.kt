package com.berlin.aflami.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme

@Composable
fun CircularProgressIndicator(modifier: Modifier = Modifier, text: String? = null) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            size = 28.dp,
            lineLength = 6.dp,
            lineWidth = 4.dp,
            color = Theme.color.primary
        )
        text?.let {
            Text(
                modifier = Modifier.padding(top = 14.dp),
                text = it,
                style = Theme.textStyle.label.medium,
                color = Theme.color.textColors.body
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CircularProgressIndicatorPreview() {
    AflamiTheme {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                text = "Loading..."
            )
        }
    }
}