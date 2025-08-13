package com.berlin.aflami.viewmodel.details.common

import com.berlin.aflami.viewmodel.reusableinteractionlistener.addtofavourite.AddToFavouriteInteractionListener
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.createnewlist.CreateNewListInteractionListener
import com.berlin.aflami.viewmodel.reusableinteractionlistener.rate.RateInteractionListener

interface MediaDetailsScreenInteractionListener :
    RateInteractionListener,
    AddToFavouriteInteractionListener,
    CreateNewListInteractionListener,
    ExtraMediaContentInteractionListener {

    fun onBackClicked()
    fun onPlayClicked(videoUrl: String)
    fun onReadMoreDescriptionClicked()
    fun onReadMoreReviewClicked(reviewId: String)
    fun onShowCastClicked(mediaId: Long)
    fun onMediaCardClicked(mediaId: Long)
    fun onLoginButtonClicked()
    fun onLoginDialogDismissed()

}

interface ExtraMediaContentInteractionListener {
    fun onShowMoreMediaLikeThisClicked(mediaId: Long)
    fun onShowReviewsClicked(mediaId: Long)
    fun onShowMediaGalleryClicked(mediaId: Long)
    fun onShowCompanyProductionClicked()
}