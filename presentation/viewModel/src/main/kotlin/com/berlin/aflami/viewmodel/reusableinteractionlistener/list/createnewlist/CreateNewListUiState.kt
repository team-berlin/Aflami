package com.berlin.aflami.viewmodel.reusableinteractionlistener.list.createnewlist

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue

@Immutable
data class CreateNewListUiState(
    val newListTitle:TextFieldValue = TextFieldValue(""),
    val isCreateNewListButtonEnabled: Boolean = false,
    val isCreateNewListDialogVisible: Boolean = false,
)
