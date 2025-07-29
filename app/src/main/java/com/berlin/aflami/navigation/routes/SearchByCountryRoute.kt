package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.screens.search.country.SearchByCountryScreen
import com.berlin.aflami.viewmodel.searchcountry.SearchByCountryEffect
import com.example.navigation.Destination
import com.example.navigation.Destination.SearchByCountryScreen

fun NavGraphBuilder.searchByCountryRoute(
    navController: NavController
) {
    composable(route = SearchByCountryScreen.route) {
        SearchByCountryScreen(
            onEffect = {effect->
                when (effect) {
                    is SearchByCountryEffect.NavigatedBack -> navController.popBackStack()
                    is SearchByCountryEffect.NavigatedToMovieDetailsScreen -> {
                        navController.navigate(
                            Destination.MediaDetailsScreen.route(
                                effect.movieId,
                                effect.mediaType
                            )
                        )
                    }
                }
            },
        )
    }
}