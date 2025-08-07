package com.berlin.aflami.viewmodel.listFeature

import android.util.Log
import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.mapper.toFavourListItemUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.FavouriteListItemUiState
import usecase.favouritelist.GetAllFavouriteListsUseCase

class AllFavouriteListsPagingSource(
    private val getAllFavouriteListsUseCase: GetAllFavouriteListsUseCase,
) : BasePagingSource<FavouriteListItemUiState>() {

    override suspend fun fetchData(page: Int): List<FavouriteListItemUiState> {
        return getAllFavouriteListsUseCase.invoke(pageNumber = page).map { favouriteList ->
            Log.d("khairy", "favouriteList = $favouriteList")
            favouriteList.toFavourListItemUiState()
        }
    }
}