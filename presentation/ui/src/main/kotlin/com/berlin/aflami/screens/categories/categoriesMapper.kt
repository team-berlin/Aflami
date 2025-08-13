package com.berlin.aflami.screens.categories

import com.berlin.designsystem.R


fun getMovieCategoryIcon(id: Int): Int {
    return when (id) {
        28 ->  R.drawable.action_category
        12 ->  R.drawable.adventure_category
        16 ->  R.drawable.animation_category
        35 ->  R.drawable.comedy_category
        80 ->  R.drawable.crime_category
        99 ->  R.drawable.documentary_category
        18 ->  R.drawable.drama_category
        10751 ->  R.drawable.family_category
        14 ->  R.drawable.fantasy_category
        36 ->  R.drawable.history_category
        27 ->  R.drawable.horror_category
        10402 ->  R.drawable.music_category
        9648 ->  R.drawable.mystery_category
        10749 ->  R.drawable.romance_category
        878 ->  R.drawable.science_fiction_category
        10770 ->  R.drawable.tv_movie_category
        53 ->  R.drawable.thriller_category
        10752 ->  R.drawable.war_category
        37 ->  R.drawable.western_cateogry
        else ->  R.drawable.all_movies
    }
}

fun getTvShowCategoryIcon(id: Int): Int {
    return when (id) {
        10759 ->  R.drawable.action_category
        16 ->  R.drawable.animation_category
        35 ->  R.drawable.comedy_category
        80 ->  R.drawable.crime_category
        99 ->  R.drawable.documentary_category
        18 ->  R.drawable.drama_category
        10751 ->  R.drawable.family_category
        10762 ->  R.drawable.kids_category
        9648 ->  R.drawable.mystery_category
        10763 ->  R.drawable.news_category
        10764 ->  R.drawable.reality_category
        10765 ->  R.drawable.science_fiction_category
        10766 ->  R.drawable.soap_category
        10767 ->  R.drawable.talk_category
        10768 ->  R.drawable.war_category
        37 ->  R.drawable.western_cateogry
        else ->  R.drawable.all_movies
    }
}