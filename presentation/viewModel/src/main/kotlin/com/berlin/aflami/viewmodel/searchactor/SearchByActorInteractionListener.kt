package com.berlin.aflami.viewmodel.searchactor

interface SearchByActorInteractionListener {
    fun onMovieClicked(movieId: Int, mediaType: String)
    fun onActorNameChanged(actorName: CharSequence)
    fun onBackClicked()
}