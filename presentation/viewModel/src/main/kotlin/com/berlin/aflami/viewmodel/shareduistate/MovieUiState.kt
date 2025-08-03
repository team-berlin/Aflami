package com.berlin.aflami.viewmodel.shareduistate

import com.berlin.aflami.viewmodel.mediadetails.details.common.CompanyProductionUiState
import com.berlin.entity.Genre

data class MovieUiState(
    val id: Long = 0L,
    val title: String = "",
    val rating: String = "",
    val releaseDate: String = "",
    val genre: List<Genre> = emptyList(),
    val companyProductionUiState: List<CompanyProductionUiState> = emptyList(),
    val posterUrl: String = "",
    val description: String = "",
    val duration: String = "",
    val originCountry: String = "",
)
