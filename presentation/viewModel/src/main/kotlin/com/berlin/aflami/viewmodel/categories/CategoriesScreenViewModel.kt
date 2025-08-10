package com.berlin.aflami.viewmodel.categories

import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.toGenreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import usecase.movie.GetMovieGenresUseCase
import usecase.tvshow.GetTVShowGenresUseCase
import javax.inject.Inject

@HiltViewModel
class CategoriesScreenViewModel @Inject constructor(
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getTVShowGenresUseCase: GetTVShowGenresUseCase,

    ) : BaseViewModel<CategoriesScreenUiState, CategoriesScreenEffect>(
    CategoriesScreenUiState()
), CategoriesInteractionListener {


    override fun onCategoryCardClicked(mediaId: Long, mediaType: MediaType) =
        sendNewEffect(CategoriesScreenEffect.NavigateToMediaScreen(mediaId, mediaType))


    init {
        getMovieGenres()
        getTVShowGenres()

    }

    private fun getMovieGenres() {
        updateScreenStateToLoading()
        tryToCall(
            call = { getMovieGenresUseCase().map { it.toGenreUiState() } },
            onSuccess = ::updateScreenStateWithMovieGenres,
            onError = ::updateScreenStateToError
        )
    }

    private fun getTVShowGenres() {
        updateScreenStateToLoading()
        tryToCall(
            call = { getTVShowGenresUseCase().map { it.toGenreUiState() } },
            onSuccess = ::updateScreenStateWithTvGenres,
            onError = ::updateScreenStateToError
        )
    }

    private fun updateScreenStateWithMovieGenres(moviesCategories: List<GenreUiState>) =
        updateState { screenState ->
            screenState.copy(moviesGenres = moviesCategories, isLoading = false)
        }

    private fun updateScreenStateWithTvGenres(tvShowCategories: List<GenreUiState>) =
        updateState { screenState ->
            screenState.copy(tvShowGenres = tvShowCategories, isLoading = false)
        }


    private fun updateScreenStateToError(errorUiState: ErrorUiState) =
        updateState { screenState ->
            screenState.copy(
                errorMessage = errorUiState.message,
                isLoading = false
            )
        }

    private fun updateScreenStateToLoading() =
        updateState { screenState -> screenState.copy(isLoading = true) }


    override fun onTabOptionClicked(tabOption: TabOption) {
        updateState {
            it.copy(
                isLoading = false,
                selectedTabOption = tabOption,
            )
        }
    }

}