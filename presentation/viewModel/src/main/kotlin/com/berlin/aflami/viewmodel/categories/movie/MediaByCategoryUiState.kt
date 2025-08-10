package com.berlin.aflami.viewmodel.categories.movie

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class MediaByCategoryUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val mediaType: MediaType = MediaType.MOVIE,
    val moviesGenres: List<GenreUiState> = emptyList(),
    val tvShowGenres: List<GenreUiState> = emptyList(),
    val moviesPagingDataFlow: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val tvShowsPagingDataFlow: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val selectedCategoryId: Long=-1L,
    )
