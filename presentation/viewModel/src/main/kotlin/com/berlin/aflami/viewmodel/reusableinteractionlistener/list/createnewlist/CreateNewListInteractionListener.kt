package com.berlin.aflami.viewmodel.reusableinteractionlistener.list.createnewlist

import androidx.compose.ui.text.input.TextFieldValue

interface CreateNewListInteractionListener {
    fun onUpdateNewListTitle(newListTitle: TextFieldValue)
    fun onCreateNewListClicked(listTitle: TextFieldValue)
    fun onCancelCreatingNewListClicked()
}