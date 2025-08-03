package com.berlin.aflami.viewmodel.mediadetails.details.common

import com.berlin.aflami.viewmodel.mediadetails.uistate.UiText
import com.berlin.aflami.viewmodel.shareduistate.CompanyProductionUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState

sealed interface MoviesRowSectionUiState {
    object Loading : MoviesRowSectionUiState
    data class Success(val content: MoviesTabContent) : MoviesRowSectionUiState
    data class Error(val message: String? = null) : MoviesRowSectionUiState
    data class NoDataFound(val message: UiText) : MoviesRowSectionUiState
}

sealed interface MoviesTabContent {
    data class MoreLikeThis(val items: List<MovieUIState>) : MoviesTabContent
    data class Reviews(val items: List<ReviewUiState>) : MoviesTabContent
    data class Gallery(val items: List<String>) : MoviesTabContent
    data class CompanyProduction(val items: List<CompanyProductionUiState>) : MoviesTabContent
}
