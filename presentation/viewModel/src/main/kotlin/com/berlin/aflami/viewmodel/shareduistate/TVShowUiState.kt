package com.berlin.aflami.viewmodel.shareduistate

import androidx.compose.runtime.Immutable
import com.berlin.entity.Genre

@Immutable
data class TVShowUiState(
    val id: Long = 0L,
    val title: String = "",
    val rating: String = "",
    val releaseDate: String = "",
    val genre: List<Genre> = emptyList(),
    val companyProductionUiState: List<CompanyProductionUiState> = emptyList(),
    val posterUrl: String = "",
    val description: String = "",
    val duration: Int = -1,
    val hasVideo: Boolean = false,
    val originCountry: String = "",
    val galleryUrls: List<String> = emptyList(),
    val numberOfSeasons: Int = 0
)
