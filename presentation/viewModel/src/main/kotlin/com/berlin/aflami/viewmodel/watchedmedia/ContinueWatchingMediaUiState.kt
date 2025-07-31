package com.berlin.aflami.viewmodel.watchedmedia

import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ContinueWatchingMediaUiState(
    val continueWatchingItems: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val isLoading: Boolean = true,
    val error: String? = null,
)