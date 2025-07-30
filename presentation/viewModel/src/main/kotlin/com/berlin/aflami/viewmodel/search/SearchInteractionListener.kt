package com.berlin.aflami.viewmodel.search

import androidx.compose.ui.text.input.TextFieldValue


interface SearchInteractionListener {
    fun onFilterButtonClicked()
    fun onSearchActionClicked()
    fun onSearchQueryChanged(query: TextFieldValue)
    fun onBackClicked()
    fun onWorldSearchCardClicked()
    fun onActorSearchCardClicked()
    fun onTabOptionClicked(tabOption: TabOption)
    fun onCardClicked(id: Long)
    fun onRecentSearchClicked(query: String)
    fun onRecentSearchCleared(query: String)
    fun onAllRecentSearchesCleared()
    fun onSearchCleared()
}