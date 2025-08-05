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
    const val WITH_GENRES = "with_genres"
    //endregion

    //region movie details
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
    const val TV_VIDEO_DETAILS = "tv/{series_id}/videos"
    const val MOVIE_VIDEO_DETAILS = "movie/{movie_id}/videos"
    //endregion

    //region tv shows details
    const val TV_SHOW_ID = "series_id"
    const val TV_SHOW_DETAILS = "tv/{series_id}"
    const val TV_SHOW_IMAGES = "tv/{series_id}/images"
    const val TV_SHOW_CAST = "tv/{series_id}/credits"
    const val TV_SHOW_MORE_LIKE_THIS = "tv/{series_id}/similar"
    const val TV_SHOW_REVIEW = "tv/{series_id}/reviews"
    const val EPISODE_SEASON_SERIES = "tv/{series_id}/season/{season_number}"
    const val SEASON_NUMBER = "season_number"
    const val POPULAR_TV_SHOWS = "tv/popular"
    const val TV_SHOW_GENRES = "genre/tv/list"

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
    const val ACCOUNT = "account"
    //endregion
}