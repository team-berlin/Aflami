package com.berlin.remote.network

object ApiConstants {
    //region search
    const val QUERY = "query"
    const val PAGE = "page"
    const val SEARCH_BY_COUNTRY = "discover/movie"
    const val WITH_ORIGIN_COUNTRY = "with_origin_country"
    const val SEARCH_BY_ACTOR = "search/person"
    const val SEARCH_MOVIE = "search/movie"
    const val SEARCH_TV = "search/tv"

    //endregion
    const val LANGUAGE = "language"
    const val WITH_GENRES = "with_genres"

    //region details
    const val MOVIE_ID = "movie_id"
    const val MOVIE_DETAILS = "movie/{movie_id}"
    const val MOVIE_IMAGES = "movie/{movie_id}/images"
    const val TOP_RATED_MOVIES = "movie/top_rated"
    const val TOP_RATED_SERIES = "tv/top_rated"
    const val MOVIE_CAST = "movie/{movie_id}/credits"
    const val MOVIE_MORE_LIKE_THIS = "movie/{movie_id}/similar"
    const val MOVIE_REVIEW = "movie/{movie_id}/reviews"
    const val MOVIE_GENRES = "genre/movie/list"
    const val MOVIE_UPCOMING = "movie/upcoming"
    const val POPULAR_MOVIES = "movie/popular"
    const val DISCOVER_MOVIE = "discover/movie"
    //endregion

    //region tv shows
    const val SERIES_ID = "series_id"
    const val SERIES_DETAILS = "tv/{series_id}"
    const val SERIES_IMAGES = "tv/{series_id}/images"
    const val SERIES_CAST = "tv/{series_id}/credits"
    const val SERIES_MORE_LIKE_THIS = "tv/{series_id}/similar"
    const val SERIES_REVIEW = "tv/{series_id}/reviews"
    const val EPISODE_SEASON_SERIES = "tv/{series_id}/season/{season_number}"
    const val SEASON_NUMBER = "season_number"
    const val POPULAR_TV_SHOWS = "tv/popular"
    const val SERIES_GENRES = "genre/tv/list"

    //endregion

    //region auth
    const val NEW_TOKEN_ENDPOINT = "authentication/token/new"
    const val CREATE_SESSION_WITH_LOGIN_ENDPOINT = "authentication/token/validate_with_login"
    const val CREATE_SESSION_ENDPOINT = "authentication/session/new"
    const val DELETE_SESSION_ENDPOINT = "authentication/session"
    const val SESSION_ID = "session_id"
    const val REQUEST_TOKEN = "request_token"
    const val LOGIN_USERNAME = "username"
    const val LOGIN_PASSWORD = "password"
    //endregion
}