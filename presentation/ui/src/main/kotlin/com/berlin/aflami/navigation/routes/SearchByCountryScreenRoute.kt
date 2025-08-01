package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.SearchByCountryDestination
import com.berlin.aflami.screens.search.country.SearchByCountryScreen

fun NavGraphBuilder.searchByCountryRoute() = composable<SearchByCountryDestination> {
    SearchByCountryScreen()
}