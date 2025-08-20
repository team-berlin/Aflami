package com.berlin.aflami.viewmodel.reusableinteractionlistener.addtofavourite

interface AddToFavouriteInteractionListener {
    fun onAddMediaToFavouriteButtonClicked(mediaId: Long, favouriteListId: Int)
    fun onSelectFavouriteList(favouriteListId: Int)
    fun onCreateNewFavouriteListClicked()
    fun onCancelAddingToFavouriteClicked()


}