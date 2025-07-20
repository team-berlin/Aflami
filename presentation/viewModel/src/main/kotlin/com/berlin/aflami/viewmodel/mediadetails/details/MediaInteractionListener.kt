package com.berlin.aflami.viewmodel.mediadetails.details

import com.berlin.aflami.viewmodel.reusableinteractionlistener.addtofavourite.AddToFavouriteInteractionListener
import com.berlin.aflami.viewmodel.reusableinteractionlistener.createnewlist.CreateNewListInteractionListener
import com.berlin.aflami.viewmodel.reusableinteractionlistener.rate.RateInteractionListener
import com.berlin.aflami.viewmodel.shareduistate.MediaType

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

interface ExtraMediaContentInteractionListener : TVShowSeasonsInteractionListener {
    fun onShowMoreMediaLikeThisClicked(mediaId: Long, mediaType: MediaType)
    fun onShowReviewsClicked(mediaId: Long, mediaType: MediaType)
    fun onShowMediaGalleryClicked(id:Long,mediaType: MediaType)
    fun onShowCompanyProductionClicked()
}

interface TVShowSeasonsInteractionListener {
    fun onSeasonsClicked(seriesId: Long, numberOfSeasons:Int)
    fun onShowSeasonEpisodesClicked(tvShowId: Long, seasonId: Long)
    fun onHideSeasonEpisodesClicked(seasonId: Long)
}