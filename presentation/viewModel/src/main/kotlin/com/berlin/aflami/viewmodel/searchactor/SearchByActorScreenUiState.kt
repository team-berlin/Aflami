package com.berlin.aflami.viewmodel.searchactor

import androidx.compose.ui.text.input.TextFieldValue
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchByActorScreenUiState(
    val isLoading: Boolean = false,
    val query: TextFieldValue = TextFieldValue(""),
    val movies: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val error: String? = null
)
