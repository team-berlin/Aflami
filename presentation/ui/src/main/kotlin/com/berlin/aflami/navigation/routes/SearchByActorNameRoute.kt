package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.SearchByActorDestination
import com.berlin.aflami.screens.search.actor.SearchByActorNameScreen

fun NavGraphBuilder.searchByActorNameRoute() = composable<SearchByActorDestination> {
    SearchByActorNameScreen()
}