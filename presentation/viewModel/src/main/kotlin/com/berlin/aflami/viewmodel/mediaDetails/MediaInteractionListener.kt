package com.berlin.aflami.viewmodel.mediaDetails

interface MediaInteractionListener : AddToFavouriteInteractionListener,
    RateInteractionListener,
    ExtraMediaContentInteractionListener {
    fun onBackClicked()
    fun onPlayClicked(id: Long)
    fun onReadMoreDescriptionClicked(id: Long)
    fun onShowCastClicked(id: Long)
}

interface RateInteractionListener {
    fun onRateMediaClicked(id: Long)
    fun onSelectRateClicked(rate: Float)
    fun onSubmitRateClicked(rate: Float)
    fun onCancelRatingClicked()
}

interface AddToFavouriteInteractionListener : CreateNewListInteractionListener {
    fun onAddMediaToFavouriteListClicked(favouriteListId: Int, mediaId: Int)
    fun onSelectFavouriteList(favouriteListId: Int)
    fun onCreateNewFavouriteListClicked()
    fun onCancelAddingToFavouriteClicked()
}

interface CreateNewListInteractionListener {
    fun onUpdateNewListTitle(newListTitle: String)
    fun onCreateNewListClicked(listTitle: String)
    fun onCancelCreatingNewListClicked()
}

interface ExtraMediaContentInteractionListener {
    fun onShowMoreMediaLikeThisClicked()
    fun onShowReviewsClicked()
    fun onShowMediaGalleryClicked()
    fun onShowComponyProductionClicked()
}