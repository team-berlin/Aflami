package com.berlin.aflami.viewmodel.listFeature

import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.CustomListItemUiState

data class ListScreenState(
    val userCustomLists: List<CustomListItemUiState> = emptyList(),
    val errorMessage:String = "",
)
