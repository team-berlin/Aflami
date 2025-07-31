package com.berlin.aflami.navigation

import com.berlin.aflami.viewmodel.shareduistate.MediaType
import kotlinx.serialization.Serializable


sealed interface NavBar{

    @Serializable
    object HomeScreen:NavBar
    @Serializable
    object ListScreen: NavBar
    @Serializable
    object CategoriesScreen: NavBar
    @Serializable
    object ProfileScreen: NavBar
    @Serializable
    object GamesScreen: NavBar

}

@Serializable
object LoginScreen
@Serializable
data class WebViewScreen(val url: String)
@Serializable
object ContinueWatchingScreen
@Serializable
object TopRatingMediaScreen
@Serializable
data class MediaDetails(val mediaId: Long, val mediaType: MediaType)
@Serializable
data class CastScreen(val mediaId: Long, val mediaType: MediaType)

@Serializable
object SearchScreen
@Serializable
object SearchByCountryScreen
@Serializable
object SearchByActorScreen
