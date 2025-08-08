package com.berlin.remote.network

object ApiConstants {
    // Query parameters
    const val QUERY = "query"
    const val PAGE = "page"
    const val WITH_ORIGIN_COUNTRY = "with_origin_country"
    const val WITH_GENRES = "with_genres"
    const val MOVIE_ID = "movie_id"
    const val SERIES_ID = "series_id"
    const val ACCOUNT_ID = "account_id"
    const val SEASON_NUMBER = "season_number"
    const val RELEASE_DATE_GTE = "release_date.gte"
    const val RELEASE_DATE_LTE = "release_date.lte"
    const val QUERY_SORT_BY = "sort_by"
    const val QUERY_INCLUDE_ADULT = "include_adult"
    const val QUERY_INCLUDE_VIDEO = "include_video"
    const val QUERY_WITH_RELEASE_TYPE = "with_release_type"
    const val SESSION_ID = "session_id"
    const val REQUEST_TOKEN = "request_token"
    const val LOGIN_USERNAME = "username"
    const val LOGIN_PASSWORD = "password"

    // Search endpoints
    const val SEARCH_BY_COUNTRY = "discover/movie"
    const val SEARCH_BY_ACTOR = "search/person"
    const val SEARCH_MOVIE = "search/movie"
    const val SEARCH_TV = "search/tv"

    // Movie endpoints
    const val MOVIE_DETAILS = "movie/{$MOVIE_ID}"
    const val MOVIE_IMAGES = "movie/{$MOVIE_ID}/images"
    const val MOVIE_CAST = "movie/{$MOVIE_ID}/credits"
    const val MOVIE_MORE_LIKE_THIS = "movie/{$MOVIE_ID}/similar"
    const val MOVIE_REVIEW = "movie/{$MOVIE_ID}/reviews"
    const val MOVIE_VIDEO_DETAILS = "movie/{$MOVIE_ID}/videos"
    const val MOVIE_GENRES = "genre/movie/list"
    const val MOVIE_UPCOMING = "movie/upcoming"
    const val POPULAR_MOVIES = "movie/popular"
    const val DISCOVER_MOVIE = "discover/movie"
    const val TOP_RATED_MOVIES = "movie/top_rated"

    // TV show endpoints
    const val SERIES_DETAILS = "tv/{$SERIES_ID}"
    const val SERIES_IMAGES = "tv/{$SERIES_ID}/images"
    const val SERIES_CAST = "tv/{$SERIES_ID}/credits"
    const val SERIES_MORE_LIKE_THIS = "tv/{$SERIES_ID}/similar"
    const val SERIES_REVIEW = "tv/{$SERIES_ID}/reviews"
    const val EPISODE_SEASON_SERIES = "tv/{$SERIES_ID}/season/{$SEASON_NUMBER}"
    const val SERIES_GENRES = "genre/tv/list"
    const val POPULAR_TV_SHOWS = "tv/popular"
    const val TOP_RATED_SERIES = "tv/top_rated"
    const val TV_VIDEO_DETAILS = "tv/{$SERIES_ID}/videos"

    // Authentication endpoints
    const val NEW_TOKEN_ENDPOINT = "authentication/token/new"
    const val CREATE_SESSION_WITH_LOGIN_ENDPOINT = "authentication/token/validate_with_login"
    const val CREATE_SESSION_ENDPOINT = "authentication/session/new"
    const val DELETE_SESSION_ENDPOINT = "authentication/session"
    const val ACCOUNT = "account"
    //endregion

    //query constants
    const val SORT_BY_POPULARITY_DESC = "popularity.desc"
    const val INCLUDE_ADULT_DEFAULT = false
    const val INCLUDE_VIDEO_DEFAULT = false
    const val RELEASE_TYPE_THEATRICAL_AND_LIMITED = "2|3"

    // Rating endpoints
    const val RATE_MOVIE = "movie/{$MOVIE_ID}/rating"
    const val RATE_TV_SHOW = "tv/{$SERIES_ID}/rating"
    const val RATED_MOVIES = "account/{$ACCOUNT_ID}/rated/movies"
    const val RATED_TV_SHOWS = "account/{$ACCOUNT_ID}/rated/tv"

}