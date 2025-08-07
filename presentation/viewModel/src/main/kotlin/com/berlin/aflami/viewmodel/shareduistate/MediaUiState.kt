package com.berlin.aflami.viewmodel.shareduistate

import androidx.compose.runtime.Immutable
import com.berlin.aflami.viewmodel.details.common.CompanyProductionUiState

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
    val isFavourite: Boolean = false,
    val userRate: Double = 0.0,
)