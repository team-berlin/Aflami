package com.berlin.aflami.viewmodel.shareduistate

import androidx.compose.runtime.Immutable

@Immutable
data class MediaUiState(
    val id: Long = 0L,
    val mediaType: MediaType?,
    val title: String = "",
    val rating: String = "",
    val releaseYear: String = "",
    val companyProductionUiState: List<CompanyProductionUiState> = emptyList(),
    val genre: List<Int> = emptyList(),
    val poster: String = "",
)