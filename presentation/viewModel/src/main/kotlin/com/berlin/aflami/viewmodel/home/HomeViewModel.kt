package com.berlin.aflami.viewmodel.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.home.uistate.HomeUiState
import com.berlin.aflami.viewmodel.home.uistate.PopularMediaUiState
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.GetPopularMoviesUseCase
import usecase.GetPopularTVShowsUseCase
import usecase.home.GetContinueWatchingMovieUseCase
import usecase.home.GetContinueWatchingTVShowUseCase

class HomeViewModel(
    private val getWatchedMovieUseCase: GetContinueWatchingMovieUseCase,
    private val getWatchedTVShowUseCase: GetContinueWatchingTVShowUseCase,
    private val popularMoviesUseCase: GetPopularMoviesUseCase,
    private val popularTVShowsUseCase: GetPopularTVShowsUseCase
) : BaseViewModel<HomeUiState, HomeScreenEffect>(
    HomeUiState()
), HomeInteractionListener {

    private val _movies = MutableStateFlow<List<MediaUiState>>(emptyList())
    private val _tvShows = MutableStateFlow<List<MediaUiState>>(emptyList())

    init {
        popularMedia("en-US")
    }

    private fun popularMedia(language: String) {

        updateState {
            it.copy(isLoading = true, error = null)
        }
        viewModelScope.launch {
            try {
                coroutineScope {
                    val movie = async { popularMoviesUseCase(language) }
                    val tvShow = async { popularTVShowsUseCase(language) }

                    val movieList = movie.await().map { it.toUIState() }
                    val tvShowList = tvShow.await().map { it.toUIState() }

                    _movies.value = movieList
                    _tvShows.value = tvShowList

                    combineMediaAndUpdateUi()
                }
            } catch (t: Throwable) {
                onPopularMediaError(t)
            }
        }
    }

    private fun combineMediaAndUpdateUi() {
        viewModelScope.launch {
            combine(_movies, _tvShows) { movieList, tvShowList ->

                val mergedList = mutableListOf<MediaUiState>()
                val maxSize = maxOf(movieList.size, tvShowList.size)

                for (i in 0 until maxSize) {
                    if (i < movieList.size) mergedList.add(movieList[i])
                    if (i < tvShowList.size) mergedList.add(tvShowList[i])
                }

                mergedList

            }.collect { combinedList ->
                Log.d("CombinedMediaList", "$combinedList")
                updateState {
                    it.copy(
                        isLoading = false,
                        popularMedia  = PopularMediaUiState(
                            popularMedia = combinedList
                        )
                    )
                }
            }
        }
    }


    private fun onPopularMediaError(throwable: Throwable) {
        _state.update { it.copy(error = throwable.message, isLoading = false) }
    }
    override fun onSearchClicked() {
        sendNewEffect(HomeScreenEffect.NavigateToSearch)
    }

    override fun onShowAllContinueWatchingClicked() {
        sendNewEffect(HomeScreenEffect.NavigateToContinueWatching)
    }

    override fun onShowAllTopRating() {
    }

    override fun onMoodPickerClicked() {
    }

    override fun onUpcomingClicked(genreId: Int) {
        TODO("Not yet implemented")
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