package com.berlin.aflami.viewmodel.profile.watchhistory

import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.mapper.toMovieUiState
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import usecase.movie.ContinueWatchingMovieUseCase
import usecase.tvshow.ContinueWatchingTVShowUseCase

class WatchHistoryMoviesPagingSource(
    private val movieUseCase: ContinueWatchingMovieUseCase
) : BasePagingSource<MovieUiState>() {

    override suspend fun fetchData(page: Int): List<MovieUiState> {
        return movieUseCase(page).map { it.toMovieUiState() }
    }
}

class WatchHistoryTVShowsPagingSource(
    private val tvShowUseCase: ContinueWatchingTVShowUseCase
) : BasePagingSource<TVShowUiState>() {

    override suspend fun fetchData(page: Int): List<TVShowUiState> {
        return tvShowUseCase(page).map { it.toUiState() }
    }
}
