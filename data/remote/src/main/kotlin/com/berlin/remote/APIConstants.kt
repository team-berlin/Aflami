package com.berlin.remote

object ApiConstants  {

    // end points
    const val SEARCH_BY_COUNTRY = "discover/movie"
    const val SEARCH_BY_ACTOR = "search/person"

    const val SEARCH_BY_ACTOR_NAME = "search/person"

    const val SEARCH_MOVIE = "search/movie"

    const val SEARCH_TV = "search/tv"

    const val  SERIES_REVIEW = "tv/{series_id}/reviews"
    const val  MOVIE_REVIEW = "movie/{movie_id}/reviews"


    // parameters
    const val LANGUAGE = "language"
    const val WITH_ORIGIN_COUNTRY = "with_origin_country"
    const val QUERY = "query"

    const val ACTOR_NAME = "query"

    const val SERIES_MORE_LIKE_THIS="tv/{series_id}/similar"
    const val MOVIE_MORE_LIKE_THIS="movie/{movie_id}/similar"
    const val MOVIE_ID = "{movie_id}"
    const val SERIES_ID="{series_id}"
    const val EPISODE_SEASON_SERIES="tv/{series_id}/season/{season_number}"




    const val MOVIE_IMAGES = "movie/id/images"
    const val SERIES_IMAGES = "tv/id/images"

    const val SERIES_CAST="tv/{series_id}/credits"
    const val MOVIE_CAST="movie/{movie_id}/credits"
}