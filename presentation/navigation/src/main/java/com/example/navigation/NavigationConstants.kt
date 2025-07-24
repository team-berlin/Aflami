package com.example.navigation

object NavigationConstants{

    object Destination{
        const val SEARCH_SCREEN="searchScreen"
        const val SEARCH_BY_COUNTRY_SCREEN="searchByCountryScreen"
        const val SEARCH_BY_ACTOR_NAME_SCREEN="searchByActorNameScreen"
        const val MEDIA_DETAILS_SCREEN="mediaDetailsScreen/{id}/{media_type}"
        const val CAST_SCREEN="castScreen/{id}/{media_type}"
        const val HOME_SCREEN="homeScreen"

    }
    object Routes{
        const val MEDIA_DETAILS_ROUTE="mediaDetailsScreen"
        const val CAST_ROUTE="castScreen"
    }
}
