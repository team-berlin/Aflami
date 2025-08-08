package com.berlin.aflami.viewmodel.listFeature

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.FavouriteListItemUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.createnewlist.CreateNewListUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.editList.EditListSheetState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class ListScreenState(
    val listName: TextFieldValue = TextFieldValue(""),
    val favouriteList: Flow<PagingData<FavouriteListItemUiState>> = emptyFlow(),
    val createNewListSheetState: CreateNewListUiState = CreateNewListUiState(),
    val isUserLoggedIn: Boolean = false,
    val isScreenLoading: Boolean = true,
    val isCreateNewListDialogVisible: Boolean = false,
    val errorMessage: String = "",
    val editListSheetState: EditListSheetState = EditListSheetState(),
)
