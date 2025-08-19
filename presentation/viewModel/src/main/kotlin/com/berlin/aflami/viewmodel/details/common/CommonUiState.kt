package com.berlin.aflami.viewmodel.details.common

import androidx.compose.runtime.Immutable
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.viewModel.R

@Immutable
data class ReviewUiState(
    val id: String = "",
    val name: String = "",
    val userName: String = "",
    val avatarImage: String?,
    val rating: Double = 0.0,
    val content: String = "",
    val date: String = "",
)

@Immutable
data class CompanyProductionUiState(
    val id: String = "",
    val image: String? = null,
    val name: String = "",
    val country: String = "",
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
    LIST_DELETED,
    LIST_RENAMED,
    RATING_ADDED,
}

fun <T> Set<T>.toggle(item: T): Set<T> =
    if (contains(item)) this - item else this + item

val NO_REVIEWS = R.string.there_is_no_reviews
val NO_GALLERY = R.string.there_is_no_gallery
val NO_MORE_MEDIA = R.string.there_is_no_more_media
val NO_COMPANY_PRODUCTION = R.string.there_is_no_company_production
val NO_SEASON= R.string.there_is_no_season
