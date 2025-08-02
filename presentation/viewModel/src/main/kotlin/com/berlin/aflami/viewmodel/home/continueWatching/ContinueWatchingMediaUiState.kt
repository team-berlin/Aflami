package com.berlin.aflami.viewmodel.home.continueWatching

import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ContinueWatchingMediaUiState(
    val continueWatchingMediaFlow: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)