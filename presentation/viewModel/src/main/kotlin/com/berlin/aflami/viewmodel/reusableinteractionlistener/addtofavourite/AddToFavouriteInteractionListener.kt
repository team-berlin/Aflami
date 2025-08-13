package com.berlin.aflami.viewmodel.reusableinteractionlistener.addtofavourite

interface AddToFavouriteInteractionListener {
    fun onAddMediaToFavouriteButtomClicked(mediaId: Long, favouriteListId: Int)
    fun onSelectFavouriteList(favouriteListId: Int)
    fun onCreateNewFavouriteListClicked()
    fun onCancelAddingToFavouriteClicked()


}