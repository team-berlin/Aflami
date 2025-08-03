package com.berlin.aflami.viewmodel.mediadetails.details.series

import com.berlin.aflami.viewmodel.shareduistate.MediaType

sealed interface TvShowDetailsScreenEffect {
    object NavigateBack : TvShowDetailsScreenEffect
    data class PlayMedia(val videoUrl: String) : TvShowDetailsScreenEffect
    data class NavigateToShowAllCastScreen(
        val mediaId: Long,
        val mediaType: MediaType,
    ) : TvShowDetailsScreenEffect

    data class ShowRatingDialog(val mediaId: Long) : TvShowDetailsScreenEffect
    data class ShowAddToFavoriteListDialog(
        val favouriteListId: Int,
        val mediaId: Int,
    ) : TvShowDetailsScreenEffect
}