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
fun getMovieGenreName(id: Int): Int {
    return when (id) {
        -1 ->  R.string.all
        28 ->  R.string.action
        12 ->  R.string.adventure
        16 ->  R.string.animation
        35 ->  R.string.comedy
        80 ->  R.string.crime
        99 ->  R.string.documentary
        18 ->  R.string.drama
        10751 ->  R.string.family
        14 ->  R.string.fantasy
        36 ->  R.string.history
        27 ->  R.string.horror
        10402 ->  R.string.music
        9648 ->  R.string.mystery
        10749 ->  R.string.romance
        878 ->  R.string.science_fiction
        10770 ->  R.string.television_movie
        53 ->  R.string.thriller
        10752 ->  R.string.war
        37 ->  R.string.western
        else ->  R.string.all
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
fun getTvShowGenreName(id: Int):Int{
    return when (id) {
        -1 -> R.string.all
        10759 ->  R.string.action
        16 ->  R.string.animation
        35 ->  R.string.comedy
        80 ->  R.string.crime
        99 ->  R.string.documentary
        18 ->  R.string.drama
        10751 ->  R.string.family
        10762 ->  R.string.kids
        9648 ->  R.string.mystery
        10763 ->  R.string.news
        10764 ->  R.string.reality
        10765 ->  R.string.science_fiction
        10766 ->  R.string.soap
        10767 ->  R.string.talk
        10768 ->  R.string.war
        37 ->  R.string.western
        else ->  R.string.all
    }
}