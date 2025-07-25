package com.berlin.aflami.viewmodel.home

import android.util.Log
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.home.uistate.HomeUiState
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.update
import usecase.home.GetContinueWatchingMovieUseCase
import usecase.home.GetContinueWatchingTVShowUseCase

class HomeViewModel(
    private val getWatchedMovieUseCase: GetContinueWatchingMovieUseCase,
    private val getWatchedTVShowUseCase: GetContinueWatchingTVShowUseCase
): BaseViewModel<HomeUiState, HomeScreenEffect>(
    HomeUiState()
),HomeInteractionListener{

    override fun onSearchClicked() {
    }

    override fun onShowAllContinueWatchingClicked() {
        sendNewEffect(HomeScreenEffect.NavigateToContinueWatching)
    }

    override fun onShowAllTopRating() {
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
                coroutineScope {
                    val moviesList = async {  getWatchedMovieUseCase().map { it.toUIStateMedia() }}
                    val tvShowsList = async { getWatchedTVShowUseCase().map { it.toUIStateMedia() }}

                    val movies = moviesList.await()
                    val tvShows = tvShowsList.await()

                    val combinedList = (movies + tvShows)
                        .shuffled()
                    combinedList
                }
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