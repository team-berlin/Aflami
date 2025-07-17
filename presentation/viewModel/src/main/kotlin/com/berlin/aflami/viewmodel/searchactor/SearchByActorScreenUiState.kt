package com.berlin.aflami.viewmodel.searchactor

import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchByActorScreenUiState(
    val isLoading: Boolean = false,
    val query: String = "",
    val movies: Flow<PagingData<MovieUIState>> = emptyFlow(),
    val error: String? = null
)
