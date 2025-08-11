package com.berlin.aflami.viewmodel.details.movie

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.details.common.MoviesRowSectionUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.AddToListSheetState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.createnewlist.CreateNewListUiState
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState

data class MovieDetailsUiState(
    val isFavorite: Boolean = false,
    val posters: List<String> = emptyList(),
    val isMovieHasVideo: Boolean = false,
    val videoUrl: String = "",
    val movieUiState: MovieUiState = MovieUiState(),
    val isDescriptionExpanded: Boolean = false,
    val castList: List<ActorUiState> = emptyList(),
    val rowSection: MoviesRowSectionUiState = MoviesRowSectionUiState.Loading,
    val expandedReviewIds: Set<String> = emptySet(),
    val isScreenLoading: Boolean = false,
    val errorMessage: String? = null,
    val movieDetailsTabsUiState: MovieDetailsTabsUiState = MovieDetailsTabsUiState(),
    val showLoginDialog: Boolean = false,
    val showRatingDialog: Boolean = false,
    val addToListDialog: AddToListSheetState = AddToListSheetState(),
    val selectedRatingMediaId: Long? = null,
    val createNewListDialog: CreateNewListUiState = CreateNewListUiState(),
    val snackBar: SnackBarUiState = SnackBarUiState(),
)

@Immutable
data class SnackBarUiState(
    val isVisible: Boolean = false,
    val isOperationSucceeded: Boolean = false,
    val errorUiState: ErrorUiState = ErrorUiState(),
    val snackBarStatus: SNACK_BAR_STATUS? = null,
)

enum class SNACK_BAR_STATUS {
    ADD_MOVIE_TO_LIST,
    CREATE_NEW_LIST,
}


data class MovieDetailsTabsUiState(
    val tab: MovieDetailsTabs = MovieDetailsTabs.MORE_LIKE_THIS,
    val isSelected: Boolean = false,
)

enum class MovieDetailsTabs {
    MORE_LIKE_THIS,
    REVIEWS,
    GALLERY,
    COMPANY_PRODUCTION,
}


sealed class UiText {
    data class Dynamic(val value: String) : UiText()
    data class Resource(@StringRes val resId: Int) : UiText()
}