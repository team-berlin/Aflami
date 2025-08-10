package com.berlin.aflami.viewmodel.reusableinteractionlistener.addtofavourite

interface AddToFavouriteInteractionListener {
    fun onAddMediaToFavouriteListClicked(mediaId: Long, favouriteListId: Int)
    fun onSelectFavouriteList(favouriteListId: Int)
    fun onCreateNewFavouriteListClicked()
    fun onCancelAddingToFavouriteClicked()
}