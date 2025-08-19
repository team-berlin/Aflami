package com.berlin.aflami.viewmodel.search

import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import usecase.tvshow.GetSearchTVShowsUseCase

class TVShowSearchPagingSource(
    private val searchTVShowsUseCase: GetSearchTVShowsUseCase,
    private val tvShowNameQuery: String,
    private val selectedRating: Float,
    private val selectedGenreId: Int,
) : BasePagingSource<TVShowUiState>() {
    override suspend fun fetchData(page: Int): List<TVShowUiState> {
        return searchTVShowsUseCase(tvShowNameQuery, page)
            .map { it.toUiState() }
            .filter { tvShowUiState ->
                val rating = parseRating(tvShowUiState.rating.replace('٫', '.'))
                    .toFloatOrNull() ?: return@filter false
                val matchesRating = rating > selectedRating
                val matchesGenre =
                    selectedGenreId == -1 || tvShowUiState.genre.any { it.id == selectedGenreId }
                matchesRating && matchesGenre
            }
    }
}