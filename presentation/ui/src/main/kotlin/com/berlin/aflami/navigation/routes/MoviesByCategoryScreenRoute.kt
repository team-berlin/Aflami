package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.MoviesByCategoryDestination
import com.berlin.aflami.screens.categories.MoviesByCategoryScreen

fun NavGraphBuilder.moviesByCategoryRoute() =
    composable<MoviesByCategoryDestination>
    {
        MoviesByCategoryScreen()
    }

