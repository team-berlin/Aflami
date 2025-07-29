package com.berlin.aflami.screens.home.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MovieCard
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.designsystem.R
import kotlinx.coroutines.delay

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

    LaunchedEffect(pagerState) {
        while (true) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(itemWidth),
        contentPadding = PaddingValues(horizontal = contentPadding),
        modifier = modifier.fillMaxWidth()
    ) { pageIndex ->
        val actualIndex = pageIndex % mediaList.size
        val mediaItem = mediaList.getOrNull(actualIndex )
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


//@Composable
//fun PosterSlider2(
//    modifier: Modifier = Modifier,
//    mediaList: List<MovieCardUiState>,
//    onClick: () -> Unit = {},
//
//) {
//    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
//    val itemWidth = remember { 244.dp }
//    val contentPadding = (screenWidth - itemWidth) / 2
//
//    val pagerState = rememberPagerState(
//        initialPage = 0, pageCount = { com.berlin.aflami.screens.home.component.mediaList.size })
//
//    LaunchedEffect(pagerState.currentPage, mediaList) {
//        if (mediaList.isNotEmpty()) {
//            delay(4000)
//            val nextPage = (pagerState.currentPage + 1) % mediaList.size
//            pagerState.animateScrollToPage(nextPage)
//        }
//    }
//
//    HorizontalPager(
//        state = pagerState,
//        pageSize = PageSize.Fixed(itemWidth),
//        contentPadding = PaddingValues(horizontal = contentPadding),
//        modifier = modifier.fillMaxWidth()
//    ) { pageIndex ->
//        val mediaItem = mediaList.getOrNull(pageIndex)
//        mediaItem?.let {
//            MovieCard(
//                isCentered = pageIndex == pagerState.currentPage,
//                onClick = { onClick() },
//                rating = it.rating,
//                posterImageUrl = it.posterImage.toString()
//            )
//        }
//    }
//}
//
//@Preview
//@Composable
//fun PosterSliderPreview() {
//    PosterSlider2(
//        mediaList = mediaList,
//        onClick = {},
//    )
//}
//
//val mediaList = listOf(
//    MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster3,
//        rating = "9.7",
//    ),
//    MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster2,
//        rating = "9.7",
//    ),
//    MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster3,
//        rating = "9.7",
//    ),
//
//    MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster2,
//        rating = "9.7",
//    ),
//    MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster3,
//        rating = "9.7",
//    ),
//
//    MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster2,
//        rating = "9.7",
//    ),   MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster3,
//        rating = "9.7",
//    ),
//
//    MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster2,
//        rating = "9.7",
//    ),   MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster3,
//        rating = "9.7",
//    ),
//
//    MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster2,
//        rating = "9.7",
//    ),   MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster3,
//        rating = "9.7",
//    ),
//
//    MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster2,
//        rating = "9.7",
//    ),   MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster3,
//        rating = "9.7",
//    ),
//
//    MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster2,
//        rating = "9.7",
//    ),   MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster3,
//        rating = "9.7",
//    ),
//
//    MovieCardUiState(
//        id = "2",
//        posterImage = R.drawable.movie_poster2,
//        rating = "9.7",
//    ),
//)
//
//
//data class MovieCardUiState(
//    val id: String,
//    val posterImage: Int,
//    val rating: String
//)