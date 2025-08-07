package com.berlin.aflami.viewmodel.listFeature

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.FavouriteListItemUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.createnewlist.CreateNewListUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.editList.EditListSheetState

@Immutable
data class ListScreenState(
    val listName: TextFieldValue = TextFieldValue(""),
    val favouriteList: List<FavouriteListItemUiState> = emptyList(),
    val createNewListSheetState: CreateNewListUiState = CreateNewListUiState(),
    val createNewListDialogVisible: Boolean = false,
    val isUserLoggedIn: Boolean = true,
    val isScreenLoading: Boolean = false,
    val errorMessage: String = "",
    val editListSheetState: EditListSheetState = EditListSheetState(),
)
