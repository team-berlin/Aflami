package com.berlin.aflami.viewmodel.mediadetails

import com.berlin.aflami.viewmodel.reusableinteractionlistener.addtofavourite.AddToFavouriteInteractionListener
import com.berlin.aflami.viewmodel.reusableinteractionlistener.createnewlist.CreateNewListInteractionListener
import com.berlin.aflami.viewmodel.reusableinteractionlistener.rate.RateInteractionListener
import com.berlin.aflami.viewmodel.mediadetails.TVShowSeasonsInteractionListener

interface MediaInteractionListener :
    RateInteractionListener,
    AddToFavouriteInteractionListener,
    CreateNewListInteractionListener,
    ExtraMediaContentInteractionListener {

    fun onBackClicked()
    fun onPlayClicked(id: Long)
    fun onReadMoreDescriptionClicked(id: Long)
    fun onShowCastClicked()

}

interface ExtraMediaContentInteractionListener : TVShowSeasonsInteractionListener  {
    fun onShowMoreMediaLikeThisClicked()
    fun onShowReviewsClicked()
    fun onShowMediaGalleryClicked(id:Long)
    fun onShowCompanyProductionClicked()
}

interface TVShowSeasonsInteractionListener {
    fun onShowAllSeasonsClicked()
    fun onShowSeasonEpisodesClicked(tvShowId: Long, seasonId: Long)
    fun onHideSeasonEpisodesClicked(seasonId: Long)
}