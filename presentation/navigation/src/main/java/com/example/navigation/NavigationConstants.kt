package com.example.navigation

object NavigationConstants{

    object Destination{
        const val SEARCH_SCREEN="searchScreen"
        const val SEARCH_BY_COUNTRY_SCREEN="searchByCountryScreen"
        const val SEARCH_BY_ACTOR_NAME_SCREEN="searchByActorNameScreen"
        const val MEDIA_DETAILS_SCREEN="mediaDetailsScreen/{id}/{media_type}"
        const val CAST_SCREEN="castScreen/{id}/{media_type}"
        const val LOGIN_SCREEN="login"
        const val WEB_VIEW_SCREEN="webViewScreen/{url}"
        const val WATCHED_MEDIA_DETAILS="watchedMediaDetails/{id}/{media_type}"
        const val HOME_SCREEN="homeScreen"
        const val LISTS_SCREEN="listsScreen"
        const val GAMES_SCREEN="gamesScreen"
        const val PROFILE_SCREEN="profileScreen"
        const val CATEGORY_SCREEN="categoryScreen"


    }
    object Routes{
        const val MEDIA_DETAILS_ROUTE="mediaDetailsScreen"
        const val CAST_ROUTE="castScreen"
        const val WEB_VIEW_ROUTE="webViewScreen"
        const val LOGIN_ROUTE = "login"
    }
}
