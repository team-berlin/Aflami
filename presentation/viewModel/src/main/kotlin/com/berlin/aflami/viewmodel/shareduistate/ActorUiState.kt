package com.berlin.aflami.viewmodel.shareduistate

import androidx.compose.runtime.Immutable

@Immutable
data class ActorUiState(
    val mediaId: Long = 0L,
    val name: String = "",
    val poster: String = "",
    val isLoading
)