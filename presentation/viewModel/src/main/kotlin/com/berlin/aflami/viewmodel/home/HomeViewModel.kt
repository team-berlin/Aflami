package com.berlin.aflami.viewmodel.home

import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.home.uistate.HomeUiState
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.GetTopRatedMoviesUseCase
import usecase.GetTopRatedSeriesUseCase
import usecase.home.GetContinueWatchingMovieUseCase
import usecase.home.GetContinueWatchingTVShowUseCase

class HomeViewModel(
    private val getWatchedMovieUseCase: GetContinueWatchingMovieUseCase,
    private val getWatchedTVShowUseCase: GetContinueWatchingTVShowUseCase,
    private val getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
) : BaseViewModel<HomeUiState, HomeScreenEffect>(HomeUiState()), HomeInteractionListener {

    override fun onSearchClicked() {
    }

    override fun onShowAllContinueWatchingClicked() {
        sendNewEffect(HomeScreenEffect.NavigateToContinueWatching)
    }

    override fun onShowAllTopRating() {
        viewModelScope.launch {
            tryToCall(
                call = {
                    val topRatedMovies =
                        getTopRatedMoviesUseCase(1).map { movie -> movie.toUIStateMedia() }
                    val topRatedSeries =
                        getTopRatedSeriesUseCase(1).map { series -> series.toUIStateMedia() }
                    (topRatedMovies + topRatedSeries).sortedByDescending { it.rating }
                },
                onSuccess = { newTopRatedMedia ->
                    _state.update { oldState ->
                        oldState.copy(
                            topRatedMediaUiState = oldState.topRatedMediaUiState.copy(
                                topRatedMedia = newTopRatedMedia,
                                isLoading = false,
                                errorMessage = null,
                            )
                        )
                    }
                },
                onError = { errorUIState ->
                    _state.update { oldState ->
                        oldState.copy(
                            topRatedMediaUiState = oldState.topRatedMediaUiState.copy(
                                topRatedMedia = null,
                                isLoading = false,
                                errorMessage = errorUIState.message
                            )
                        )
                    }
                },
                dispatcher = Dispatchers.Default
            )
        }
    }

    override fun onMoodPickerClicked() {
    }

    override fun onUpcomingTabClicked(genreId: Int) {
    }

    override fun onUpComingMovieCardClick() {
    }

    fun getContinueWatchingMedia() {
        _state.update {
            it.copy(isLoading = true, error = null)
        }
        tryToCall(
            call = {
                val movies = getWatchedMovieUseCase().map { it.toUIStateMedia() }
                val tvShows = getWatchedTVShowUseCase().map { it.toUIStateMedia() }

                val combinedList = (movies + tvShows)
                    .shuffled()
                combinedList
            },
            onSuccess = { continueWatchingMedia ->
                _state.update {
                    it.copy(
                        mediaContinueWatching = continueWatchingMedia,
                        isLoading = false,
                    )
                }

            },
            onError = { throwable ->
                _state.update {
                    it.copy(
                        error = throwable.message
                    )
                }
            },
        )

    }
}