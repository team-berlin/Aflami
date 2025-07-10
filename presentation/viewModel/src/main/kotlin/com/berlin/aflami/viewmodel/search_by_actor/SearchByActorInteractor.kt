package com.berlin.aflami.viewmodel.search_by_actor

interface SearchByNameInteractor {
    fun onBackClick()
    fun onActorNameChanged(actorName: CharSequence)
    fun onSearchClick()
    fun onClickMovie(id: Int)
}