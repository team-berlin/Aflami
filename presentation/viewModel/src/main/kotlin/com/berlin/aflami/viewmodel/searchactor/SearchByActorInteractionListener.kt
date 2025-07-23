package com.berlin.aflami.viewmodel.searchactor

import com.berlin.aflami.viewmodel.util.MediaType


interface SearchByActorInteractionListener {
    fun onMovieClicked(movieId: Long, mediaType: MediaType)
    fun onActorNameChanged(actorName: CharSequence)
    fun onBackClicked()
}