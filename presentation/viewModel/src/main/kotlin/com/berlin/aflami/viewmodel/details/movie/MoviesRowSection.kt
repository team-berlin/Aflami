package com.berlin.aflami.viewmodel.details.movie

import com.berlin.aflami.viewmodel.details.common.CompanyProductionUiState
import com.berlin.aflami.viewmodel.details.common.ReviewUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState

sealed interface MoviesRowSectionUiState {
    object Loading : MoviesRowSectionUiState
    data class Success(val content: MoviesTabContent) : MoviesRowSectionUiState
    data class NoDataFound(val message: UiText) : MoviesRowSectionUiState
}

sealed interface MoviesTabContent {
    data class MoreLikeThis(val moreMoviesLikeThis: List<MovieUiState>) : MoviesTabContent
    data class Reviews(val movieReviews: List<ReviewUiState>) : MoviesTabContent
    data class Gallery(val images: List<String>) : MoviesTabContent
    data class CompanyProduction(val companyProductionsList: List<CompanyProductionUiState>) : MoviesTabContent
}