package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.TVShowsByCategoryDestination
import com.berlin.aflami.screens.categories.TVShowByCategoryScreen

fun NavGraphBuilder.tvShowByCategoryRoute() =
    composable<TVShowsByCategoryDestination>
    {
        TVShowByCategoryScreen()
    }

