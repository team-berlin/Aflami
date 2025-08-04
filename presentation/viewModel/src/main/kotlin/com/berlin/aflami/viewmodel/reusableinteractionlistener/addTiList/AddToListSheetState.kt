package com.berlin.aflami.viewmodel.reusableinteractionlistener.addTiList

import androidx.compose.runtime.Immutable

@Immutable
data class AddToListSheetState(
    val selectedListId: Int? = null,
    val userCustomLists: List<CustomListItemUiState> = emptyList(),
    val isAddButtonEnabled: Boolean = false,
)

@Immutable
data class CustomListItemUiState(
    val listTitle: String = "",
    val numberOfItems: Int = 0,
    val isSelected: Boolean = false,
)