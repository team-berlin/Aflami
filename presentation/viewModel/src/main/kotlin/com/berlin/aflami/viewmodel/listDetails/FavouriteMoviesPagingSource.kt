package com.berlin.aflami.viewmodel.listDetails

import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.mapper.toMovieUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import usecase.favouritelist.GetFavouriteListItemsUseCase

class FavouriteMoviesPagingSource @AssistedInject constructor(
    private val getAllFavouriteListItemsUseCase: GetFavouriteListItemsUseCase,
    @Assisted private val favouriteListId: Int,
) : BasePagingSource<MovieUiState>() {

    override suspend fun fetchData(page: Int): List<MovieUiState> {
        return getAllFavouriteListItemsUseCase.invoke(
            pageNumber = page,
            favouriteListId = favouriteListId
        ).map { movie -> movie.toMovieUiState() }
    }

    @AssistedFactory
    interface Factory {
        fun create(favouriteListId: Int): FavouriteMoviesPagingSource
    }

}