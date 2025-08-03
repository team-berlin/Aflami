package com.berlin.aflami.viewmodel.mediadetails.details

sealed interface MovieDetailsScreenEffect {
    object NavigateBack : MovieDetailsScreenEffect
    data class PlayMedia(val videoUrl: String) : MovieDetailsScreenEffect
    data class NavigateToShowAllCastScreen(val movieId: Long) : MovieDetailsScreenEffect

    data class NavigateToMovieDetailsScreen(val movieId: Long) :
        MovieDetailsScreenEffect

    data class ShowRatingDialog(val movieId: Long) : MovieDetailsScreenEffect
    data class ShowAddToFavoriteListDialog(
        val favouriteListId: Int,
        val movieId: Int,
    ) : MovieDetailsScreenEffect
}
