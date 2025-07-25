package com.berlin.aflami.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.berlin.aflami.screens.home.component.HomeSections
import com.berlin.aflami.screens.home.component.upcomingMovies
import com.berlin.aflami.viewmodel.home.HomeInteractionListener
import com.berlin.aflami.viewmodel.home.HomeScreenEffect
import com.berlin.aflami.viewmodel.home.HomeViewModel
import com.berlin.aflami.viewmodel.home.uistate.HomeUiState
import com.berlin.ui.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onEffect: (HomeScreenEffect) -> Unit = {},
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getContinueWatchingMedia()
        viewModel.effect.collect {
            onEffect(it)
        }
    }

    HomeScreenContent(
        state = state,
        listener = viewModel,
    )

}

@Composable
private fun HomeScreenContent(
    state: HomeUiState,
    listener: HomeInteractionListener,
) {

    Column(
        modifier = Modifier.padding(top = 6.dp)
    ) {
        HomeSections(
            onShowAllContinueWatchingClick = {
                listener.onShowAllContinueWatchingClicked()
            }, state = state.mediaContinueWatching, sectionTitleId = R.string.continue_watching
        )
        val lazyListState = rememberLazyListState()
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(bottom = 100.dp)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState,
                ) {
                    upcomingMovies(
                        moviesGenres = state.upcomingMovieGenres,
                        movies = state.upcomingMovies,
                        onMovieClicked = listener::onClickUpcomingMovieCard,
                        onChangeMovieGenre = listener::onChangeUpcomingMovieGenre,
                    )
                }
            }
        }
    }
}