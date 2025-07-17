package com.berlin.aflami.viewmodel.searchactor

sealed class SearchByActorEffect {
    object NavigatedBack : SearchByActorEffect()
    data class NavigatedToMovieDetailsScreen(val movieId: Int) : SearchByActorEffect()
}