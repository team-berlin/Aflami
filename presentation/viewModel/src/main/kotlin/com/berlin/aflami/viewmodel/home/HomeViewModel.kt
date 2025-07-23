package com.berlin.aflami.viewmodel.home

import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.home.uistate.HomeUiState
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaDetailsUiState
import kotlinx.coroutines.flow.update
import usecase.home.GetWatchedMediaUseCase

class HomeViewModel(
    private val getWatchedMediaUseCase: GetWatchedMediaUseCase

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

    fun getContinueWatching(){
        tryToCall(
            call = {
              getWatchedMediaUseCase().map {continueWatching->
                  continueWatching.toUIState()
              }
            },
            onSuccess = {
                _state.update { homeState->
                    homeState.copy(
                        mediaContinueWatching = it
                    )
                }

            },
            onError = {throwable ->
                _state.update {homeState->
                    homeState.copy(
                        error = throwable.message
                    )
                }
            },
        )
    }

}