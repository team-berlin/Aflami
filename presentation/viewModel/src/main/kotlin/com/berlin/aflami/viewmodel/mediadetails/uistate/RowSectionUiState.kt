package com.berlin.aflami.viewmodel.mediadetails.uistate

import com.berlin.aflami.viewmodel.shareduistate.MediaUiState

sealed class RowSectionUiState {
    object Loading : RowSectionUiState()
    data class Success(val content: TabContent) : RowSectionUiState()
    data class Error(val message: String) : RowSectionUiState()
    data class NoDataFound(val message: String) : RowSectionUiState()
}

sealed class TabContent {
    data class MoreLikeThis(val items: List<MediaUiState>) : TabContent()
    data class Reviews(val items: List<ReviewUiState>) : TabContent()
    data class Gallery(val items: List<String>) : TabContent()
    data class Season(val items: MutableMap<Int, List<EpisodesUiState>>) : TabContent()
    data class CompanyProduction(val items: List<CompanyProductionUiState>) : TabContent()
}

