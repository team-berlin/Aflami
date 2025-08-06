package com.berlin.aflami.viewmodel.listFeature

import androidx.compose.runtime.Immutable
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.FavouriteListItemUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.createnewlist.CreateNewListUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.editList.EditListSheetState

@Immutable
data class ListScreenState(
    val favouriteList: List<FavouriteListItemUiState> = emptyList(),
    val createNewListSheetState: CreateNewListUiState = CreateNewListUiState(),
    val errorMessage: String = "",
    val editListSheetState: EditListSheetState = EditListSheetState(),
)
