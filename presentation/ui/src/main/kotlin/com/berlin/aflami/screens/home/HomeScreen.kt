package com.berlin.aflami.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.BlurredPosterBackground
import com.berlin.aflami.component.GenersChip
import com.berlin.aflami.component.HomeBar
import com.berlin.aflami.component.SectionTitle
import com.berlin.aflami.screens.home.component.MediaSections
import com.berlin.aflami.screens.home.component.PosterSlider
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.home.HomeInteractionListener
import com.berlin.aflami.viewmodel.home.HomeScreenEffect
import com.berlin.aflami.viewmodel.home.HomeViewModel
import com.berlin.aflami.viewmodel.home.HomeUiState
import com.berlin.ui.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(), onEffect: (HomeScreenEffect) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getContinueWatchingMedia()
        viewModel.effect.collect {
            onEffect(it)
        }
    }
    HomeContent(
        state = state, listener = viewModel
    )

}


@Composable
private fun HomeContent(
    state: HomeUiState, listener: HomeInteractionListener,
) {
    val pagerState = rememberPagerState(
        initialPage = 0, pageCount = { state.popularMedia.popularMedia.size })

    val currentMedia = state.popularMedia.popularMedia.getOrNull(pagerState.currentPage)
    LazyColumn(
        modifier = Modifier.padding(vertical = 6.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.surface)
            ) {
                Box() {
                    BlurredPosterBackground(
                        imageUrl = currentMedia?.poster ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(390.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                    ) {

                        HomeBar(
                            modifier = Modifier.fillMaxWidth(),
                            onSearchClicked = {
                                listener.onSearchClicked()
                            },
                        )

                        SectionTitle(
                            title = stringResource(com.berlin.designsystem.R.string.popular),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp),
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .size(20.dp),
                                    painter = painterResource(com.berlin.designsystem.R.drawable.trending),
                                    tint = Theme.color.secondary,
                                    contentDescription = stringResource(com.berlin.designsystem.R.string.trending)
                                )
                            })

                        PosterSlider(
                            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                            mediaList = state.popularMedia.popularMedia,
                            pagerState = pagerState,
                            onClick = { listener.onClickPopularMovieCard(it.id, it.mediaType) }
                        )

                        currentMedia?.let { media ->
                            Text(
                                media.title,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(bottom = 8.dp),
                                style = Theme.textStyle.title.small,
                                color = Theme.color.textColors.title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row {
                                media.genre.forEach { genre ->
                                    Box(
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .align(Alignment.CenterVertically)
                                    ) {
                                        GenersChip(label = genre.toString())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        item {
            MediaSections(
                onShowAllContinueWatchingClick = {
                    listener.onShowAllContinueWatchingClicked()
                }, state = state.mediaContinueWatching, sectionTitleId = R.string.continue_watching
            )
            val lazyListState = rememberLazyListState()
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
//                Column(modifier = Modifier.padding(bottom = 100.dp))
//                {
//                        upcomingMovies(
//                            moviesGenres = state.upcomingMovieGenres,
//                            movies = state.upcomingMovies,
//                            onMovieClicked = listener::onClickUpcomingMovieCard,
//                            onChangeMovieGenre = listener::onChangeUpcomingMovieGenre,
//                        )
//                }
            }
        }
        item {
            MediaSections(
                onShowAllContinueWatchingClick = { listener.onAllTopRatingClicked() },
                state = state.topRatedMediaUiState.topRatedMedia,
                sectionTitleId = R.string.top_rating,
            )
        }
    }
}