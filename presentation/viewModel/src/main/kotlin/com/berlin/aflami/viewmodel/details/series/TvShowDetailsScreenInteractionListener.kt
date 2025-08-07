package com.berlin.aflami.viewmodel.details.series

import com.berlin.aflami.viewmodel.details.common.MediaInteractionListener

interface TvShowDetailsScreenInteractionListener : MediaInteractionListener {
    fun onSeasonsClicked(tvShowId: Long, numberOfSeasons: Int)
}