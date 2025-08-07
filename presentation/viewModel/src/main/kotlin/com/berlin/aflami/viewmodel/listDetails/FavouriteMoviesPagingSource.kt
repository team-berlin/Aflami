package com.berlin.aflami.viewmodel.listDetails

import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.mapper.toMovieUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import usecase.favouritelist.GetFavouriteListItemsUseCase

class FavouriteMoviesPagingSource(
    private val getAllFavouriteListItemsUseCase: GetFavouriteListItemsUseCase,
    private val favouriteListId: Int,
) : BasePagingSource<MovieUiState>() {

    override suspend fun fetchData(page: Int): List<MovieUiState> {
        return getAllFavouriteListItemsUseCase(
            pageNumber = page,
            favouriteListId = favouriteListId
        ).map { movie -> movie.toMovieUiState() }
    }
}