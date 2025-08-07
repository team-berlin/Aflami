package com.berlin.aflami.screens.mediadetails.components

import com.berlin.aflami.viewmodel.details.movie.MovieDetailsTabs
import com.berlin.aflami.viewmodel.details.series.TVShowDetailsTabs
import com.berlin.ui.R

fun movieDetailsTabsMapper(tab: MovieDetailsTabs): Int {
    return when (tab) {
        MovieDetailsTabs.MORE_LIKE_THIS -> R.string.more_like_this
        MovieDetailsTabs.REVIEWS -> R.string.reviews
        MovieDetailsTabs.GALLERY -> R.string.gallery
        MovieDetailsTabs.COMPANY_PRODUCTION -> R.string.company_production
    }
}

fun getMovieDetailsTabsIcon(tab: MovieDetailsTabs): Int {
    return when (tab) {
        MovieDetailsTabs.MORE_LIKE_THIS -> R.drawable.ic_camera_video
        MovieDetailsTabs.REVIEWS -> R.drawable.ic_star_review
        MovieDetailsTabs.GALLERY -> R.drawable.ic_album
        MovieDetailsTabs.COMPANY_PRODUCTION -> R.drawable.ic_city
    }
}
fun tvShowDetailsTabsMapper(tab: TVShowDetailsTabs): Int {
    return when (tab) {
        TVShowDetailsTabs.MORE_LIKE_THIS -> R.string.more_like_this
        TVShowDetailsTabs.REVIEWS -> R.string.reviews
        TVShowDetailsTabs.GALLERY -> R.string.gallery
        TVShowDetailsTabs.COMPANY_PRODUCTION -> R.string.company_production
        TVShowDetailsTabs.SEASONS -> R.string.season
    }
}

fun getTVShowDetailsTabsIcon(tab: TVShowDetailsTabs): Int {
    return when (tab) {
        TVShowDetailsTabs.MORE_LIKE_THIS -> R.drawable.ic_camera_video
        TVShowDetailsTabs.REVIEWS -> R.drawable.ic_star_review
        TVShowDetailsTabs.GALLERY -> R.drawable.ic_album
        TVShowDetailsTabs.COMPANY_PRODUCTION -> R.drawable.ic_city
        TVShowDetailsTabs.SEASONS -> R.drawable.ic_season
    }
}