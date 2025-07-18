package com.berlin.aflami.viewmodel.mediadetails

import com.berlin.aflami.viewmodel.reusableinteractionlistener.addtofavourite.AddToFavouriteInteractionListener
import com.berlin.aflami.viewmodel.reusableinteractionlistener.createnewlist.CreateNewListInteractionListener
import com.berlin.aflami.viewmodel.reusableinteractionlistener.rate.RateInteractionListener
import com.berlin.aflami.viewmodel.mediadetails.TVShowSeasonsInteractionListener
import com.berlin.aflami.viewmodel.uistate.MediaType

interface MediaInteractionListener :
    RateInteractionListener,
    AddToFavouriteInteractionListener,
    CreateNewListInteractionListener,
    ExtraMediaContentInteractionListener {

    fun onBackClicked()
    fun onPlayClicked(id: Long)
    fun onReadMoreDescriptionClicked(id: Long)
    fun onReadMoreReviewClicked(id: Long)
    fun onShowCastClicked()

}

interface ExtraMediaContentInteractionListener : TVShowSeasonsInteractionListener  {
    fun onShowMoreMediaLikeThisClicked(mediaId: Long, mediaType: MediaType)
    fun onShowReviewsClicked()
    fun onShowMediaGalleryClicked(id:Long,mediaType: MediaType)
    fun onShowCompanyProductionClicked()
}

interface TVShowSeasonsInteractionListener {
    fun onShowAllSeasonsClicked(seriesId: Long,numberOfSeasons:Int)
    fun onShowSeasonEpisodesClicked(tvShowId: Long, seasonId: Long)
    fun onHideSeasonEpisodesClicked(seasonId: Long)
}