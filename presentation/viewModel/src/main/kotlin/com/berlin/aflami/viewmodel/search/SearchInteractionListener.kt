package com.berlin.aflami.viewmodel.search


interface SearchInteractionListener {
    fun onBackClick()
    fun onQuerySearchChange(query: CharSequence)
    fun onSearchClick()
    fun onMovieClick(id: Int)
}