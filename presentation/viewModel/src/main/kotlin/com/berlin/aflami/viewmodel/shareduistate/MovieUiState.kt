package com.berlin.aflami.viewmodel.shareduistate

import com.berlin.aflami.viewmodel.details.common.CompanyProductionUiState

data class MovieUiState(
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
    val hasVideo: Boolean = false,
    val isFavourite: Boolean = false,
)