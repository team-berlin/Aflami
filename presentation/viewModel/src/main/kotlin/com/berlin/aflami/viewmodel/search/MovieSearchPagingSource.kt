package com.berlin.aflami.viewmodel.search

import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.mapper.toMovieUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import usecase.movie.GetSearchMoviesUseCase

class MovieSearchPagingSource(
    private val searchMoviesUseCase: GetSearchMoviesUseCase,
    private val query: String,
    private val selectedRating: Float,
    private val selectedGenreId: Int,
) : BasePagingSource<MovieUiState>() {
    override suspend fun fetchData(page: Int): List<MovieUiState> {
        return searchMoviesUseCase(query, page)
            .map { it.toMovieUiState() }
            .filter { movieUiState ->
                val rating = parseRating(movieUiState.rating.replace('٫', '.'))
                    .toFloatOrNull() ?: return@filter false
                val matchesRating = rating >= selectedRating
                val matchesGenre =
                    selectedGenreId == -1 || movieUiState.genre.any { it.id == selectedGenreId }
                matchesRating && matchesGenre
            }
    }
}
