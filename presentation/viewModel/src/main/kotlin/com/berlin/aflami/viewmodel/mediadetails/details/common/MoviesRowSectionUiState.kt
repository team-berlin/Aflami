package com.berlin.aflami.viewmodel.mediadetails.details.common

import com.berlin.aflami.viewmodel.mediadetails.uistate.UiText
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState

sealed interface MoviesRowSectionUiState {
    object Loading : MoviesRowSectionUiState
    data class Success(val content: MoviesTabContent) : MoviesRowSectionUiState
    data class Error(val message: String? = null) : MoviesRowSectionUiState
    data class NoDataFound(val message: UiText) : MoviesRowSectionUiState
}

sealed interface MoviesTabContent {
    data class MoreLikeThis(val moreMoviesLikeThis: List<MovieUiState>) : MoviesTabContent
    data class Reviews(val movieReviews: List<ReviewUiState>) : MoviesTabContent
    data class Gallery(val images: List<String>) : MoviesTabContent
    data class CompanyProduction(val companyProductionsList: List<CompanyProductionUiState>) : MoviesTabContent
}