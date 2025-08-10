package com.berlin.aflami.viewmodel.categories.movie

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
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
import usecase.tvshow.GetTVShowGenresUseCase
import usecase.tvshow.GetTVShowsByCategoryUseCase
import javax.inject.Inject

@HiltViewModel
class MediaByCategoryScreenViewModel @Inject constructor(
    private val getMovieByGenresUseCase: GetMoviesByCategoryUseCase,
    private val getTVShowByGenresUseCase: GetTVShowsByCategoryUseCase,
    private val getMoviesGenreUseCase: GetMovieGenresUseCase,
    private val getTVShowGenresUseCase: GetTVShowGenresUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    mediaByCategoriesArgs: MediaByCategoryArgs
) : BaseViewModel<MediaByCategoryUiState, MediaByCategoryScreenEffect>(
    MediaByCategoryUiState()
), MediaByCategoryInteractionListener {
    private val categoryId = mediaByCategoriesArgs.categoryId ?: 0
    private val mediaType = mediaByCategoriesArgs.mediaType ?: MediaType.MOVIE

    init {
        Log.d("WOWTEST", mediaType.toString())
        updateState { screenState ->
            screenState.copy(
                selectedCategoryId = categoryId,
                mediaType = mediaType
            )
        }
        observeCategorySelection()
    }

    fun observeCategorySelection() {
        viewModelScope.launch {
            state.map { state -> state.selectedCategoryId }
                .distinctUntilChanged()
                .collectLatest { genre ->
                    when (mediaType) {
                        MediaType.MOVIE -> {
                            loadGenresMovies()
                            getMoviesByCategory()
                        }
                        MediaType.TV_SHOW -> {
                            loadGenresTVShow()
                            getTVShowsByCategory()
                        }
                    }
                }
        }
    }

    override fun onMediaCardClicked(mediaId: Long, mediaType: MediaType) =
        when (mediaType) {
            MediaType.MOVIE -> sendNewEffect(
                MediaByCategoryScreenEffect.NavigateToMediaDetails(
                    mediaId,
                    mediaType
                )
            )

            MediaType.TV_SHOW -> sendNewEffect(
                MediaByCategoryScreenEffect.NavigateToMediaDetails(
                    mediaId,
                    mediaType
                )
            )
        }


    private fun getMoviesByCategory() {
        updateScreenStateToLoading()
        tryToCall(
            call = {
                getMoviesByCategoryAsFlow(
                    getMovieByGenresUseCase,
                    state.value.selectedCategoryId
                )
            },
            onSuccess = ::updateScreenStateWithMovies,
            onError = ::updateScreenStateToError
        )
    }

    private fun getTVShowsByCategory() {
        updateScreenStateToLoading()
        Log.d("WOWTEST", "getTVShowsByCategory: ${state.value.selectedCategoryId}")
        tryToCall(
            call = {
                getTvShowsByCategoryAsFlow(
                    getTVShowByGenresUseCase,
                    state.value.selectedCategoryId
                )
            },
            onSuccess = ::updateScreenStateWithTvVShows,
            onError = ::updateScreenStateToError
        )
    }

    private fun getMoviesByCategoryAsFlow(
        getMovieGenresUseCase: GetMoviesByCategoryUseCase,
        movieCategoryId: Long
    ): Flow<PagingData<MediaUiState>> {
        return Pager(
            config = defaultPageConfigurations(),
            pagingSourceFactory = {
                MoviesByCategoryPagingSource(
                    getMovieGenresUseCase,
                    movieCategoryId
                )
            }
        ).flow
    }

    private fun updateScreenStateWithMovies(topRatingMediaFlow: Flow<PagingData<MediaUiState>>) {
        updateState {
            it.copy(
                moviesPagingDataFlow = topRatingMediaFlow,
                isLoading = false,
            )
        }
    }

    private fun updateScreenStateWithTvVShows(topRatingMediaFlow: Flow<PagingData<MediaUiState>>) {
        updateState {
            it.copy(
                tvShowsPagingDataFlow = topRatingMediaFlow,
                isLoading = false,
            )
        }
    }

    private fun getTvShowsByCategoryAsFlow(
        getTVShowsByCategoryUseCase: GetTVShowsByCategoryUseCase,
        movieCategoryId: Long
    ): Flow<PagingData<MediaUiState>> = Pager(
        config = defaultPageConfigurations(),
        pagingSourceFactory = {
            TVShowsByCategoryPagingSource(
                getTVShowsByCategoryUseCase,
                movieCategoryId
            )
        }
    ).flow

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

    fun loadGenresTVShow() {
        tryToCall(
            call = {
                val tvShowsGenre = getTVShowGenresUseCase()
                val genres = tvShowsGenre.map { genre ->
                    GenreUiState(
                        id = genre.id,
                        name = genre.name,
                        isSelected = genre.id.toLong() == state.value.selectedCategoryId
                    )
                }
                genres
            },
            onSuccess = ::updateScreenWithNewTVShowGenres,
            onError = {},
            dispatcher = coroutineDispatcher
        )
    }

    private fun updateScreenWithNewMovieGenres(movieGenres: List<GenreUiState>) {
        updateState { state ->
            state.copy(
                moviesGenres = movieGenres,
                isLoading = false
            )
        }
    }

    private fun updateScreenWithNewTVShowGenres(tVShowGenres: List<GenreUiState>) {
        updateState { state ->
            state.copy(
                tvShowGenres = tVShowGenres
            )
        }
    }

    private fun updateScreenStateToError(errorUiState: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                errorMessage = errorUiState.message,
                isLoading = false
            )
        }
    }

    private fun updateScreenStateToLoading() = updateState { it.copy(isLoading = true) }

    override fun onCategoryCardClicked(catgoryId: Long) {

        when (mediaType) {
            MediaType.MOVIE -> {
                updateState { state ->
                    state.copy(
                        selectedCategoryId = catgoryId,
                        moviesGenres = state.moviesGenres.map { genre ->
                            if (genre.id.toLong() == catgoryId) {
                                genre.copy(isSelected = true)
                            } else {
                                genre.copy(isSelected = false)
                            }
                        }
                    )
                }
            }

            MediaType.TV_SHOW -> {
                updateState { state ->
                    state.copy(
                        selectedCategoryId = catgoryId,
                        tvShowGenres = state.tvShowGenres.map { genre ->
                            if (genre.id.toLong() == catgoryId) {
                                genre.copy(isSelected = true)
                            } else {
                                genre.copy(isSelected = false)
                            }
                        },

                        )
                }
            }
        }
    }
}

