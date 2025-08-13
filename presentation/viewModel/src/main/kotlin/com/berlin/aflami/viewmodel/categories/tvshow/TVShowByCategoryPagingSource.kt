package com.berlin.aflami.viewmodel.categories.tvshow

import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import usecase.tvshow.GetTVShowsByCategoryUseCase

class TVShowsByCategoryPagingSource(
    private val getTVShowsByCategoryUseCase: GetTVShowsByCategoryUseCase,
    private val selectedCategoryId: Long,
) : BasePagingSource<TVShowUiState>() {
    override suspend fun fetchData(page: Int): List<TVShowUiState> {
        return getTVShowsByCategoryUseCase(selectedCategoryId, page).map { it.toUiState() }
    }
}