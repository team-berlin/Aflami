package com.berlin.aflami.viewmodel.categories.tvshow

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.categories.movie.MediaByCategoryArgs
import com.berlin.aflami.viewmodel.categories.movie.MediaByCategoryInteractionListener
import com.berlin.aflami.viewmodel.categories.movie.MediaByCategoryScreenEffect
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import usecase.tvshow.GetTVShowGenresUseCase
import usecase.tvshow.GetTVShowsByCategoryUseCase
import javax.inject.Inject

@HiltViewModel
class TVShowByCategoryScreenViewModel @Inject constructor(
    private val getTVShowByGenresUseCase: GetTVShowsByCategoryUseCase,
    private val getTVShowGenresUseCase: GetTVShowGenresUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    mediaByCategoriesArgs: MediaByCategoryArgs
) : BaseViewModel<TVShowByCategoryUiState, MediaByCategoryScreenEffect>(
    TVShowByCategoryUiState()
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
            state.map { state -> state.selectedCategoryId }
                .distinctUntilChanged()
                .collectLatest { genre ->
                    loadGenresTVShow()
                    getTVShowsByCategory()
                }
        }
    }

    override fun onMediaCardClicked(mediaId: Long) =
        sendNewEffect(
            MediaByCategoryScreenEffect.NavigateToMediaDetails(
                mediaId,

                )
        )

    override fun onBackClicked() {
        sendNewEffect(MediaByCategoryScreenEffect.NavigateBack)
    }

    private fun getTVShowsByCategory() {
        updateScreenStateToLoading()
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

    private fun updateScreenStateWithTvVShows(topRatingMediaFlow: Flow<PagingData<TVShowUiState>>) {
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
    ): Flow<PagingData<TVShowUiState>> = Pager(
        config = defaultPageConfigurations(),
        pagingSourceFactory = {
            TVShowsByCategoryPagingSource(
                getTVShowsByCategoryUseCase,
                movieCategoryId
            )
        }
    ).flow

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

