package com.berlin.aflami.viewmodel.shareduistate

import androidx.compose.runtime.Immutable

@Immutable
data class EpisodeUiState(
    val id: Long,
    val stillPath: String,
    val airDate: String,
    val episodeNumber: Int,
    val episodeType: String,
    val name: String,
    val overview: String,
    val duration: String?,
    val rating: Double,
)