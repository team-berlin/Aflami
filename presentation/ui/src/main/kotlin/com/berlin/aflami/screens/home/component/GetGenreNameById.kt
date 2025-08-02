package com.berlin.aflami.screens.home.component

import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.designsystem.R

fun getGenreNameById(id: Int, mediaType: MediaType): Int {
    val movieGenres = mapOf(
        28 to R.string.action,
        12 to R.string.adventure,
        16 to R.string.animation,
        35 to R.string.comedy,
        80 to R.string.crime,
        99 to R.string.documentary,
        18 to R.string.drama,
        10751 to R.string.family,
        14 to R.string.fantasy,
        36 to R.string.history,
        27 to R.string.horror,
        10402 to R.string.music,
        9648 to R.string.mystery,
        10749 to R.string.romance,
        878 to R.string.science_fiction,
        10770 to R.string.tv_movie,
        53 to R.string.thriller,
        10752 to R.string.war,
        37 to R.string.western
    )

    val tvGenres = mapOf(
        10759 to R.string.action_adventure,
        16 to R.string.animation,
        35 to R.string.comedy,
        80 to R.string.crime,
        99 to R.string.documentary,
        18 to R.string.drama,
        10751 to R.string.family,
        10762 to R.string.kids,
        9648 to R.string.mystery,
        10763 to R.string.news,
        10764 to R.string.reality,
        10765 to R.string.sci_fi_fantasy,
        10766 to R.string.soap,
        10767 to R.string.talk,
        10768 to R.string.war_politics,
        37 to R.string.western
    )

    return when (mediaType) {
        MediaType.MOVIE -> movieGenres[id] ?: R.string.all
        MediaType.TVSHOW -> tvGenres[id] ?: R.string.all
    }
}
