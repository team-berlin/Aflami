package com.berlin.aflami.viewmodel.profile.watchhistory

import androidx.paging.Pager
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import usecase.movie.ContinueWatchingMovieUseCase
import usecase.tvshow.ContinueWatchingTVShowUseCase
import javax.inject.Inject

@HiltViewModel
class WatchHistoryViewModel @Inject constructor(
    private val getContinueWatchingMovieUseCase: ContinueWatchingMovieUseCase,
    private val getContinueWatchingTvShowUseCase: ContinueWatchingTVShowUseCase,

    ) : BaseViewModel<WatchHistoryUiState, WatchHistoryScreenEffect>(
    WatchHistoryUiState()
), WatchHistoryInteractionListener {

    override fun onBackClicked() = sendNewEffect(WatchHistoryScreenEffect.NavigateBack)

    override fun onMediaCardClicked(mediaId: Long, mediaType: MediaType) =
        sendNewEffect(WatchHistoryScreenEffect.NavigateToDetailsScreen(mediaId, mediaType))


    init {
        getWatchHistoryToMovie()
        getWatchHistoryToTVShow()

    }
    private fun getWatchHistoryToMovie() {
        updateScreenStateToLoading()
        tryToCall(
            call = { (getWatchHistoryMovieAsFlow()) },
            onSuccess = ::updateScreenStateWithNewMovie,
            onError = ::updateScreenStateToError
        )
    }

    private fun getWatchHistoryToTVShow() {
        updateScreenStateToLoading()
        tryToCall(
            call = { getWatchHistoryTVShowAsFlow() },
            onSuccess = ::updateScreenStateWithNewTVShow,
            onError = ::updateScreenStateToError
        )
    }

    private fun getWatchHistoryMovieAsFlow(): Flow<PagingData<MovieUiState>> = Pager(
        config = defaultPageConfigurations(),
        pagingSourceFactory = {
            WatchHistoryMoviesPagingSource(
                getContinueWatchingMovieUseCase
            )
        }
    ).flow

    private fun getWatchHistoryTVShowAsFlow(): Flow<PagingData<TVShowUiState>> = Pager(
        config = defaultPageConfigurations(),
        pagingSourceFactory = {
            WatchHistoryTVShowsPagingSource(
                getContinueWatchingTvShowUseCase
            )
        }
    ).flow

    private fun updateScreenStateWithNewMovie(watchHistoryMovie: Flow<PagingData<MovieUiState>>) =
        updateState { screenState ->
            screenState.copy(movies = watchHistoryMovie, isLoading = false)
        }

    private fun updateScreenStateWithNewTVShow(watchHistoryTVShow: Flow<PagingData<TVShowUiState>>) =
        updateState { screenState ->
            screenState.copy(tvShows = watchHistoryTVShow, isLoading = false)
        }


    private fun updateScreenStateToError(errorUiState: ErrorUiState) =
        updateState { screenState ->
            screenState.copy(
                errorUiState = errorUiState,
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