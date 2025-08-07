package com.berlin.aflami.viewmodel.reusableinteractionlistener.list.editList

import androidx.compose.runtime.Immutable

@Immutable
data class EditListSheetState(
    val currentListId: Int? = null,
    val currentListTitle: String = "",
    val isSaveButtonEnabled: Boolean = false,
    val errorMessage: String = "",
)
