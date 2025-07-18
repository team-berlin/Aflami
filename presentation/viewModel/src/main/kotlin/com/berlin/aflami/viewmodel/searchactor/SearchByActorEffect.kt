package com.berlin.aflami.viewmodel.searchactor

sealed class SearchByActorEffect {
    object NavigatedBack : SearchByActorEffect()
    data class NavigatedToMediaDetailsScreen(val movieId: Int, val mediaType: String) : SearchByActorEffect()
}