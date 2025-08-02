package com.berlin.aflami.viewmodel.home.continueWatching

import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import usecase.movie.ContinueWatchingMovieUseCase
import usecase.tvshow.ContinueWatchingTVShowUseCase

class ContinueWatchingMediaPagingSource(
    private val movieUseCase: ContinueWatchingMovieUseCase,
    private val tvShowUseCase: ContinueWatchingTVShowUseCase,
) : BasePagingSource<MediaUiState>() {

    override suspend fun fetchData(page: Int): List<MediaUiState> = coroutineScope {
        val moviesDeferred = async {
            movieUseCase(page).map { MediaUiState() }
        }
        val tvShowsDeferred = async {
            tvShowUseCase(page).map { MediaUiState() }
        }

        val movies = moviesDeferred.await()
        val tvShows = tvShowsDeferred.await()

        (movies + tvShows).shuffled()
    }
}