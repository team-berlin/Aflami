package com.berlin.aflami.viewmodel.categories.movie

import android.util.Log
import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.mapper.toMediaUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import usecase.movie.GetMoviesByCategoryUseCase
import usecase.tvshow.GetTVShowsByCategoryUseCase

class MoviesByCategoryPagingSource(
    private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase,
    private val selectedCategoryId: Long,
) : BasePagingSource<MediaUiState>() {
    override suspend fun fetchData(page: Int): List<MediaUiState> {
        return getMoviesByCategoryUseCase(selectedCategoryId, page).map { it.toMediaUiState() }
    }
}

class TVShowsByCategoryPagingSource(
    private val getTVShowsByCategoryUseCase: GetTVShowsByCategoryUseCase,
    private val selectedCategoryId: Long,
) : BasePagingSource<MediaUiState>() {
    override suspend fun fetchData(page: Int): List<MediaUiState> {
        return getTVShowsByCategoryUseCase(selectedCategoryId, page).map { it.toMediaUiState() }
    }
}
