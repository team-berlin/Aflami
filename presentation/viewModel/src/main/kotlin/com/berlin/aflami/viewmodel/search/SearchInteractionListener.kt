package com.berlin.aflami.viewmodel.search


interface SearchInteractionListener {
    fun onBackClick()
    fun onSearchClick(query: CharSequence)
    fun onMovieClick(id: Int)
}