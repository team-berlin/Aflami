package com.berlin.aflami.viewmodel.search

sealed interface SearchUiEffect {
    object NavigateToWorldSearch : SearchUiEffect
    object NavigateToActorSearch : SearchUiEffect
    object NavigatedBack : SearchUiEffect
    data class NavigatedToMovieDetailsScreen(val id: Int) : SearchUiEffect
}
