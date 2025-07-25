package com.berlin.aflami.viewmodel.mediadetails.uistate

import androidx.annotation.StringRes
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState

sealed class RowSectionUiState {
    object Loading : RowSectionUiState()
    data class Success(val content: TabContent) : RowSectionUiState()
    data class Error(val message: String? = null) : RowSectionUiState()
    data class NoDataFound(val message: UiText) : RowSectionUiState()
}

sealed class TabContent {
    data class MoreLikeThis(val items: List<MediaUiState>) : TabContent()
    data class Reviews(val items: List<ReviewUiState>) : TabContent()
    data class Gallery(val items: List<String>) : TabContent()
    data class Season(val items: MutableMap<Int, List<EpisodesUiState>>) : TabContent()
    data class CompanyProduction(val items: List<CompanyProductionUiState>) : TabContent()
}


sealed class UiText {
    data class Dynamic(val value: String) : UiText()
    data class Resource(@StringRes val resId: Int) : UiText()
}