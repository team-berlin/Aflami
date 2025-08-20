package com.berlin.aflami.viewmodel.profile.watchhistory

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class WatchHistoryUiState (
    val isLoading: Boolean = true,
    val errorUiState: ErrorUiState? = null,
    val selectedTabOption: TabOption = TabOption.MOVIES,
    val movies: Flow<PagingData<MovieUiState>> = emptyFlow(),
    val tvShows: Flow<PagingData<TVShowUiState>> = emptyFlow(),
)
