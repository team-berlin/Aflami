package com.berlin.aflami.screens.search
import com.berlin.designsystem.R


fun getMovieGenreIcon(id: Int): Int {
    return when (id) {
        -1 ->  R.drawable.all_movies
        28 ->  R.drawable.action
        12 ->  R.drawable.adventure
        16 ->  R.drawable.animation
        35 ->  R.drawable.comedy
        80 ->  R.drawable.crime
        99 ->  R.drawable.documentary
        18 ->  R.drawable.drama
        10751 ->  R.drawable.family
        14 ->  R.drawable.fantasy
        36 ->  R.drawable.history
        27 ->  R.drawable.horror
        10402 ->  R.drawable.music
        9648 ->  R.drawable.mystery
        10749 ->  R.drawable.romance
        878 ->  R.drawable.science_fiction
        10770 ->  R.drawable.television_movie
        53 ->  R.drawable.thriller
        10752 ->  R.drawable.war
        37 ->  R.drawable.western
        else ->  R.drawable.all_movies
    }
}

fun getTvShowGenreIcon(id: Int): Int {
    return when (id) {
        -1 -> R.drawable.all_movies
        10759 ->  R.drawable.action
        16 ->  R.drawable.animation
        35 ->  R.drawable.comedy
        80 ->  R.drawable.crime
        99 ->  R.drawable.documentary
        18 ->  R.drawable.drama
        10751 ->  R.drawable.family
        10762 ->  R.drawable.kids
        9648 ->  R.drawable.mystery
        10763 ->  R.drawable.news
        10764 ->  R.drawable.reality
        10765 ->  R.drawable.science_fiction
        10766 ->  R.drawable.soap
        10767 ->  R.drawable.talk
        10768 ->  R.drawable.war
        37 ->  R.drawable.western
        else ->  R.drawable.all_movies
    }
}