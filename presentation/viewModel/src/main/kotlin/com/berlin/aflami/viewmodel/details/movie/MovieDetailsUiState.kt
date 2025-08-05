package com.berlin.aflami.viewmodel.details.movie

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.painter.Painter
import com.berlin.aflami.viewmodel.details.common.MoviesRowSectionUiState
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import kotlinx.datetime.LocalDate

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
    val showLoginDialog:Boolean = false,
    val showRatingDialog: Boolean = false,
    val showAddToListDialog: Boolean = false,
    val selectedRatingMediaId: Long? = null,
    val selectedAddToListMediaId: Long? = null,
    val selectedFavouriteListId: Int? = null
)


fun String.toLocalDate1(): LocalDate? {
    return try {
        LocalDate.parse(this)
    } catch (e: Exception) {
        null
    }
}

data class MediaOptions(
    val isSelected: Boolean,
    val title: String,
    val image: Painter,
)

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