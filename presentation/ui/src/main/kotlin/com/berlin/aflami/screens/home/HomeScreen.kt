package com.berlin.aflami.screens.home

import androidx.compose.animation.AnimatedVisibility
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
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.BlurredPosterBackground
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.GenersChip
import com.berlin.aflami.component.HomeBar
import com.berlin.aflami.component.SectionTitle
import com.berlin.aflami.screens.home.component.MoodPickerDialog
import com.berlin.aflami.screens.home.sections.MediaSections
import com.berlin.aflami.screens.home.sections.MoodPickerSection
import com.berlin.aflami.screens.home.sections.PosterSlider
import com.berlin.aflami.screens.home.sections.UpcomingMoviesSection
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.home.HomeInteractionListener
import com.berlin.aflami.viewmodel.home.HomeScreenEffect
import com.berlin.aflami.viewmodel.home.HomeUiState
import com.berlin.aflami.viewmodel.home.HomeViewModel
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
    AnimatedVisibility(state.isLoading) {
        Box(
            modifier = Modifier
                .background(Theme.color.surface),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                text = "Loading..."
            )
        }
    }
    val pagedMovies = state.mediaContinueWatching.collectAsLazyPagingItems()
    val currentMedia = state.popularMedia.popularMedia.getOrNull(pagerState.currentPage)
    AnimatedVisibility(state.isLoading.not()) {
        LazyColumn(
            modifier = Modifier
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(bottom = 6.dp)
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
                    },
                    state = state.mediaContinueWatching,
                    sectionTitleId = R.string.continue_watching
                )
            }
        if(pagedMovies.itemCount>0) {
            item {
                MediaSections(
                    seeAllOnClick = {
                        listener.onShowAllContinueWatchingClicked()
                    },
                    state = pagedMovies,
                    sectionTitleId = R.string.continue_watching,
                    cardClick = { id, type ->
                        listener.onClickCard(id, type)
                    },
                )
            }
        }
            item {
                MoodPickerSection(state, listener)
            }
            item {
                UpcomingMoviesSection(
                    movies = state.upcomingMoviesSectionUiState.upcomingMovies,
                    genres = state.upcomingMoviesSectionUiState.movieGenres,
                    onMovieClick = { listener.onClickUpcomingMovieCard(it) },
                    onGenreClick = { listener.onChangeUpcomingMovieGenre(it) },
                    modifier = Modifier
                )
            }
        }
    }
    AnimatedVisibility(state.moodPickerUiState.openMovieDialog) {
        with(state.moodPickerUiState.selectedMovie) {
            MoodPickerDialog(
                mediaImg = poster,
                title = title,
                typeOfMedia = mediaType,
                date = releaseYear,
                rate = rating,
                onDismiss = { listener.onDismissMoodPickerDialog() },
                onClickViewDetails = { listener.onClickViewDetails() },
                onClickGetAnotherMovie = { listener.onClickGetAnotherMovie() },
            )
        }

    }

}