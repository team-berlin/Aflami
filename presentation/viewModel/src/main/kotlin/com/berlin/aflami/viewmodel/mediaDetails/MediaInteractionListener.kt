package com.berlin.aflami.viewmodel.mediaDetails

import com.berlin.aflami.viewmodel.reusableinteractionlistener.addtofavourite.AddToFavouriteInteractionListener
import com.berlin.aflami.viewmodel.reusableinteractionlistener.createnewlist.CreateNewListInteractionListener
import com.berlin.aflami.viewmodel.reusableinteractionlistener.rate.RateInteractionListener

interface MediaInteractionListener :
    RateInteractionListener,
    AddToFavouriteInteractionListener,
    CreateNewListInteractionListener,
    ExtraMediaContentInteractionListener {

    fun onBackClicked()
    fun onPlayClicked(id: Long)
    fun onReadMoreDescriptionClicked(id: Long)
    fun onShowCastClicked(id: Long)

}

interface ExtraMediaContentInteractionListener {
    fun onShowMoreMediaLikeThisClicked()
    fun onShowReviewsClicked()
    fun onShowMediaGalleryClicked()
    fun onShowComponyProductionClicked()
}