package com.berlin.aflami.viewmodel.listFeature

import androidx.compose.ui.text.input.TextFieldValue
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.createnewlist.CreateNewListInteractionListener

interface ListScreenInteractionListener : CreateNewListInteractionListener {
    fun onBackClicked()
    fun onListNameChange(newListTitle: TextFieldValue)
    fun onLoginClicked()
    fun onClickAddList()
    fun onClickListCard(listId: Int, listName: String)
}