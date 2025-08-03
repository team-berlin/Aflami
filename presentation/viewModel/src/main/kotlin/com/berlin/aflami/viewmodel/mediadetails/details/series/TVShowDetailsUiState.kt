package com.berlin.aflami.viewmodel.mediadetails.details.series

import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState

data class TVShowDetailsUiState(
    val isFavorite: Boolean = false,
    val isPlaying: Boolean = false,
    val tvShowUiState: TVShowUiState = TVShowUiState(),
    val isDescriptionExpanded: Boolean = false,
    val castList: List<ActorUiState> = emptyList(),
    val rowSection: TVShowRowSectionUiState = TVShowRowSectionUiState.Loading,
    val expandedReviewIds: Set<String> = emptySet(),
    val isScreenLoading: Boolean = false,
    val errorMessage: String = "",
    val tvShowDetailsTabsUiState: TVShowDetailsTabsUiState = TVShowDetailsTabsUiState(),
)

data class TVShowDetailsTabsUiState(
    val tab: TVShowDetailsTabs = TVShowDetailsTabs.MORE_LIKE_THIS,
    val isSelected: Boolean = false,
)
enum class TVShowDetailsTabs {
    MORE_LIKE_THIS,
    REVIEWS,
    SEASONS,
    GALLERY,
    COMPANY_PRODUCTION,
}