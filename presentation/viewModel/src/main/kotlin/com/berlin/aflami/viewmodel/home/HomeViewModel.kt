package com.berlin.aflami.viewmodel.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.uistate.MediaUiState
import com.berlin.aflami.viewmodel.util.MediaType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.GetPopularMoviesUseCase
import usecase.GetPopularTVShowsUseCase

class HomeViewModel(
    private val popularMoviesUseCase: GetPopularMoviesUseCase,
    private val popularTVShowsUseCase: GetPopularTVShowsUseCase
) : BaseViewModel<PopularMediaUiState, HomeScreenEffect>(PopularMediaUiState()),
    HomeInteractionListener {

    private val _movies = MutableStateFlow(MediaUiState())
    private val _tvShows = MutableStateFlow(MediaUiState())

    init {
        popularMedia("en-US")
        combineMediaAndUpdateUi()
    }

    fun popularMedia(language: String) {

        updateState {
            it.copy(isLoading = true, error = null)
        }

        tryToCall(
            call = { popularMoviesUseCase(language) },
            onSuccess = { movieList ->
                val firstMovie = movieList.firstOrNull()?.let {
                    _movies.value = it.toUIState(MediaType.MOVIE)
                }
            },
            onError = ::onPopularMediaError,
        )

        tryToCall(
            call = { popularTVShowsUseCase(language) },
            onSuccess = { tvShowList ->
                val firstTVShow = tvShowList.firstOrNull()?.let {
                    _tvShows.value = it.toUIState(MediaType.TV_SHOW)
                }
            },
            onError = ::onPopularMediaError,
        )
    }

    private fun combineMediaAndUpdateUi() {
        viewModelScope.launch {
            combine(_movies, _tvShows) { movie, tvShow ->
                listOf(movie, tvShow)
            }.collect { combinedList ->
                Log.d("CombinedMediaList" , "$combinedList")
                updateState {
                    it.copy(
                        isLoading = false,
                        popularMedia = combinedList
                    )
                }
            }
        }
    }


    private fun onPopularMediaError(throwable: Throwable) {
        _state.update { it.copy(error = throwable.message, isLoading = false) }
    }

    override fun onSearchClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowAllContinueWatchingClicked() {
        TODO("Not yet implemented")
    }

    override fun onShowAllTopRating() {
        TODO("Not yet implemented")
    }

    override fun onMoodPickerClicked() {
        TODO("Not yet implemented")
    }

    override fun onUpcomingClicked(genreId: Int) {
        TODO("Not yet implemented")
    }

    override fun onUpComingMovieCardClick() {
        TODO("Not yet implemented")
    }
}