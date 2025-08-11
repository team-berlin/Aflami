package com.berlin.aflami.viewmodel.listFeature

import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.createnewlist.CreateNewListInteractionListener
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.editList.EditListInteractionListener

interface ListScreenInteractionListener : CreateNewListInteractionListener,
    EditListInteractionListener {
    fun onBackClicked()
    fun onListNameChange(newListTitle: String)
    fun onLoginClicked()
    fun onClickAddList()
    fun onClickListCard(listId: Int, listName: String)
}