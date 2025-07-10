package com.berlin.aflami.screens.search.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.SearchSuggestionHub
import com.berlin.aflami.component.TextField
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun SearchScreen() {
    SearchScreenContent()
}

@Composable
private fun SearchScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface),
    ) {
        TopBar(
            modifier = Modifier.padding(vertical = 8.dp),
            title = {
                Text(
                    text = stringResource(R.string.search),
                    style = Theme.textStyle.title.large,
                    color = Theme.color.textColors.title
                )
            }, leadingIcon = {
                Box(
                    Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Theme.color.surfaceHigh),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = stringResource(R.string.icon_cd),
                        tint = Theme.color.textColors.title
                    )
                }
            }
        )

        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            TextField(
                text = "search...",
                hintText = stringResource(R.string.search_hint_text),
                trailingIcon = R.drawable.filter_vertical,
            )
        }

        Text(
            stringResource(R.string.search_suggestions_hub),
            color = Theme.color.textColors.title,
            style = Theme.textStyle.title.medium,
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp, start = 16.dp)
        )

        SearchSuggestionHub(
            Modifier.padding(horizontal = 16.dp),
            onWorldTourClick = {},
            onFindActorClick = {}
        )

        // NoDataSearch()

        SearchData("Fauda")

    }
}

@Composable
fun SearchData(text: String) {
    LazyColumn() {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(R.string.recent_searches),
                    color = Theme.color.textColors.title,
                    style = Theme.textStyle.title.medium,
                    modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
                )
                Text(
                    stringResource(R.string.clear_all),
                    color = Theme.color.primary,
                    style = Theme.textStyle.label.medium,
                    modifier = Modifier.padding(top = 8.dp, bottom = 12.dp, start = 16.dp)
                )
            }

            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(com.berlin.ui.R.drawable.clock),
                    contentDescription = "Clock"
                )

                Text(text, modifier = Modifier.padding(start = 8.dp))

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(com.berlin.ui.R.drawable.cancel),
                    contentDescription = "cancel"
                )
            }

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Theme.color.stroke
            )
        }
    }
}

@Composable
private fun NoDataSearch() {
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
private fun SearchScreenPreview() {
    AflamiTheme {
        SearchScreenContent()
    }
}