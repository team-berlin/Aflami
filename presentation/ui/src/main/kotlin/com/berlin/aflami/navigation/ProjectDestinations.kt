package com.berlin.aflami.navigation

import com.berlin.aflami.viewmodel.shareduistate.MediaType
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationBarDestinations {

    @Serializable
    object HomeScreen : NavigationBarDestinations

    @Serializable
    object ListScreen : NavigationBarDestinations

    @Serializable
    object CategoriesScreen : NavigationBarDestinations

    @Serializable
    object ProfileScreen : NavigationBarDestinations

    @Serializable
    object GamesScreen : NavigationBarDestinations

}

val bottomNavBarDestinationsMap = mapOf(
    NavigationBarDestinations.HomeScreen::class.qualifiedName to NavigationBarDestinations.HomeScreen,
    NavigationBarDestinations.ListScreen::class.qualifiedName to NavigationBarDestinations.ListScreen,
    NavigationBarDestinations.CategoriesScreen::class.qualifiedName to NavigationBarDestinations.CategoriesScreen,
    NavigationBarDestinations.GamesScreen::class.qualifiedName to NavigationBarDestinations.GamesScreen,
    NavigationBarDestinations.ProfileScreen::class.qualifiedName to NavigationBarDestinations.ProfileScreen
)

@Serializable
object OnBoardingDestination

@Serializable
object LoginDestination

@Serializable
data class WebViewDestination(val url: String)

@Serializable
object ContinueWatchingDestination

@Serializable
object TopRatingMediaDestination

@Serializable
data class MovieDetailsDestination(val movieId: Long)

@Serializable
data class TVShowDetailsDestination(val tvShowId: Long)

@Serializable
data class MediaByCategoryDestination(val categoryId: Long, val mediaType: MediaType)

@Serializable
data class CastDestination(val mediaId: Long, val mediaType: MediaType)

@Serializable
data class VideoWebViewDestination(val url: String)

@Serializable
object SearchDestination

@Serializable
object SearchByCountryDestination

@Serializable
object SearchByActorDestination

@Serializable
object WatchHistoryDestination

@Serializable
data class ListDetailsDestination(val listId: Int)

@Serializable
data class ListsScreen(
    val showEditSheet: Boolean = false,
    val requiredToEditListId: Int? = null,
    val showDeletedSnackBar: Boolean? = false,
)
