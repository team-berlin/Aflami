package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.screens.search.country.SearchByCountryScreen
import com.example.navigation.Destination.SearchByCountryScreen

fun NavGraphBuilder.searchByCountryRoute(
    navController: NavController
) {
    composable(route = SearchByCountryScreen.route) {
        SearchByCountryScreen(navController = navController)
    }
}