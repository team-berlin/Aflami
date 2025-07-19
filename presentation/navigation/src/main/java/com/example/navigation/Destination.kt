package com.example.navigation

sealed class Destination(val route: String) {
    object SearchScreen : Destination("searchScreen")
    object SearchByCountryScreen : Destination("searchByCountryScreen")
    object SearchByActorNameScreen : Destination("searchByActorNameScreen")
}

