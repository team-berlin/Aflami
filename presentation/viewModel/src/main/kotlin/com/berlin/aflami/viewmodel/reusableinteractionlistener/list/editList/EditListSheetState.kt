package com.berlin.aflami.viewmodel.reusableinteractionlistener.list.editList

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.berlin.aflami.viewmodel.base.ErrorUiState

@Immutable
data class EditListSheetState(
    val isEditNewListDialogVisible: Boolean = false,
    val requiredListIdToEdit: Int? = null,
    val currentListTitle:TextFieldValue = TextFieldValue(""),
    val isSaveButtonEnabled: Boolean = false,
    val errorUiState: ErrorUiState? = null,
)
