package com.berlin.aflami.viewmodel.home.toprating

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecase.GetTopRatedMoviesUseCase
import usecase.GetTopRatedSeriesUseCase

class TopRatingViewModel(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase,
) : BaseViewModel<TopRatingUiState, TopRatingScreenEffect>(TopRatingUiState()),
    TopRatingInteractionListener {

    val topRatedPagingFlow: Flow<PagingData<MediaUiState>> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20),
        pagingSourceFactory = {
            BasePagingSource(
                call = { page ->
                    coroutineScope {
                        val moviesDeferred =
                            async { getTopRatedMoviesUseCase(page).map { it.toUIStateMedia() } }
                        val seriesDeferred =
                            async { getTopRatedSeriesUseCase(page).map { it.toUIStateMedia() } }
                        val topRatedMovies = moviesDeferred.await()
                        val topRatedSeries = seriesDeferred.await()
                        _state.update { it.copy(isLoading = false) }
                        (topRatedMovies + topRatedSeries).sortedByDescending { it.rating }
                    }
                }
            )
        }
    ).flow.cachedIn(viewModelScope)

    fun getTopRatingMoviesAndTvShows() {
        _state.update { oldState ->
            oldState.copy(isLoading = true, errorMessage = null, topRatedMedia = emptyList())
        }
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 20, initialLoadSize = 20),
                pagingSourceFactory = {
                    BasePagingSource(
                        call = { page ->
                            val moviesDeferred =
                                async { getTopRatedMoviesUseCase(page).map { it.toUIStateMedia() } }
                            val seriesDeferred =
                                async { getTopRatedSeriesUseCase(page).map { it.toUIStateMedia() } }
                            val topRatedMovies = moviesDeferred.await()
                            val topRatedSeries = seriesDeferred.await()
                            (topRatedMovies + topRatedSeries).sortedByDescending { it.rating }
                        }
                    )
                }
            )
        }
    }

    override fun onBackClicked() = sendNewEffect(TopRatingScreenEffect.NavigateBack)

    override fun onMediaCardClicked(
        id: Long,
        mediaType: MediaType,
    ) = sendNewEffect(TopRatingScreenEffect.NavigateToMediaDetailsScreen(id, mediaType))
}