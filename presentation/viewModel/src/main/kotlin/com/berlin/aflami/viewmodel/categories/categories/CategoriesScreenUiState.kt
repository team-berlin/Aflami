package com.berlin.aflami.viewmodel.categories.categories

import androidx.compose.runtime.Immutable
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.GenreUiState

@Immutable
data class CategoriesScreenUiState (
    val isLoading: Boolean = true,
    val errorUiState: ErrorUiState? = null,
    val selectedTabOption: TabOption = TabOption.MOVIES,
    val moviesGenres: List<GenreUiState> = emptyList(),
    val tvShowGenres: List<GenreUiState> = emptyList(),
    )
