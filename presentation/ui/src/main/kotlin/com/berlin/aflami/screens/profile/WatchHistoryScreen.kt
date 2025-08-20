package com.berlin.aflami.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.component.TabBar
import com.berlin.aflami.component.TabBarItem
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.navigation.MovieDetailsDestination
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.search.components.CountryTourExploring
//import com.berlin.aflami.screens.search.components.CountryTourExploring
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.profile.watchhistory.WatchHistoryInteractionListener
import com.berlin.aflami.viewmodel.profile.watchhistory.WatchHistoryScreenEffect
import com.berlin.aflami.viewmodel.profile.watchhistory.WatchHistoryUiState
import com.berlin.aflami.viewmodel.profile.watchhistory.WatchHistoryViewModel
import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.ui.R

@Composable
fun WatchHistoryScreen(
    viewModel: WatchHistoryViewModel = hiltViewModel(),
) {
    val navController = Theme.navController
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { newEffect ->
            watchHistoryOnReceiveEffect(navController = navController, effect = newEffect)
        }
    }

    AnimatedVisibility(
        enter =  EnterTransition.None ,
        exit = ExitTransition.None ,
        visible = state.isLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(R.string.loading)
        )
    }

    AnimatedVisibility(
        enter =  EnterTransition.None ,
        exit = ExitTransition.None ,
        visible = !state.isLoading
    ) {
        WatchHistoryContent(
            state = state, listener = viewModel
        )
    }
}

private fun watchHistoryOnReceiveEffect(navController: NavController, effect: WatchHistoryScreenEffect) {
    when (effect) {
        WatchHistoryScreenEffect.NavigateBack -> {
            navController.popBackStack()
        }
        is WatchHistoryScreenEffect.NavigateToDetailsScreen -> {
            navController.navigate(
                MovieDetailsDestination(
                    movieId = effect.mediaId,
                )
            )
        }
    }
}

