package com.berlin.aflami.viewmodel.searchactor

import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchByActorScreenUiState(
    val isLoading: Boolean = false,
    val query: String = "",
    val movies: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val error: String? = null
)
