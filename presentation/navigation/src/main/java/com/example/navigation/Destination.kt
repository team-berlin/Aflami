package com.example.navigation


enum class MediaType{
    MOVIE,
    TV_SHOW
}
sealed class Destination(val route: String) {
    object SearchScreen : Destination("searchScreen")
    object SearchByCountryScreen : Destination("searchByCountryScreen")
    object SearchByActorNameScreen : Destination("searchByActorNameScreen")

    object MediaDetailsScreen : Destination("mediaDetailsScreen/{id}/{media_type}"){
        fun route(id:Long, media_type:MediaType): String {
            return "mediaDetailsScreen/$id/$media_type"
        }
    }
    object CastScreen : Destination("castScreen/{id}/{media_type}"){
        fun route(id:Long, media_type:MediaType): String {
            return "castScreen/$id/$media_type"
        }
    }
}

