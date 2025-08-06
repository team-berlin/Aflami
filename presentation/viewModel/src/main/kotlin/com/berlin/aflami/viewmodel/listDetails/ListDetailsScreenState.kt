package com.berlin.aflami.viewmodel.listDetails

//data class ListDetailsScreenState()
import androidx.compose.runtime.Immutable
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState

@Immutable
data class ListDetailsScreenState(
    val mediaUiState: List<MediaUiState> = emptyList(),
    val isScreenLoading: Boolean = false,
    val errorMessage: String? = null,
)
