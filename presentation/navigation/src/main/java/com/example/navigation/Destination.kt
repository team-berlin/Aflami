package com.example.navigation

import com.example.navigation.NavigationConstants.Destination.CAST_SCREEN
import com.example.navigation.NavigationConstants.Destination.MEDIA_DETAILS_SCREEN
import com.example.navigation.NavigationConstants.Destination.SEARCH_BY_ACTOR_NAME_SCREEN
import com.example.navigation.NavigationConstants.Destination.SEARCH_BY_COUNTRY_SCREEN
import com.example.navigation.NavigationConstants.Destination.SEARCH_SCREEN
import com.example.navigation.NavigationConstants.Routes.CAST_ROUTE
import com.example.navigation.NavigationConstants.Routes.MEDIA_DETAILS_ROUTE

sealed class Destination(val route: String) {
    object SearchScreen : Destination(SEARCH_SCREEN)
    object SearchByCountryScreen : Destination(SEARCH_BY_COUNTRY_SCREEN)
    object SearchByActorNameScreen : Destination(SEARCH_BY_ACTOR_NAME_SCREEN)

    object MediaDetailsScreen : Destination(MEDIA_DETAILS_SCREEN){
        fun route(id: Long, mediaType: String): String {
            return "$MEDIA_DETAILS_ROUTE/$id/$mediaType"
        }
    }
    object CastScreen : Destination(CAST_SCREEN){
        fun route(id:Long, mediaType:String): String {
            return "$CAST_ROUTE/$id/$mediaType"
        }
    }
}

