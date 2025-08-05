package com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList

import androidx.compose.runtime.Immutable

@Immutable
data class AddToListSheetState(
    val selectedListId: Int? = null,
    val userCustomLists: List<FavouriteListItemUiState> = emptyList(),
    val isAddButtonEnabled: Boolean = false,
)

@Immutable
data class FavouriteListItemUiState(
    val listTitle: String = "",
    val numberOfFavouriteMovies: Int = 0,
    val isSelected: Boolean = false,
)