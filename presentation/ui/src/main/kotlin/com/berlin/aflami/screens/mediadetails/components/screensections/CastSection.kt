package com.berlin.aflami.screens.mediadetails.components.screensections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.screens.mediadetails.components.MediaCastItem
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState

@Composable
fun CastSection(
    cast: List<ActorUiState>,
    onShowAllClicked: () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(com.berlin.ui.R.string.cast),
                style = Theme.textStyle.headline.small,
                color = Theme.color.textColors.title
            )
            Text(
                text = stringResource(com.berlin.ui.R.string.all),
                style = Theme.textStyle.label.medium,
                color = Theme.color.primary,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onShowAllClicked() }
            )
        }

        BoxWithConstraints {
            val screenWidth = this.maxWidth
            val cardSize = 78.dp
            val spaceBetween = 8.dp
            val totalCardWidth = cardSize + spaceBetween
            val maxCardsInRow = (screenWidth / totalCardWidth).toInt()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(spaceBetween)
            ) {
                cast.take(maxCardsInRow).forEach {
                    MediaCastItem(
                        modifier = Modifier.size(cardSize),
                        name = it.name,
                        poster = it.poster
                    )
                }
            }
        }
    }
}
