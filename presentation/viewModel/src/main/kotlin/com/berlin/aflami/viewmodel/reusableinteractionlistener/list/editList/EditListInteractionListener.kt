package com.berlin.aflami.viewmodel.reusableinteractionlistener.list.editList

import androidx.compose.ui.text.input.TextFieldValue

interface EditListInteractionListener {
    fun onCancelEditingListClicked()
    fun onOldListTitleChanged(editedListTitle: String)
    fun onSaveOldListTitleToNewTitleClicked(listId: Int, editedListTitle: TextFieldValue)
}