@Composable
fun WatchHistoryContent(
    state: WatchHistoryUiState, listener: WatchHistoryInteractionListener,
) {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Theme.color.surface)) {
        TopBar(modifier = Modifier
            .statusBarsPadding()
            .padding(vertical = 8.dp), title = {
            Text(
                text = stringResource(R.string.watch__history),
                style = Theme.textStyle.title.large,
                color = Theme.color.textColors.title,
            )
        }, leadingIcon = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Theme.color.surfaceHigh)
                    .clickable {
                        listener.onBackClicked()
                    }
                    .padding(10.dp), contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = stringResource(R.string.arrow_back),
                    tint = Theme.color.textColors.title
                )
            }
        })

        TabBar(
            selectedTabIndex = state.selectedTabOption.index,
            containerColor = Theme.color.surface,
            items = listOf(
                TabBarItem(
                    text = stringResource(com.berlin.designsystem.R.string.movies),
                    isSelected = state.selectedTabOption == TabOption.MOVIES,
                ), TabBarItem(
                    text = stringResource(com.berlin.designsystem.R.string.tv_shows),
                    isSelected = state.selectedTabOption == TabOption.TV_SHOWS,
                )
            ),
            onTabChange = {
                listener.onTabOptionClicked(
                    when (it) {
                        0 -> TabOption.MOVIES
                        1 -> TabOption.TV_SHOWS
                        else -> throw IllegalArgumentException("Invalid tab index")
                    }
                )
            },
        )
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    text = stringResource(R.string.loading)
                )
            }

            else -> {
                val movies = state.movies.collectAsLazyPagingItems()
                val tvShows = state.tvShows.collectAsLazyPagingItems()
                val moviesLoadState = movies.loadState
                val tvShowsLoadState = tvShows.loadState
                when (state.selectedTabOption) {

                    TabOption.MOVIES -> {

                        val isEmpty =
                            movies.itemCount == 0 && moviesLoadState.refresh is LoadState.NotLoading && moviesLoadState.append is LoadState.NotLoading
                        if (isEmpty) {
//                            CountryTourExploring(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .align(Alignment.CenterHorizontally),
//                                painterResource(R.drawable.no_search_result),
//                                R.string.no_result_found
//                            )
                        } else if (LoadState.Loading == moviesLoadState.refresh) {
                            CircularProgressIndicator(
                                modifier = Modifier.fillMaxSize(),
                                text = stringResource(R.string.loading)
                            )
                        } else {
                            when (val error = moviesLoadState.refresh) {
                                is LoadState.Error -> {
                                    val isNoInternet = error.error.message?.contains(
                                        "No internet connection",
                                        ignoreCase = true
                                    ) == true
                                    if (isNoInternet) {
                                        NoInternetConnectionPlaceholder(
                                            onClick = { movies.retry() }
                                        )
                                    } else {
//                                        CountryTourExploring(
//                                            modifier = Modifier
//                                                .fillMaxSize()
//                                                .align(Alignment.CenterHorizontally),
//                                            painterResource(R.drawable.no_search_result),
//                                            R.string.no_result_found
//                                        )
                                    }
                                }

                                LoadState.Loading -> {}
                                is LoadState.NotLoading -> {}
                            }

                            Box(modifier = Modifier.fillMaxSize()) {

                                LazyVerticalGrid(
                                    modifier = Modifier.fillMaxSize(),
                                    columns = GridCells.Adaptive(minSize = 160.dp),
                                    contentPadding = PaddingValues(
                                        start = 16.dp, end = 16.dp, top = 8.dp
                                    ),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(
                                        count = movies.itemCount
                                    ) { index ->
                                        val movie = movies[index]
                                        if (movie != null) {
                                            MediaCard(
                                                modifier = Modifier.height(222.dp),
                                                onClick = {
                                                    listener.onMediaCardClicked(
                                                        mediaId = movie.id,
                                                        mediaType = MediaType.MOVIE
                                                    )
                                                },
                                                mediaImg = movie.posterUrl,
                                                title = movie.title,
                                                typeOfMedia = MediaType.MOVIE.name,
                                                date = movie.releaseDate,
                                                rating = movie.rating
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    TabOption.TV_SHOWS -> {
                        val isEmpty =
                            tvShows.itemCount == 0 && tvShowsLoadState.refresh is LoadState.NotLoading && tvShowsLoadState.append is LoadState.NotLoading
                        if (isEmpty) {
//                            CountryTourExploring(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .align(Alignment.CenterHorizontally),
//                                painterResource(R.drawable.no_search_result),
//                                R.string.no_result_found
//                            )
                        } else {
                            when (val error = moviesLoadState.refresh) {
                                is LoadState.Error -> {
                                    val isNoInternet = error.error.message?.contains(
                                        "No internet connection",
                                        ignoreCase = true
                                    ) == true
                                    if (isNoInternet) {
                                        NoInternetConnectionPlaceholder(
                                            onClick = { movies.retry() }
                                        )
                                    } else {
//                                        CountryTourExploring(
//                                            modifier = Modifier
//                                                .fillMaxSize()
//                                                .align(Alignment.CenterHorizontally),
//                                            painterResource(R.drawable.no_search_result),
//                                            R.string.no_result_found
//                                        )
                                    }
                                }

                                LoadState.Loading -> {}
                                is LoadState.NotLoading -> {}
                            }
                            LazyVerticalGrid(
                                modifier = Modifier.fillMaxSize(),
                                columns = GridCells.Adaptive(minSize = 160.dp),
                                contentPadding = PaddingValues(
                                    start = 16.dp, end = 16.dp, top = 8.dp
                                ),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(
                                    count = tvShows.itemCount
                                ) { index ->
                                    val tvShows = tvShows[index]
                                    if (tvShows != null) {
                                        MediaCard(
                                            modifier = Modifier.height(222.dp),
                                            mediaImg = tvShows.posterUrl,
                                            title = tvShows.title,
                                            onClick = {
                                                listener.onMediaCardClicked(
                                                    tvShows.id,
                                                    mediaType = MediaType.TV_SHOW
                                                )
                                            },
                                            typeOfMedia = MediaType.TV_SHOW.name,
                                            date = tvShows.releaseDate,
                                            rating = tvShows.rating
                                        )

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

