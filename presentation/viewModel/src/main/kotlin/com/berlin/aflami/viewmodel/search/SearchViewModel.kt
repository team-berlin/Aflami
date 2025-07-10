package com.berlin.aflami.viewmodel.search

import androidx.lifecycle.ViewModel
import com.berlin.aflami.viewmodel.search_world_tour.WorldTourUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase

class SearchViewModel(
    private val searchMoviesUseCase: GetSearchMoviesUseCase,
    private val searchTvShowsUseCase: GetSearchTvShowsUseCase
) : ViewModel() {
    private val _tvShowUiState = MutableStateFlow(TvShowUiState())
    val tvShowUiState = _tvShowUiState.asStateFlow()

    private val _moviesUiState = MutableStateFlow(MoviesUiState())
    val movieUiState = _moviesUiState.asStateFlow()




}