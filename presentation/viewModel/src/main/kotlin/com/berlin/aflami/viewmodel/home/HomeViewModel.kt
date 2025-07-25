package com.berlin.aflami.viewmodel.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.uistate.MediaUiState
import com.berlin.aflami.viewmodel.util.MediaType
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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

    private val _movies = MutableStateFlow<List<MediaUiState>>(emptyList())
    private val _tvShows = MutableStateFlow<List<MediaUiState>>(emptyList())

    init {
        popularMedia("en-US")
    }

    fun popularMedia(language: String) {

        updateState {
            it.copy(isLoading = true, error = null)
        }
        viewModelScope.launch {
            try {
                coroutineScope {
                    val movie = async { popularMoviesUseCase(language) }
                    val tvShow = async { popularTVShowsUseCase(language) }

                    val movieList = movie.await().map { it.toUIState(MediaType.MOVIE) }
                    val tvShowList = tvShow.await().map { it.toUIState(MediaType.TV_SHOW) }

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
                var movieIndex = 0
                var tvShowIndex = 0

                while (movieIndex < movieList.size && tvShowIndex < tvShowList.size) {
                    mergedList.add(movieList[movieIndex])
                    movieIndex++
                    mergedList.add(tvShowList[tvShowIndex])
                    tvShowIndex++
                }

                while (movieIndex < movieList.size) {
                    mergedList.add(movieList[movieIndex])
                    movieIndex++
                }

                while (tvShowIndex < tvShowList.size) {
                    mergedList.add(tvShowList[tvShowIndex])
                    tvShowIndex++
                }

                mergedList
            }.collect { combinedList ->
                Log.d("CombinedMediaList", "$combinedList")
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