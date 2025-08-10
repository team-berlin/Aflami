package com.berlin.aflami.viewmodel.details.series

import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState

data class TVShowDetailsUiState(
    val isFavorite: Boolean = false,
    val posters: List<String> = emptyList(),
    val isTVShowHasVideo: Boolean = false,
    val videoUrl: String = "",
    val tvShowUiState: TVShowUiState = TVShowUiState(),
    val isDescriptionExpanded: Boolean = false,
    val castList: List<ActorUiState> = emptyList(),
    val rowSection: TVShowRowSectionUiState = TVShowRowSectionUiState.Loading,
    val expandedReviewIds: Set<String> = emptySet(),
    val isScreenLoading: Boolean = false,
    val errorMessage: String? = null,
    val tvShowDetailsTabsUiState: TVShowDetailsTabsUiState = TVShowDetailsTabsUiState(),
    val showLoginDialog: Boolean = false,
    val showRatingDialog: Boolean = false,
    val showAddToListDialog: Boolean = false,
    val selectedRatingMediaId: Long? = null,
    val selectedAddToListMediaId: Long? = null,
    val selectedFavouriteListId: Int? = null,
    val sorryNotSupportedFeatureForTvShowVisible: Boolean = false,
)

data class TVShowDetailsTabsUiState(
    val tab: TVShowDetailsTabs = TVShowDetailsTabs.SEASONS,
    val isSelected: Boolean = false,
)
enum class TVShowDetailsTabs {
    SEASONS,
    MORE_LIKE_THIS,
    REVIEWS,
    GALLERY,
    COMPANY_PRODUCTION,
}