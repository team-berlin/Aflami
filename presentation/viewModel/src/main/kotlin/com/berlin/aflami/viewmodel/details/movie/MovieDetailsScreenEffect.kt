package com.berlin.aflami.viewmodel.details.movie

sealed interface MovieDetailsScreenEffect {
    object NavigateBack : MovieDetailsScreenEffect
    data class PlayMedia(val videoUrl: String) : MovieDetailsScreenEffect
    data class NavigateToShowAllCastScreen(val movieId: Long) : MovieDetailsScreenEffect

    data class NavigateToMovieDetailsScreen(val movieId: Long) :
        MovieDetailsScreenEffect

    data class ShowRatingDialog(val movieId: Long) : MovieDetailsScreenEffect


    data object NavigateToLogin : MovieDetailsScreenEffect
//    data class ShowAddToFavouriteSnackBar(val isAddedSuccessfully: Boolean) :
//        MovieDetailsScreenEffect
//
//    data class ShowCreateNewListSnackBar(val isListCreatedSuccessfully: Boolean) :
//        MovieDetailsScreenEffect


}
