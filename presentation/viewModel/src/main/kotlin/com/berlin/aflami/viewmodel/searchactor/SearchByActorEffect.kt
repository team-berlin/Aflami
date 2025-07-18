package com.berlin.aflami.viewmodel.searchactor

import com.berlin.aflami.viewmodel.search.SearchViewModel.MediaType

sealed class SearchByActorEffect {
    object NavigatedBack : SearchByActorEffect()
    data class NavigatedToMediaDetailsScreen(val movieId: Int, val mediaType: MediaType) : SearchByActorEffect()
}