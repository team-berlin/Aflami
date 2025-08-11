package com.berlin.aflami.viewmodel.categories.categories

import androidx.compose.runtime.Immutable
import com.berlin.aflami.viewmodel.shareduistate.GenreUiState
import com.berlin.aflami.viewmodel.search.TabOption

@Immutable
data class CategoriesScreenUiState (
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val selectedTabOption: TabOption = TabOption.MOVIES,
    val moviesGenres: List<GenreUiState> = emptyList(),
    val tvShowGenres: List<GenreUiState> = emptyList(),
    )
