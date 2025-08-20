package com.berlin.aflami.navigation

import com.berlin.aflami.viewmodel.game.GameType
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationBarDestinations {

    @Serializable
    object HomeScreen : NavigationBarDestinations

    @Serializable
    object ListsScreenNoArgs : NavigationBarDestinations

    @Serializable
    object CategoriesScreen : NavigationBarDestinations

    @Serializable
    object ProfileScreen : NavigationBarDestinations

    @Serializable
    object GamesScreen : NavigationBarDestinations

}

val bottomNavBarDestinationsMap = mapOf(
    NavigationBarDestinations.HomeScreen::class.qualifiedName to NavigationBarDestinations.HomeScreen,
    HomeScreenWithArgs::class.qualifiedName to NavigationBarDestinations.HomeScreen,
    ListsScreenWithArgs::class.qualifiedName to NavigationBarDestinations.ListsScreenNoArgs,
    NavigationBarDestinations.ListsScreenNoArgs::class.qualifiedName to NavigationBarDestinations.ListsScreenNoArgs,
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
data class MoviesByCategoryDestination(val categoryId: Long)

@Serializable
data class TVShowsByCategoryDestination(val categoryId: Long)

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
class GuessGameDestination(
    val gameType: GameType,
    val numberOfQuestion: Int,
    val numberOfPoint: Int,
    val time: Int
)

@Serializable
class GameResultDestination(
    val totalTime:Int,
    val gameType :GameType,
    val numberOfQuestion :Int,
    val numberOfPoints :Int,
    val totalPoint :Int,
    val time:Int
)

@Serializable
object MyRatingDestination

@Serializable
data class ListDetailsDestination(val listId: Int, val listTitle: String)

@Serializable
data class ListsScreenWithArgs(
    val showEditSheet: Boolean? = null,
    val listTitle: String? = null,
    val requiredToEditListId: Int? = null,
    val showDeletedSnackBar: Boolean? = null,
    val isListDeletedSuccessfully: Boolean? = null,
)
@Serializable
data class HomeScreenWithArgs(val isLoggedIn: Boolean?=false) : NavigationBarDestinations
