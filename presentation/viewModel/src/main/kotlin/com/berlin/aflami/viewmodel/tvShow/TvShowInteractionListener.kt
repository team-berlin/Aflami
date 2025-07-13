package com.berlin.aflami.viewmodel.tvShow

import com.berlin.aflami.viewmodel.mediaDetails.MediaInteractionListener

interface TvShowInteractionListener :
    MediaInteractionListener,
    TvShowSeasonsInteractionListener

interface TvShowSeasonsInteractionListener {
    fun onShowAllSeasonsClicked()
    fun onShowSeasonEpisodesClicked(tvShowId: Long, seasonId: Long)
    fun onHideSeasonEpisodesClicked(seasonId: Long)
}