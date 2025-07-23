package com.berlin.aflami.viewmodel.searchactor


sealed class SearchByActorEffect {
    object NavigatedBack : SearchByActorEffect()
    data class NavigatedToMediaDetailsScreen(val movieId: Long, val mediaType: String) : SearchByActorEffect()
}