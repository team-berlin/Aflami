package com.berlin.aflami.viewmodel.search

import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchUiState(
    val searchQuery: String = "",
    val recentSearches: List<String> = emptyList(),
    val selectedTabOption: TabOption = TabOption.MOVIES,
    val movies: Flow<PagingData<MovieUIState>> = emptyFlow(),
    val tvShows: Flow<PagingData<TVShowUiState>> = emptyFlow(),
    val isDialogVisible: Boolean = false,
    val filterItemUiState: FilterItemUiState = FilterItemUiState(),
    val filterTrigger: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)