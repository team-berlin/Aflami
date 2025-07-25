package com.berlin.aflami.viewmodel.home

import androidx.lifecycle.viewModelScope
import android.util.Log
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.home.uistate.HomeUiState
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import kotlinx.coroutines.flow.update
import usecase.home.GetContinueWatchingMovieUseCase
import kotlinx.coroutines.launch
import usecase.GetSearchTvShowsUseCase
import usecase.GetTopRatedMovies
import usecase.GetTopRatedSeries

class HomeViewModel(
    private val getWatchedMovieUseCase: GetContinueWatchingMovieUseCase,
    private val getWatchedTVShowUseCase: GetContinueWatchingTVShowUseCase
): BaseViewModel<HomeUiState, HomeScreenEffect>(
    private val getTopRatedMovies: GetTopRatedMovies,
    private val getTopRatedSeries: GetTopRatedSeries,
) : BaseViewModel<HomeUiState, HomeScreenEffect>(
    HomeUiState()
), HomeInteractionListener {
    override fun onSearchClicked() {
    }

    override fun onShowAllContinueWatchingClicked() {
        sendNewEffect(HomeScreenEffect.NavigateToContinueWatching)
    }

    override fun onShowAllTopRating() {
        viewModelScope.launch {
            val topRatedMoviesAndSeries = getTopRatedMovies(1).plus(getTopRatedSeries(1))
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
            onSuccess = {continueWatchingMedia->
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