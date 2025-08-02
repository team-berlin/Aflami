package com.berlin.aflami.viewmodel.searchactor

import com.berlin.aflami.viewmodel.shareduistate.MediaType

sealed class SearchByActorScreenEffect {
    object NavigatedBack : SearchByActorScreenEffect()
    data class NavigatedToMediaDetailsScreen(val movieId: Long, val mediaType: MediaType) :
        SearchByActorScreenEffect()
}