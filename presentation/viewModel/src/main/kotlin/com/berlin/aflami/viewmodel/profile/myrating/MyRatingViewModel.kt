package com.berlin.aflami.viewmodel.profile.myrating

import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import usecase.movie.GetRatedMoviesUseCase
import usecase.tvshow.GetRatedTVShowsUseCase
import javax.inject.Inject

@HiltViewModel
class MyRatingViewModel @Inject constructor(
    private val getRatedMoviesUseCase: GetRatedMoviesUseCase,
    private val getRatedTVShowsUseCase: GetRatedTVShowsUseCase,
) : BaseViewModel<MyRatingUiState, MyRatingScreenEffect>(MyRatingUiState()),
    MyRatingInteractionListener {


    init {
        loadRatedMovies()
        loadRatedTVShows()
    }


    override fun onBackClicked() {
        sendNewEffect(MyRatingScreenEffect.NavigateBack)
    }

    override fun onMediaCardClicked(
        mediaId: Long,
        mediaType: MediaType
    ) {
        sendNewEffect(MyRatingScreenEffect.NavigateToDetailsScreen(mediaId, mediaType))

    }

    override fun onTabOptionClicked(tabOption: TabOption) {
        updateState {
            it.copy(
                isLoading = false,
                selectedTabOption = tabOption,
            )
        }
    }


    private fun loadRatedMovies() {
        updateState { it.copy(isLoading = true) }
        tryToCall(
            call = { ratedMoviesFlow() },
            onSuccess = { flow -> updateState { it.copy(movies = flow, isLoading = false) } },
            onError = ::updateToError
        )
    }

    private fun loadRatedTVShows() {
        updateState { it.copy(isLoading = true) }
        tryToCall(
            call = { ratedTvFlow() },
            onSuccess = { flow -> updateState { it.copy(tvShows = flow, isLoading = false) } },
            onError = ::updateToError
        )
    }

    private fun ratedMoviesFlow() = androidx.paging.Pager(
        config = defaultPageConfigurations(),
        pagingSourceFactory = {
            RatedMoviesPagingSource(getRatedMoviesUseCase)
        }
    ).flow

    private fun ratedTvFlow() = androidx.paging.Pager(
        config = defaultPageConfigurations(),
        pagingSourceFactory = {
            RatedTVShowsPagingSource(getRatedTVShowsUseCase)
        }
    ).flow

    private fun updateToError(e: ErrorUiState) =
        updateState { it.copy(errorUiState = e, isLoading = false) }


}