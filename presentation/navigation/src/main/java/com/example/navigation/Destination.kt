package com.example.navigation

import com.example.navigation.NavigationConstants.Destination.CAST_SCREEN
import com.example.navigation.NavigationConstants.Destination.CATEGORY_SCREEN
import com.example.navigation.NavigationConstants.Destination.GAMES_SCREEN
import com.example.navigation.NavigationConstants.Destination.HOME_SCREEN
import com.example.navigation.NavigationConstants.Destination.LISTS_SCREEN
import com.example.navigation.NavigationConstants.Destination.LOGIN_SCREEN
import com.example.navigation.NavigationConstants.Destination.MEDIA_DETAILS_SCREEN
import com.example.navigation.NavigationConstants.Destination.PROFILE_SCREEN
import com.example.navigation.NavigationConstants.Destination.SEARCH_BY_ACTOR_NAME_SCREEN
import com.example.navigation.NavigationConstants.Destination.SEARCH_BY_COUNTRY_SCREEN
import com.example.navigation.NavigationConstants.Destination.SEARCH_SCREEN
import com.example.navigation.NavigationConstants.Destination.WATCHED_MEDIA_DETAILS
import com.example.navigation.NavigationConstants.Destination.WEB_VIEW_SCREEN
import com.example.navigation.NavigationConstants.Routes.CAST_ROUTE
import com.example.navigation.NavigationConstants.Routes.MEDIA_DETAILS_ROUTE

sealed class Destination(val route: String) {
    object SearchScreen : Destination(SEARCH_SCREEN)
    object SearchByCountryScreen : Destination(SEARCH_BY_COUNTRY_SCREEN)
    object SearchByActorNameScreen : Destination(SEARCH_BY_ACTOR_NAME_SCREEN)
    object MediaDetailsScreen : Destination(MEDIA_DETAILS_SCREEN) {
        fun route(id: Long, mediaType: String): String {
            return "$MEDIA_DETAILS_ROUTE/$id/$mediaType"
        }
    }
    object CastScreen : Destination(CAST_SCREEN) {
        fun route(id: Long, mediaType: String): String {
            return "$CAST_ROUTE/$id/$mediaType"
        }
    }
    object LoginScreen : Destination(LOGIN_SCREEN)
    object WebViewScreen : Destination(WEB_VIEW_SCREEN)
    object WatchedMediaDetails : Destination(WATCHED_MEDIA_DETAILS)
    object HomeScreen : Destination(HOME_SCREEN)
    object GamesScreen : Destination(GAMES_SCREEN)
    object ProfileScreen : Destination(PROFILE_SCREEN)
    object ListsScreen : Destination(LISTS_SCREEN)
    object CategoriesScreen : Destination(CATEGORY_SCREEN)
}

