package com.berlin.aflami.viewmodel.reusableinteractionlistener.addtofavourite

interface AddToFavouriteInteractionListener {
    fun onAddMediaToFavouriteListClicked(favouriteListId: Int, mediaId: Int)
    fun onSelectFavouriteList(favouriteListId: Int)
    fun onCreateNewFavouriteListClicked()
    fun onCancelAddingToFavouriteClicked()
}