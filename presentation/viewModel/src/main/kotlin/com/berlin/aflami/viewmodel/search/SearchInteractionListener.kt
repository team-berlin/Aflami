package com.berlin.aflami.viewmodel.search


interface SearchInteractionListener {
    fun onBackClick()
    fun onQuerySearchChanged(query: CharSequence)
    fun onSearchClick()
    fun onClickMovie(id: Int)
}