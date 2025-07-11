package com.berlin.aflami.viewmodel.search_actor

interface SearchByActorInteractionListener {
    fun onBackClick()
    fun onActorNameChanged(actorName: CharSequence)
    fun onSearchClick()
    fun onClickMovie(id: Int)
}