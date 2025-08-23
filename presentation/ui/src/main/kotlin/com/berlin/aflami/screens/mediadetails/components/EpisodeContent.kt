package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.details.common.MediaDetailsScreenInteractionListener
import com.berlin.aflami.viewmodel.details.series.EpisodeUiState
import com.berlin.aflami.viewmodel.details.series.TVShowRowSectionUiState
import com.berlin.aflami.viewmodel.details.series.TVShowTabContent
import com.berlin.designsystem.R

@Composable
fun TVShowRowSection(
    state: TVShowRowSectionUiState,
    listener: MediaDetailsScreenInteractionListener,
    content: @Composable BoxScope
    .(
        state: TVShowRowSectionUiState,
        listener: MediaDetailsScreenInteractionListener
    ) -> Unit
) {

    Crossfade(targetState = state) { tvShowRowSectionUiState ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            content(tvShowRowSectionUiState, listener)
        }

    }
}

fun LazyListScope.seasonItem(
    state: TVShowRowSectionUiState,
    expandedStates: SnapshotStateMap<Int, Boolean>,
    listener: MediaDetailsScreenInteractionListener,
    ) {
    if (state is TVShowRowSectionUiState.Success) {
        val tab = state.content
        if (tab is TVShowTabContent.Season) {
            tab.seasonToEpisodesMap.forEach { (seasonNumber, episodes) ->
                val isExpanded = expandedStates[seasonNumber] ?: false

                stickyHeader(key = "season_$seasonNumber")
                {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Theme.color.surface)
                    )
                    {
                        SeasonsHeader(
                            modifier = Modifier.padding(vertical = 12.dp),
                            seasonNumber = seasonNumber.toString(),
                            episodeCount = episodes.size.toString(),
                            isExpanded = isExpanded,
                            onToggleExpand = {
                                expandedStates[seasonNumber] = !isExpanded
                            }
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
                if (isExpanded) {
                    items(
                        items = episodes,
                        key = { episode -> "episode_${seasonNumber}_${episode.id}" }
                    )
                    { episode ->
                        EpisodeCard(
                            episode = episode,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            onClickPlay = {
                                episode.trailer?.let { listener.onEpisodePlayClicked(it) }
                            }
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun SeasonsSection(
    seasonsMap: MutableMap<Int, List<EpisodeUiState>>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(Theme.color.surface),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        for ((seasonNumber, episodes) in seasonsMap) {
            EpisodeScreen(
                seasonNumber = seasonNumber.toString(),
                episodes = episodes
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
    episodes: List<EpisodeUiState>,
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    episodes.forEach { episode ->
                        EpisodeCard(
                            episode = episode,
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
    modifier: Modifier = Modifier,
    seasonNumber: String,
    episodeCount: String,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surface)
            .clickable { onToggleExpand() }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "${stringResource(com.berlin.ui.R.string.season)} $seasonNumber",
            style = Theme.textStyle.title.small,
            color = Theme.color.textColors.title,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "$episodeCount ${stringResource(com.berlin.ui.R.string.episode)}",
                style = Theme.textStyle.label.small,
                color = Theme.color.textColors.hint,
            )
            Icon(
                painter = if (isExpanded) painterResource(R.drawable.arrow_up) else painterResource(
                    R.drawable.arrow_down
                ),
                contentDescription = stringResource(R.string.icon_cd),
                modifier = Modifier.size(20.dp),
                tint = Theme.color.textColors.title
            )
        }
    }
}
