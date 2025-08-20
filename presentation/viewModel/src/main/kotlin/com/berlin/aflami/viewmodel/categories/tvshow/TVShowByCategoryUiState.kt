package com.berlin.aflami.viewmodel.categories.tvshow

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class TVShowByCategoryUiState(
    val isLoading: Boolean = true,
    val errorUiState: ErrorUiState? = null,
    val tvShowGenres: List<GenreUiState> = emptyList(),
    val tvShowsPagingDataFlow: Flow<PagingData<TVShowUiState>> = emptyFlow(),
    val selectedCategoryId: Long = -1L,
)
