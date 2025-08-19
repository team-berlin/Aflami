package com.berlin.repository.util

object MediaUrls {
    const val TMDB_IMAGE_BASE = "https://image.tmdb.org/t/p"
    const val GRAVATAR_BASE = "https://www.gravatar.com/avatar/"
    const val YOUTUBE_WATCH_BASE = "https://www.youtube.com/watch?v="


    enum class TmdbImageSize(val path: String) {
        W185("/w185"),
        W500("/w500"),
    }
}