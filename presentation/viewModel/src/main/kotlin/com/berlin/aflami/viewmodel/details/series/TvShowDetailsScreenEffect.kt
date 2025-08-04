package com.berlin.aflami.viewmodel.details.series

sealed interface TvShowDetailsScreenEffect {
    object NavigateBack : TvShowDetailsScreenEffect
    data class PlayMedia(val videoUrl: String) : TvShowDetailsScreenEffect
    data class NavigateToShowAllCastScreen(val tvShowId: Long) : TvShowDetailsScreenEffect
    data class NavigateToMediaDetailsScreen(val tvShowId: Long) : TvShowDetailsScreenEffect

    data class ShowRatingDialog(val tvShowId: Long) : TvShowDetailsScreenEffect
    data class ShowAddToFavoriteListDialog(
        val favouriteListId: Int,
        val tvShowId: Int,
    ) : TvShowDetailsScreenEffect
}