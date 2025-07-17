package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.uistate.EpisodeUi
import com.berlin.aflami.viewmodel.uistate.SeasonUiState
import com.berlin.designsystem.R


@Composable
fun SeasonsScreen(
    seasons: List<SeasonUiState>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(Theme.color.surface),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        seasons.forEach { season ->
            EpisodeScreen(
                seasonNumber = season.seasonNumber,
                episodes = season.episodes
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = Theme.color.stroke,
                thickness = 1.dp
            )
        }
    }
}

@Composable
fun EpisodeScreen(
    seasonNumber: String,
    episodes: List<EpisodeUi>,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.background(Theme.color.surface),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SeasonsHeader(
            seasonNumber = seasonNumber,
            episodeCount = episodes.size.toString(),
            isExpanded = isExpanded,
            onToggleExpand = { isExpanded = !isExpanded }
        )
        if (isExpanded) {
            Column {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    items(episodes.size) { index ->
                        EpisodeCard(
                            episode = episodes[index],
                            modifier = Modifier.fillMaxWidth(),
                            onClickPlay = {}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SeasonsHeader(
    seasonNumber: String,
    episodeCount: String,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .background(Theme.color.surface)
            .clickable { onToggleExpand() }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Season $seasonNumber",
            style = Theme.textStyle.title.small,
            color = Theme.color.textColors.title,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "$episodeCount episodes",
                style = Theme.textStyle.label.small,
                color = Theme.color.textColors.hint,
            )
            Icon(
                painter = if (isExpanded) painterResource(R.drawable.arrow_up) else painterResource(R.drawable.arrow_down),
                contentDescription = stringResource(R.string.icon_cd),
                modifier = Modifier.size(20.dp),
                tint = Theme.color.textColors.title
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SeasonsScreenPreview() {
    AflamiTheme(isDarkTheme = false) {
        SeasonsScreen(
            seasons = sampleSeasons,
            modifier = Modifier.background(Theme.color.surface)
        )
    }
}
val sampleSeasons = listOf(
    SeasonUiState(
        seasonNumber = "1",
        episodes = listOf(
            EpisodeUi(
                id = 0,
                episodeNumber = 1,
                title = "Recovering a Body",
                description = "In 1935, corrections officer Paul Edgecomb oversees The Green Mile, the death row",
                imageUrl = "",
                time = "58 m",
                date = "3 Sep 2020",
                rating = "8.2"
            ),
            EpisodeUi(
                id = 1,
                episodeNumber = 2,
                title = "The Mouse",
                description = "A mysterious inmate arrives at The Green Mile.",
                imageUrl = "",
                time = "60 m",
                date = "10 Sep 2020",
                rating = "8.5"
            )
        )
    ),
    SeasonUiState(
        seasonNumber = "2",
        episodes = listOf(
            EpisodeUi(
                id = 0,
                episodeNumber = 1,
                title = "Recovering a Body",
                description = "In 1935, corrections officer Paul Edgecomb oversees The Green Mile, the death row",
                imageUrl = "",
                time = "58 m",
                date = "3 Sep 2020",
                rating = "8.2"
            ),
            EpisodeUi(
                id = 1,
                episodeNumber = 2,
                title = "The Mouse",
                description = "A mysterious inmate arrives at The Green Mile.",
                imageUrl = "",
                time = "60 m",
                date = "10 Sep 2020",
                rating = "8.5"
            )
        )
    ),
    SeasonUiState(
        seasonNumber = "3",
        episodes = listOf(
            EpisodeUi(
                id = 0,
                episodeNumber = 1,
                title = "Recovering a Body",
                description = "In 1935, corrections officer Paul Edgecomb oversees The Green Mile, the death row",
                imageUrl = "",
                time = "58 m",
                date = "3 Sep 2020",
                rating = "8.2"
            ),
            EpisodeUi(
                id = 1,
                episodeNumber = 2,
                title = "The Mouse",
                description = "A mysterious inmate arrives at The Green Mile.",
                imageUrl = "",
                time = "60 m",
                date = "10 Sep 2020",
                rating = "8.5"
            ),
            EpisodeUi(
                id = 2,
                episodeNumber = 3,
                title = "Recovering a Body",
                description = "In 1935, corrections officer Paul Edgecomb oversees The Green Mile, the death row",
                imageUrl = "",
                time = "58 m",
                date = "3 Sep 2020",
                rating = "8.2"
            ),
            EpisodeUi(
                id = 3,
                episodeNumber = 4,
                title = "The Mouse",
                description = "A mysterious inmate arrives at The Green Mile.",
                imageUrl = "",
                time = "60 m",
                date = "10 Sep 2020",
                rating = "8.5"
            )
        )
    )
)
