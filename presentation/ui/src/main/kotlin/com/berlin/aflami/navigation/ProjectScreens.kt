package com.berlin.aflami.navigation

import com.berlin.aflami.viewmodel.shareduistate.MediaType
import kotlinx.serialization.Serializable

@Serializable
object LoginScreen
@Serializable
data class WebViewScreen(val url: String)
@Serializable
object HomeScreen
@Serializable
object ContinueWatchingScreen
@Serializable
object TopRatingMediaScreen
@Serializable
data class MediaDetailsScreen(val mediaId: Long, val mediaType: MediaType)
@Serializable
data class CastScreen(val mediaId: Long, val mediaType: MediaType)

@Serializable
object SearchScreen
@Serializable
object SearchByCountryScreen
@Serializable
object SearchByActorScreen