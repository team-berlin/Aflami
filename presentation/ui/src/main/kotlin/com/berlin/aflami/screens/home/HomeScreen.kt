package com.berlin.aflami.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.berlin.aflami.screens.home.section.upcomingMovies
import com.berlin.aflami.viewmodel.home.HomeInteractionListener
import com.berlin.aflami.viewmodel.home.HomeScreenEffect
import com.berlin.aflami.viewmodel.home.HomeViewModel
import com.berlin.aflami.viewmodel.home.uistate.HomeUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onEffect: (HomeScreenEffect) -> Unit = {},
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val state by homeViewModel.state.collectAsStateWithLifecycle()



    HomeScreenContent(
        modifier = modifier,
        state = state,
        interactionListener = homeViewModel,
    )

}

@Composable
private fun HomeScreenContent(
    state: HomeUiState,
    interactionListener: HomeInteractionListener,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(bottom = 100.dp)) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                state = lazyListState,
            ) {
                upcomingMovies(
                    moviesGenres = state.upcomingMovieGenres,
                    movies = state.upcomingMovies,
                    onMovieClicked = interactionListener::onClickUpcomingMovieCard,
                    onChangeMovieGenre = interactionListener::onChangeUpcomingMovieGenre,
                )
            }
        }
    }
}