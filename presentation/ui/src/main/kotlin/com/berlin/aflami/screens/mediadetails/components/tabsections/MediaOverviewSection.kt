package com.berlin.aflami.screens.mediadetails.components.tabsections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.GenersChip
import com.berlin.aflami.screens.mediadetails.components.CircularDot
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaDetailsUiState
import com.berlin.ui.R

@Composable
fun MediaOverviewSection(state: MediaDetailsUiState) {
    Spacer(Modifier.height(12.dp))
    Column(Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = state.title,
            style = Theme.textStyle.title.large,
            color = Theme.color.textColors.title,
        )
        Spacer(Modifier.height(12.dp))
        Row {
            state.genres.forEach { genre ->
                Box(modifier = Modifier.padding(end = 4.dp)) {
                    GenersChip(label = genre)
                }
            }
        }
    }

    Spacer(Modifier.height(8.dp))
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            state.releaseYear,
            style = Theme.textStyle.label.small,
            color = Theme.color.textColors.hint
        )
        state.duration?.takeIf { it.isNotEmpty() }?.let {
            CircularDot()
            Text(it, style = Theme.textStyle.label.small, color = Theme.color.textColors.hint)
        }
        state.numberOfSeasons?.toString()?.let {
            CircularDot()
            Text("$it ${stringResource(R.string.season)}", style = Theme.textStyle.label.small, color = Theme.color.textColors.hint)
        }
        state.originalCountry?.takeIf { it.isNotEmpty() }?.let {
            CircularDot()
            Text(it, style = Theme.textStyle.label.small, color = Theme.color.textColors.hint)
        }
    }
}
