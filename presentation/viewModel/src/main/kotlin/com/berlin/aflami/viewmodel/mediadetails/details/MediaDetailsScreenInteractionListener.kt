package com.berlin.aflami.viewmodel.mediadetails.details

import com.berlin.aflami.viewmodel.reusableinteractionlistener.addtofavourite.AddToFavouriteInteractionListener
import com.berlin.aflami.viewmodel.reusableinteractionlistener.createnewlist.CreateNewListInteractionListener
import com.berlin.aflami.viewmodel.reusableinteractionlistener.rate.RateInteractionListener

interface MediaInteractionListener :
    RateInteractionListener,
    AddToFavouriteInteractionListener,
    CreateNewListInteractionListener,
    ExtraMediaContentInteractionListener {

    fun onBackClicked()
    fun onPlayClicked(mediaId: Long)
    fun onReadMoreDescriptionClicked()
    fun onReadMoreReviewClicked(reviewId: String)
    fun onShowCastClicked()
    fun onMediaClicked(mediaId: Long)

}

interface ExtraMediaContentInteractionListener {
    fun onShowMoreMediaLikeThisClicked(mediaId: Long)
    fun onShowReviewsClicked(mediaId: Long)
    fun onShowMediaGalleryClicked(mediaId: Long)
    fun onShowCompanyProductionClicked()
}