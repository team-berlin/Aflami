package com.berlin.aflami.viewmodel.details.series

sealed interface TvShowDetailsScreenEffect {
    object NavigateBack : TvShowDetailsScreenEffect
    data class PlayMedia(val videoUrl: String) : TvShowDetailsScreenEffect
    data class NavigateToShowAllCastScreen(val mediaId: Long) : TvShowDetailsScreenEffect
    data class NavigateToMediaDetailsScreen(val mediaId: Long) : TvShowDetailsScreenEffect

    data class ShowRatingDialog(val mediaId: Long) : TvShowDetailsScreenEffect
    data class ShowAddToFavoriteListDialog(
        val favouriteListId: Int,
        val mediaId: Int,
    ) : TvShowDetailsScreenEffect
}