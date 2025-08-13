package com.berlin.aflami.viewmodel.categories.movie

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class MoviesByCategoryUiState(
    val isScreenLoading: Boolean = true,
    val errorMessage: String? = null,
    val moviesGenres: List<GenreUiState> = emptyList(),
    val moviesPagingDataFlow: Flow<PagingData<MovieUiState>> = emptyFlow(),
    val selectedCategoryId: Long = -1L,
)
