package com.berlin.aflami.viewmodel.listFeature

import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.CustomListItemUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.editList.EditListSheetState

data class ListScreenState(
    val userCustomLists: List<CustomListItemUiState> = emptyList(),
    val errorMessage: String = "",
    val editListSheetState: EditListSheetState = EditListSheetState(),
)
