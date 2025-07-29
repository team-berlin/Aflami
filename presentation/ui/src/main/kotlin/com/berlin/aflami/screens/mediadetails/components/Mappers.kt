package com.berlin.aflami.screens.mediadetails.components

import com.berlin.aflami.viewmodel.mediadetails.MovieDetailsTabs
import com.berlin.ui.R

fun movieDetailsTabsMapper(tab: MovieDetailsTabs): Int {
    return when (tab) {
        MovieDetailsTabs.MORE_LIKE_THIS -> R.string.more_like_this
        MovieDetailsTabs.REVIEWS -> R.string.reviews
        MovieDetailsTabs.GALLERY -> R.string.gallery
        MovieDetailsTabs.COMPANY_PRODUCTION -> R.string.company_production
        MovieDetailsTabs.SEASON -> R.string.season
    }
}

fun getMovieDetailsTabsIcon(tab: MovieDetailsTabs): Int {
    return when (tab) {
        MovieDetailsTabs.MORE_LIKE_THIS -> R.drawable.ic_camera_video
        MovieDetailsTabs.REVIEWS -> R.drawable.ic_star_review
        MovieDetailsTabs.GALLERY -> R.drawable.ic_album
        MovieDetailsTabs.COMPANY_PRODUCTION -> R.drawable.ic_city
        MovieDetailsTabs.SEASON -> R.drawable.ic_season
    }
}