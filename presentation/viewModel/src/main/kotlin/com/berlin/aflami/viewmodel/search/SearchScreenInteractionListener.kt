package com.berlin.aflami.viewmodel.search

import androidx.compose.ui.text.input.TextFieldValue


interface SearchScreenInteractionListener {
    fun onBackClicked()
    fun onFilterButtonClicked()
    fun onSearchActionClicked()
    fun onSearchQueryChanged(query: TextFieldValue)
    fun onWorldSearchCardClicked()
    fun onActorSearchCardClicked()
    fun onTabOptionClicked(tabOption: TabOption)
    fun onMoviesCardClicked(movieId: Long)
    fun onTVShowsCardClicked(tvShowId: Long)
    fun onItemClicked(query: TextFieldValue)
    fun onRecentSearchCleared(query: String)
    fun onAllRecentSearchesCleared()
    fun onSearchCleared()
}