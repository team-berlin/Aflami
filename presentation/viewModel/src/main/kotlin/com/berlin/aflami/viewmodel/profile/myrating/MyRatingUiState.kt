package com.berlin.aflami.viewmodel.profile.myrating

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class MyRatingUiState (
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val selectedTabOption: TabOption = TabOption.MOVIES,
    val movies: Flow<PagingData<MovieUiState>> = emptyFlow(),
    val tvShows: Flow<PagingData<TVShowUiState>> = emptyFlow(),
)
