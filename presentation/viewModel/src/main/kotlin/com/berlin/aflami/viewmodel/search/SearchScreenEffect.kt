package com.berlin.aflami.viewmodel.search

sealed interface SearchScreenEffect {
    object NavigatedBack : SearchScreenEffect
    object NavigateToWorldSearchScreen : SearchScreenEffect
    object NavigateToActorSearchScreen : SearchScreenEffect
    data class NavigatedToMovieDetailsScreen(val id: Long, val mediaType: String) :
        SearchScreenEffect
}
