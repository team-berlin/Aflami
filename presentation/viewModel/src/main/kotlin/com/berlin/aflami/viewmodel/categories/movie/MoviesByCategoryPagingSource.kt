package com.berlin.aflami.viewmodel.categories.movie

import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.mapper.toMovieUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import usecase.movie.GetMoviesByCategoryUseCase

class MoviesByCategoryPagingSource(
    private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase,
    private val selectedCategoryId: Long,
) : BasePagingSource<MovieUiState>() {
    override suspend fun fetchData(page: Int): List<MovieUiState> {
        return getMoviesByCategoryUseCase(selectedCategoryId, page).map { it.toMovieUiState() }
    }
}

