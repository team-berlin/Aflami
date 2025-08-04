package com.berlin.aflami.viewmodel.reusableinteractionlistener.addTiList

interface AddToListInteractionListener {
    fun onCancelClicked()
    fun onSelectListClicked(listId: Int)
    fun onAddToListClicked(listId: Int, movieId: Long)
    fun onCreateNewListClicked()
}