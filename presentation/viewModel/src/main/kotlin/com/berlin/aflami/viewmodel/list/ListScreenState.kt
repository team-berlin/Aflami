package com.berlin.aflami.viewmodel.list

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.details.common.SnackBarUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.FavouriteListItemUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.createnewlist.CreateNewListUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.editList.EditListSheetState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class ListScreenState(
    val listName: TextFieldValue = TextFieldValue(""),
    val favouriteList: Flow<PagingData<FavouriteListItemUiState>> = emptyFlow(),
    val isUserLoggedIn: Boolean? = null,
    val isLoginRequiredDialogVisible: Boolean = false,
    val createNewListSheetState: CreateNewListUiState = CreateNewListUiState(),
    val editListSheetState: EditListSheetState = EditListSheetState(),
    val isScreenLoading: Boolean = true,
    val errorUiState: ErrorUiState? = null,
    val snackBar: SnackBarUiState = SnackBarUiState(),
)
