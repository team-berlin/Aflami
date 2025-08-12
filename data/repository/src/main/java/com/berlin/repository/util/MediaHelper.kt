package com.berlin.repository.util

import com.berlin.repository.util.MediaUrls.GRAVATAR_BASE
import com.berlin.repository.util.MediaUrls.TMDB_IMAGE_BASE
import com.berlin.repository.util.MediaUrls.YOUTUBE_WATCH_BASE

fun tmdbImageUrl(path: String?, size: MediaUrls.TmdbImageSize = MediaUrls.TmdbImageSize.W500): String? {
    if (path.isNullOrBlank()) return null
    val normalized = if (path.startsWith("/")) path else "/$path"
    return "$TMDB_IMAGE_BASE${size.path}$normalized"
}

fun gravatarUrl(hash: String?): String? =
    hash?.takeIf { it.isNotBlank() }?.let { "$GRAVATAR_BASE$it" }

fun youtubeUrl(key: String?): String? =
    key?.takeIf { it.isNotBlank() }?.let { "$YOUTUBE_WATCH_BASE$it" }