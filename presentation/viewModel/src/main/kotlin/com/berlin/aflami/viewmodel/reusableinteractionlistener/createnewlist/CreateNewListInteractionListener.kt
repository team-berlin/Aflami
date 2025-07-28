package com.berlin.aflami.viewmodel.reusableinteractionlistener.createnewlist

interface CreateNewListInteractionListener {
    fun onUpdateNewListTitle(newListTitle: String)
    fun onCreateNewListClicked(listTitle: String)
    fun onCancelCreatingNewListClicked()
}