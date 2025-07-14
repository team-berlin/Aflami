package com.berlin.aflami.screens.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme

@Composable
fun NoDataSearch() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(com.berlin.ui.R.drawable.no_search_result),
                    contentDescription = "Clock"
                )
                Text(
                    "Start exploring! Search for your favorite movies, series and shows",
                    textAlign = TextAlign.Center,
                    color = Theme.color.textColors.body,
                    style = Theme.textStyle.body.small,
                )
            }

        }
    }
}


@Preview
@Composable
fun NoDataSearchPreview() {
    NoDataSearch()
}