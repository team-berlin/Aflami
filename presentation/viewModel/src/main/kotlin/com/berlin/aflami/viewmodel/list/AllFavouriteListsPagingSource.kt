package com.berlin.aflami.viewmodel.list

import android.util.Log
import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.mapper.toFavourListItemUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.FavouriteListItemUiState
import usecase.favouritelist.GetAllFavouriteListsUseCase

class AllFavouriteListsPagingSource(
    private val getAllFavouriteListsUseCase: GetAllFavouriteListsUseCase,
) : BasePagingSource<FavouriteListItemUiState>() {

    override suspend fun fetchData(page: Int): List<FavouriteListItemUiState> {
        return getAllFavouriteListsUseCase.invoke(pageNumber = page).ifEmpty {
            Log.d("Khairy", "empty lists")
            return emptyList()
        }
            .map { favouriteList ->
                favouriteList.toFavourListItemUiState()
            }
    }
}