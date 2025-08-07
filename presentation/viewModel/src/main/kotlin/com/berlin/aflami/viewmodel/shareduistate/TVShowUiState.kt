package com.berlin.aflami.viewmodel.shareduistate

import androidx.compose.runtime.Immutable
import com.berlin.aflami.viewmodel.details.common.CompanyProductionUiState

@Immutable
data class TVShowUiState(
    val id: Long = 0L,
    val title: String = "",
    val rating: String = "",
    val releaseDate: String = "",
    val genre: List<GenreUiState> = emptyList(),
    val companyProductionUiState: List<CompanyProductionUiState> = emptyList(),
    val posterUrl: String = "",
    val description: String = "",
    val duration: String = "",
    val originCountry: String = "",
    val numberOfSeasons: Int = 0,
    val hasVideo:Boolean = false
)
