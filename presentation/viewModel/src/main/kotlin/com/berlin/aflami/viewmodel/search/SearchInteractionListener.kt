package com.berlin.aflami.viewmodel.search


interface SearchInteractionListener {
    fun onFilterButtonClicked()
    fun onSearchActionClicked()
    fun onSearchQueryChanged(query: String)
    fun onBackClicked()
    fun onWorldSearchCardClicked()
    fun onActorSearchCardClicked()
    fun onTabOptionClicked(tabOption: TabOption)
    fun onCardClicked(id: Int)
    fun onRecentSearchClicked(query: String)
    fun onRecentSearchCleared(query: String)
    fun onAllRecentSearchesCleared()
    fun onSearchCleared()
}