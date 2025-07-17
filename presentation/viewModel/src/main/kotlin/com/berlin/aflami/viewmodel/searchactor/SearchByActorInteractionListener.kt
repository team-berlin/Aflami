package com.berlin.aflami.viewmodel.searchactor

interface SearchByActorInteractionListener {
    fun onMovieClicked(movieId: Int)
    fun onActorNameChanged(actorName: CharSequence)
    fun onBackClicked()
}