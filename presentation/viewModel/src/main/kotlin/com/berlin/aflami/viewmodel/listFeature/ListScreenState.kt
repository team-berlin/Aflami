package com.berlin.aflami.viewmodel.listFeature

import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.FavouriteListItemUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.editList.EditListSheetState

data class ListScreenState(
    val favouriteList: List<FavouriteListItemUiState> = emptyList(),
    val errorMessage: String = "",
    val editListSheetState: EditListSheetState = EditListSheetState(),
)
