package com.berlin.aflami.viewmodel.search_actor

interface SearchByActorInteractionListener {
    fun onBackClicked()
    fun onActorNameChanged(actorName: CharSequence)
    fun onSearchClicked()
    fun onMovieClicked(id: Int)
}