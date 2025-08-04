package com.berlin.aflami.viewmodel.list

import com.berlin.aflami.viewmodel.reusableinteractionlistener.addTiList.CustomListItemUiState

data class ListScreenState(
    val userCustomLists: List<CustomListItemUiState> = emptyList(),
    val errorMessage:String = "",
)
