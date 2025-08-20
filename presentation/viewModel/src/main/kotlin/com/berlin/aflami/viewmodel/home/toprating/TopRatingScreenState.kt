package com.berlin.aflami.viewmodel.home.toprating

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class TopRatingScreenState(
    val topRatedMediaFlow: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val isLoading: Boolean = true,
    val errorUiState: ErrorUiState? = null,
)