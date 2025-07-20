package com.berlin.aflami.viewmodel.mediadetails.details

sealed class MediaDetailsScreenEffect {
    object NavigateBack: MediaDetailsScreenEffect()
    data class PlayMedia(val id: Long): MediaDetailsScreenEffect()
    data object NavigateToShowAllCastScreen: MediaDetailsScreenEffect()
    data class ShowRatingSheet(val id: Long) : MediaDetailsScreenEffect()
    data class ShowAddToFavoriteListSheet(
        val favouriteListId: Int,
        val mediaId: Int
    ) : MediaDetailsScreenEffect()
}
