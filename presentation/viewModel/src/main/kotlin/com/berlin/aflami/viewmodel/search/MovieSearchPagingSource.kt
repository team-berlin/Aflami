package com.berlin.aflami.viewmodel.search

import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.mapper.toMovieUIState
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState
import usecase.movie.GetSearchMoviesUseCase
import kotlin.collections.filter

class MovieSearchPagingSource(
    private val searchMoviesUseCase: GetSearchMoviesUseCase,
    private val query: String,
    private val selectedRating: Float,
    private val selectedGenreId: Int,
) : BasePagingSource<MovieUIState>() {
    override suspend fun fetchData(page: Int): List<MovieUIState> {
        return searchMoviesUseCase(query, page)
            .map { it.toMovieUIState() }
            .filter { movieUiState ->
                val rating = convertArabicToEnglish(movieUiState.rating.replace('٫', '.'))
                    .toFloatOrNull() ?: return@filter false
                val matchesRating = rating > selectedRating
                val matchesGenre =
                    selectedGenreId == -1 || movieUiState.genre.any { it.id == selectedGenreId }
                matchesRating && matchesGenre
            }
    }
}
