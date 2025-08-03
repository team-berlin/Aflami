package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.CircularIconButton
import com.berlin.aflami.component.RatingCard
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.details.series.EpisodeUiState
import com.berlin.designsystem.R
import com.berlin.safeimageviewer.SafeImageViewer

@Composable
fun EpisodeCard(
    episode: EpisodeUiState,
    modifier: Modifier = Modifier,
    onClickPlay: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ImageWithRatingBadge(
                modifier = Modifier,
                imageUrl = "https://image.tmdb.org/t/p/w342".plus(episode.stillPath),
                rating = episode.voteAverage.toString()
            )

            EpisodeDetails(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f),
                episodeNumber = episode.episodeNumber,
                title = episode.name,
                time = episode.runtime.toString(),
                date = episode.airDate
            )

            CircularIconButton(
                painter = painterResource(R.drawable.play),
                onClick = onClickPlay
            )
        }

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = episode.overview,
            style = Theme.textStyle.label.small,
            color = Theme.color.textColors.hint,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ImageWithRatingBadge(
    modifier: Modifier = Modifier,
    imageUrl: String,
    rating: String
) {
    Box(
        modifier = modifier
            .size(width = 116.dp, height = 78.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = Theme.color.stroke,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        SafeImageViewer(
            modifier = Modifier.fillMaxSize(),
            imageUri = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            error = painterResource(R.drawable.place_holder),
            fallback = painterResource(R.drawable.place_holder),
            placeholder = painterResource(R.drawable.place_holder),
        )

        RatingCard(
            modifier = Modifier.align(Alignment.TopEnd),
            rating = rating
        )
    }
}

@Composable
private fun EpisodeDetails(
    episodeNumber: Int,
    title: String,
    time: String,
    date: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = "${stringResource(com.berlin.ui.R.string.episode)} $episodeNumber",
            style = Theme.textStyle.label.large,
            color = Theme.color.textColors.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = title,
            style = Theme.textStyle.label.small,
            color = Theme.color.textColors.hint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = time,
                style = Theme.textStyle.label.small,
                color = Theme.color.textColors.hint
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Theme.color.stroke)
            )

            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = date,
                style = Theme.textStyle.label.small,
                color = Theme.color.textColors.hint
            )
        }
    }
}


