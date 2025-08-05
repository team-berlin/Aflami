package com.berlin.aflami.viewmodel.reusableinteractionlistener.addtofavourite

interface AddToFavouriteInteractionListener {
    fun onAddMediaToFavouriteListClicked(favouriteListId: Int, mediaId: Long)
    fun onSelectFavouriteList(favouriteListId: Int)
    fun onCreateNewFavouriteListClicked()
    fun onCancelAddingToFavouriteClicked()
}