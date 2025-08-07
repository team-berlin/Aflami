package com.berlin.aflami.viewmodel.reusableinteractionlistener.list.createnewlist

import androidx.compose.runtime.Immutable

@Immutable
data class CreateNewListUiState(
    val newListTitle: String = "",
    val isCreateNewListButtonEnabled: Boolean = false,
    val isCreateNewListDialogVisible: Boolean = false,
)
