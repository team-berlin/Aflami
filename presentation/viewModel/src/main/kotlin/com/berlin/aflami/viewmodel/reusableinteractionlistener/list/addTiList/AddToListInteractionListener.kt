package com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList

interface AddToListInteractionListener {
    fun onCancelAddingToListClicked()
    fun onSelectListClicked(listId: Int)
    fun onAddToListClicked(listId: Int, movieId: Long)
    fun onCreateNewListClicked()
}