package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState

@Composable
fun MediaCastGrid(
    modifier: Modifier = Modifier,
    mediaCast: List<ActorUiState>,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 104.dp),
            modifier = Modifier.align(Alignment.TopCenter),
            contentPadding = PaddingValues(top = 12.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(mediaCast) { cast ->
                MediaCastItem(
                    modifier = Modifier.size(130.dp),
                    name = cast.name,
                    poster = cast.poster,
                )
            }
        }
    }

}
