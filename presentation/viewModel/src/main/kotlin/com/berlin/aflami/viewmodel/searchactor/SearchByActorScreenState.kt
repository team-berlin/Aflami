package com.berlin.aflami.viewmodel.searchactor

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class SearchByActorScreenState(
    val isLoading: Boolean = false,
    val actorName: TextFieldValue = TextFieldValue(""),
    val mediaPagingDataFlow: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val errorMessage: String? = null,
)