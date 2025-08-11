package com.berlin.aflami.viewmodel.listFeature

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.util.IS_LIST_DELETED_SUCCESSFULLY
import com.berlin.aflami.viewmodel.util.REQUIRED_LIST_ID_TO_EDIT
import com.berlin.aflami.viewmodel.util.REQUIRED_LIST_TITLE_TO_EDIT
import com.berlin.aflami.viewmodel.util.SHOULD_SHOW_DELETE_SNACK_BAR
import com.berlin.aflami.viewmodel.util.SHOW_EDIT_SHEET
import javax.inject.Inject

class FavouriteListArgs @Inject constructor(
    savedStateHandle: SavedStateHandle,
) {
    val shouldShowEditSheet = savedStateHandle.get<Boolean?>(SHOW_EDIT_SHEET)
    val requiredListIdToEdit = savedStateHandle.get<Int?>(REQUIRED_LIST_ID_TO_EDIT)
    val requiredListTitleToEdit = savedStateHandle.get<String?>(REQUIRED_LIST_TITLE_TO_EDIT)
    val shouldShowDeleteSnackBar = savedStateHandle.get<Boolean?>(SHOULD_SHOW_DELETE_SNACK_BAR)
    val isListDeletedSuccessfully = savedStateHandle.get<Boolean?>(IS_LIST_DELETED_SUCCESSFULLY)
}