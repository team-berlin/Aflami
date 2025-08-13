package com.berlin.aflami.viewmodel.details.series

import com.berlin.aflami.viewmodel.details.common.MediaDetailsScreenInteractionListener

interface TvShowDetailsScreenInteractionListener : MediaDetailsScreenInteractionListener {
    fun onSeasonsClicked(tvShowId: Long, numberOfSeasons: Int)
}