package com.berlin.aflami.viewmodel.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase

class SearchViewModel(
    private val searchMoviesUseCase: GetSearchMoviesUseCase,
    private val searchTvShowsUseCase: GetSearchTvShowsUseCase
) : ViewModel(), SearchInteractor {
    private val _tvShowUiState = MutableStateFlow(SearchTvShowUiState())
    val tvShowUiState = _tvShowUiState.asStateFlow()

    private val _moviesUiState = MutableStateFlow(SearchMoviesUiState())
    val movieUiState = _moviesUiState.asStateFlow()


    override fun onBackClick() {
        TODO("Not yet implemented")
    }

    override fun onQuerySearchChanged(query: CharSequence) {
        TODO("Not yet implemented")
    }

    override fun onSearchClick() {
    }

    override fun onClickMovie(id: Int) {
        TODO("Not yet implemented")
    }
}