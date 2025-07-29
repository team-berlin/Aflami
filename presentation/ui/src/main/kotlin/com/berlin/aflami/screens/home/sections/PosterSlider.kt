package com.berlin.aflami.screens.home.sections

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MovieCard
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun PosterSlider(
    modifier: Modifier = Modifier,
    mediaList: List<MediaUiState>,
    onClick: (MediaUiState) -> Unit = {},
    pagerState: PagerState,
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = remember { 244.dp }
    val contentPadding = (screenWidth - itemWidth) / 2
    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(itemWidth),
        contentPadding = PaddingValues(horizontal = contentPadding),
        modifier = modifier.fillMaxWidth()
    ) { pageIndex ->
        val mediaItem = mediaList.getOrNull(pageIndex)
        mediaItem?.let {
            MovieCard(
                isCentered = pageIndex == pagerState.currentPage,
                onClick = { onClick(it) },
                rating = it.rating,
                posterImageUrl = it.poster
            )
        }
    }
}