package com.berlin.aflami.viewmodel.listDetails

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class ListDetailsScreenState(
    val listItems: Flow<PagingData<MovieUiState>> = emptyFlow(),
    val showDeleteListDialog: Boolean = false,
    val isScreenLoading: Boolean = false,
    val errorMessage: String? = null,
)
