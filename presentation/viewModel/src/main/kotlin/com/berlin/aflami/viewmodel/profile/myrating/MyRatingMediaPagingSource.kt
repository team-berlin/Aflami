package com.berlin.aflami.viewmodel.profile.myrating

import com.berlin.aflami.viewmodel.base.PagedResultPagingSource
import com.berlin.aflami.viewmodel.mapper.toMovieUiStateFromRated
import com.berlin.aflami.viewmodel.mapper.toTvUiStateFromRated
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.entity.PaginatedResult
import usecase.movie.GetRatedMoviesUseCase
import usecase.tvshow.GetRatedTVShowsUseCase

class RatedTVShowsPagingSource(
    private val getRatedTv: GetRatedTVShowsUseCase
) : PagedResultPagingSource<TVShowUiState>() {

    override suspend fun fetchPage(page: Int): PaginatedResult<TVShowUiState> {
        val res = getRatedTv(page)
        return PaginatedResult(
            page = res.page,
            totalPages = res.totalPages,
            results = res.results.map { it.toTvUiStateFromRated() }
        )
    }
}

class RatedMoviesPagingSource(
    private val getRatedMovies: GetRatedMoviesUseCase
) : PagedResultPagingSource<MovieUiState>() {

    override suspend fun fetchPage(page: Int): PaginatedResult<MovieUiState> {
        val res = getRatedMovies(page)
        return PaginatedResult(
            page = res.page,
            totalPages = res.totalPages,
            results = res.results.map { it.toMovieUiStateFromRated() }
        )
    }
}