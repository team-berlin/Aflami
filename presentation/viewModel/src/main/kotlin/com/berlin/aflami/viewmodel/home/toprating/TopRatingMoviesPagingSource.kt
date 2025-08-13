package com.berlin.aflami.viewmodel.home.toprating

import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.mapper.toMediaUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import usecase.movie.GetTopRatedMoviesUseCase
import usecase.tvshow.GetTopRatedTVShowUseCase

class TopRatingMoviesPagingSource(
    private val getTopRateMovieUseCase: GetTopRatedMoviesUseCase,
    private val getTopRatedTvShowUseCase: GetTopRatedTVShowUseCase,
) : BasePagingSource<MediaUiState>() {

    override suspend fun fetchData(page: Int): List<MediaUiState> = coroutineScope {
        val moviesDeferred = async {
            getTopRateMovieUseCase(page).map {
                it.toMediaUiState(
                )
            }
        }
        val tvShowsDeferred = async {
            getTopRatedTvShowUseCase(page).map {
                it.toMediaUiState(

                )
            }
        }

        val movies = moviesDeferred.await()
        val tvShows = tvShowsDeferred.await()
        (movies + tvShows).sortedByDescending { it.rating }
    }
}