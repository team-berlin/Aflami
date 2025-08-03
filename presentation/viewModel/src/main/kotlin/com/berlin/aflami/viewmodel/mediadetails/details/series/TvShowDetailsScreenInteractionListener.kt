package com.berlin.aflami.viewmodel.mediadetails.details.series

import com.berlin.aflami.viewmodel.mediadetails.details.MediaInteractionListener

interface TvShowDetailsScreenInteractionListener : MediaInteractionListener {
    fun onSeasonsClicked(seriesId: Long, numberOfSeasons: Int)
}