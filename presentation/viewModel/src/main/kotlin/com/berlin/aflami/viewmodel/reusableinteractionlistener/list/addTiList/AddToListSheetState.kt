package com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.base.ErrorUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class AddToListSheetState(
    val isLoading: Boolean = true,
    val selectedListId: Int? = null,
    val favouriteLists: Flow<PagingData<FavouriteListItemUiState>> = emptyFlow(),
    val isAddToListDialogVisible: Boolean = false,
    val isAddButtonEnabled: Boolean = false,
    val errorUiState: ErrorUiState? = null,
)

@Immutable
data class FavouriteListItemUiState(
    val listId: Int? = null,
    val listTitle: String = "",
    val numberOfFavouriteMovies: Int = 0,
)