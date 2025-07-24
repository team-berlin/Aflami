package com.berlin.aflami.viewmodel.mediadetails


data class MovieDetailsTabsUiState(
    val tab: MovieDetailsTabs = MovieDetailsTabs.REVIEWS,
    val isSelected: Boolean = false,
)

enum class MovieDetailsTabs {
    MORE_LIKE_THIS,
    REVIEWS,
    GALLERY,
    COMPANY_PRODUCTION,
    SEASON
}