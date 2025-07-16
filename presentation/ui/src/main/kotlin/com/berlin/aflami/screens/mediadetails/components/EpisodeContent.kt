package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EpisodeScreen(
    seasonNumber: String,
    seasonTitle: String,
    episodes: EpisodeUi,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        SeasonsHeader(
            seasonNumber = seasonNumber,
            episodesNumber = seasonTitle,
            isExpanded = isExpanded,
            onToggleExpand = onToggleExpand
        )
        AnimatedVisibility(visible = isExpanded) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    EpisodeCard(
                        episode = episodes,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClickPlay = {}
                    )
                }
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview(){
    EpisodeScreen(
        seasonNumber = "1",
        seasonTitle = "ddf",
        isExpanded = true,
        episodes = EpisodeUi(
            id = 0,
            episodeNumber = 1,
            title = "Recovering a body",
            description = "In 1935, corrections officer Paul Edge comb oversees sssssssssssssssssssssssssssssssssssssaekjflfldflfjl;edjjjjjjjjjjjjjjjjjjjjjjjjjjjjsssssssssssssssssss",
            imageUrl = "",
            time = "58 m",
            date = "3 Sep 2020",
            rating = "8.2",

        ),
        onToggleExpand = {}
    )

}