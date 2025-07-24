package com.berlin.aflami.screens.home.component

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.ui.R

@Composable
fun HomeSections(
    modifier: Modifier = Modifier,
    onShowAllContinueWatchingClick: () -> Unit,
    state: List<MediaUiState>,
    sectionTitleId:Int
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
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
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onShowAllContinueWatchingClick()
                    }
            )
        }
        BoxWithConstraints {
            val screenWidth = maxWidth
            val spaceBetween = 8.dp

            val maxCardsInRow = (screenWidth / (156.dp+spaceBetween)).toInt().coerceAtLeast(2)


            val totalSpacing = spaceBetween * (maxCardsInRow - 1)
            val cardWidth = (screenWidth - totalSpacing) / maxCardsInRow
            LazyRow(
            modifier = Modifier.padding(top = 12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
                items(state.size) {
                    MediaCard(
                        Modifier.height(222.dp).width(cardWidth),
                        mediaImg = state[it].poster,
                        title = state[it].title,
                        typeOfMedia = state[it].mediaType.name,
                        date = state[it].releaseYear,
                        rating = state[it].rating,
                    )
                }
            }
        }
    }

}