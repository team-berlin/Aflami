package com.berlin.aflami.viewmodel.listDetails

import android.util.Log
import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.mapper.toMovieUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import usecase.favouritelist.GetFavouriteListItemsUseCase

class FavouriteMoviesPagingSource(
    private val getAllFavouriteListItemsUseCase: GetFavouriteListItemsUseCase,
    private val favouriteListId: Int,
) : BasePagingSource<MovieUiState>() {

    override suspend fun fetchData(page: Int): List<MovieUiState> {
        return getAllFavouriteListItemsUseCase.invoke(
            pageNumber = page,
            favouriteListId = favouriteListId
        ).map { movie -> movie.toMovieUiState() }.also {
            Log.d("kairy", "fetchFavourite Movies ui states returned $it")
        }
    }
}