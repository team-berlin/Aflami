package com.berlin.aflami.screens.home.sections

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.ui.R

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun TopRatingHomeSections(
    modifier: Modifier = Modifier,
    seeAllOnClick: () -> Unit,
    onMovieItemClicked: (movieId: Long) -> Unit = {},
    onTVShowItemClicked: (tvShowId: Long) -> Unit = {},
    state: List<MediaUiState>,
    sectionTitleId: Int,
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(sectionTitleId),
                style = Theme.textStyle.headline.small,
                color = Theme.color.textColors.title
            )
            Text(
                text = stringResource(R.string.all),
                style = Theme.textStyle.label.medium,
                color = Theme.color.primary,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    seeAllOnClick()
                })
        }
        BoxWithConstraints {
            val screenWidth = maxWidth
            val spaceBetween = 8.dp
            val maxCardsInRow = (screenWidth / (156.dp + spaceBetween)).toInt().coerceAtLeast(2)

            val totalSpacing = spaceBetween * (maxCardsInRow - 1)
            val cardWidth = (screenWidth - totalSpacing) / maxCardsInRow
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.size) { index ->
                    val item = state[index]
                    MediaCard(
                        Modifier
                            .height(222.dp)
                            .width(cardWidth),
                        mediaImg = item.poster,
                        title = item.title,
                        typeOfMedia = item.mediaType?.name ?: MediaType.MOVIE.name,
                        date = item.releaseYear,
                        rating = item.rating,
                    ) {
                        when(item.mediaType){
                            MediaType.MOVIE -> onMovieItemClicked(item.id)
                            MediaType.TV_SHOW -> onTVShowItemClicked(item.id)
                            else -> throw IllegalArgumentException("Unknown media type")
                        }                    }
                }

            }
        }
    }
}