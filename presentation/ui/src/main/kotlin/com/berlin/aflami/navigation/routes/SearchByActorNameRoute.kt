package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.SearchByActorScreen
import com.berlin.aflami.screens.search.actor.SearchByActorNameScreen

fun NavGraphBuilder.searchByActorNameRoute() {
    composable<SearchByActorScreen> {
        SearchByActorNameScreen()
    }
}