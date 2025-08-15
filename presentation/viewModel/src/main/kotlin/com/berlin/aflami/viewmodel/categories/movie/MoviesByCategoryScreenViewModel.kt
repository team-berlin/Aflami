package com.berlin.aflami.viewmodel.categories.movie

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import usecase.movie.GetMovieGenresUseCase
import usecase.movie.GetMoviesByCategoryUseCase
import javax.inject.Inject

@HiltViewModel
class MoviesByCategoryScreenViewModel @Inject constructor(
    private val getMovieByGenresUseCase: GetMoviesByCategoryUseCase,
    private val getMoviesGenreUseCase: GetMovieGenresUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    mediaByCategoriesArgs: MediaByCategoryArgs
) : BaseViewModel<MoviesByCategoryUiState, MediaByCategoryScreenEffect>(
    MoviesByCategoryUiState()
), MediaByCategoryInteractionListener {
    private val categoryId = mediaByCategoriesArgs.categoryId ?: 0

    init {
        updateState { screenState ->
            screenState.copy(
                selectedCategoryId = categoryId,
            )
        }
        observeCategorySelection()
    }

    fun observeCategorySelection() {

        viewModelScope.launch {
            state.map { state -> state.selectedCategoryId }.distinctUntilChanged()
                .collectLatest { genre ->
                    loadGenresMovies()
                    getMoviesByCategory()
                }
        }
    }

    override fun onBackClicked() {
        sendNewEffect(MediaByCategoryScreenEffect.NavigateBack)
    }

    override fun onMediaCardClicked(mediaId: Long) = sendNewEffect(
        MediaByCategoryScreenEffect.NavigateToMediaDetails(
            mediaId,
        )
    )

    private fun getMoviesByCategory() {

        tryToCall(
            call = {
                getMoviesByCategoryAsFlow(
                    getMovieByGenresUseCase, state.value.selectedCategoryId
                )
            }, onSuccess = ::updateScreenStateWithMovies, onError = ::updateScreenStateToError
        )
    }


    private fun getMoviesByCategoryAsFlow(
        getMovieGenresUseCase: GetMoviesByCategoryUseCase, movieCategoryId: Long
    ): Flow<PagingData<MovieUiState>> {
        return Pager(
            config = defaultPageConfigurations(), pagingSourceFactory = {
                MoviesByCategoryPagingSource(
                    getMovieGenresUseCase, movieCategoryId
                )
            }).flow
    }

    private fun updateScreenStateWithMovies(topRatingMediaFlow: Flow<PagingData<MovieUiState>>) {

        updateState {
            it.copy(
                moviesPagingDataFlow = topRatingMediaFlow,
                isScreenLoading = false,
            )
        }
    }

    fun loadGenresMovies() {
        tryToCall(
            call = {
            val movieGenres = getMoviesGenreUseCase()
            val genres = movieGenres.map { genre ->
                GenreUiState(
                    id = genre.id,
                    name = genre.name,
                    isSelected = genre.id.toLong() == state.value.selectedCategoryId
                )
            }
            genres
        },
            onSuccess = ::updateScreenWithNewMovieGenres,
            onError = {},
            dispatcher = coroutineDispatcher
        )
    }

    fun updateScreenWithNewMovieGenres(movieGenres: List<GenreUiState>) {
        updateState { state ->
            state.copy(
                moviesGenres = movieGenres, isScreenLoading = false
            )
        }
    }

    fun updateScreenStateToError(errorUiState: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                errorMessage = errorUiState.message, isScreenLoading = false
            )
        }
    }

    fun updateScreenStateToLoading() = updateState { it.copy(isScreenLoading = true) }

    override fun onCategoryCardClicked(catgoryId: Long) {
        updateState { state ->
            state.copy(
                selectedCategoryId = catgoryId,
                moviesGenres = state.moviesGenres.map { genre ->
                    if (genre.id.toLong() == catgoryId) {
                        genre.copy(isSelected = true)
                    } else {
                        genre.copy(isSelected = false)
                    }
                })
        }

    }
}


