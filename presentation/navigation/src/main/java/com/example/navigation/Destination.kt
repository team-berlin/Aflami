package com.example.navigation

sealed class Destination(val route: String) {
    object SearchScreen : Destination("searchScreen")
    object WorldTourScreen : Destination("worldTourScreen")
    object SearchByActorNameScreen : Destination("searchByActorNameScreen")
}

