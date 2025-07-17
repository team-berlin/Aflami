package com.berlin.aflami.viewmodel.mediadetails

import com.berlin.aflami.viewmodel.review.ReviewUiState


sealed class RowSectionUiState {
    object Loading : RowSectionUiState()
    data class Success(val content: TabContent) : RowSectionUiState()
    data class Error(val message: String) : RowSectionUiState()
}

sealed class TabContent {
    data class Reviews(val items: List<ReviewUiState>) : TabContent()
    // todo list the rest items here and remove all the old approach uistates
}
