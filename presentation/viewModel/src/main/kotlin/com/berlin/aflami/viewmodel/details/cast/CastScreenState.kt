package com.berlin.aflami.viewmodel.details.cast

import androidx.compose.runtime.Immutable
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState

@Immutable
data class CastScreenState(
    val castList: List<ActorUiState> = emptyList(),
    val isScreenLoading: Boolean = false,
    val errorUiState: ErrorUiState? = null,
)
