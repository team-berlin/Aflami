package com.berlin.aflami.viewmodel.home

sealed interface HomeUiEffect {
    data class NavigatedToMovieDetailsScreen(val movieId: Int) : HomeUiEffect
}