package com.berlin.aflami.screens.mediadetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaCastUiState

@Composable
fun MediaCastGrid(
    modifier: Modifier = Modifier,
    mediaCast: List<MediaCastUiState>,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 104.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = mediaCast,
        ) { cast ->
            MediaCastItem(
                modifier = Modifier.size(104.dp),
                name = cast.name,
                poster = cast.poster,
            )
        }
    }
}