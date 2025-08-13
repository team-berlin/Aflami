package com.berlin.aflami.viewmodel.categories.movie


sealed class MediaByCategoryScreenEffect {
    object NavigateBack : MediaByCategoryScreenEffect()
    data class NavigateToMediaDetails(val mediaId: Long) : MediaByCategoryScreenEffect()
}