package com.berlin.aflami.viewmodel.mediadetails.details

import com.berlin.aflami.viewmodel.mediadetails.MovieDetailsTabs
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
    fun onPlayClicked(id: Long, mediaType: MediaType)
    fun onReadMoreDescriptionClicked()
    fun onReadMoreReviewClicked(id: String)
    fun onShowCastClicked()
    fun onMediaClicked(mediaId: Long, mediaType: MediaType)
    fun onLoginButtonClicked()

}

interface ExtraMediaContentInteractionListener : TVShowSeasonsInteractionListener {
    fun onShowMoreMediaLikeThisClicked(mediaId: Long, mediaType: MediaType)
    fun onShowReviewsClicked(mediaId: Long, mediaType: MediaType)
    fun onShowMediaGalleryClicked(id:Long,mediaType: MediaType)
    fun onShowCompanyProductionClicked()
    fun onTabSelected(tab: MovieDetailsTabs)
}

interface TVShowSeasonsInteractionListener {
    fun onSeasonsClicked(seriesId: Long, numberOfSeasons:Int)
}