package com.berlin.aflami.viewmodel.mediadetails

import com.berlin.aflami.viewmodel.uistate.EpisodesUiState
import com.berlin.aflami.viewmodel.uistate.MediaUiState
import com.berlin.aflami.viewmodel.uistate.ReviewUiState

sealed class RowSectionUiState {
    object Loading : RowSectionUiState()
    data class Success(val content: TabContent) : RowSectionUiState()
    data class Error(val message: String) : RowSectionUiState()
}

sealed class TabContent {
    data class MoreLikeThis(val items: List<MediaUiState>) : TabContent()
    data class Reviews(val items: List<ReviewUiState>) : TabContent()
    data class Gallery(val items: List<String>) : TabContent()
    data class Season(val items: MutableMap<Int, List<EpisodesUiState>>) : TabContent()
    data class CompanyProduction(val items: List<CompanyProductionItem>) : TabContent()
}


data class CompanyProductionItem(
    val id: String = "",
    val image: String? = null,
    val name: String = "",
    val country: String = "",
)