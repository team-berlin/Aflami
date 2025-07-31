package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.berlin.aflami.navigation.SearchByCountryScreen
import com.berlin.aflami.screens.search.country.SearchByCountryScreen

fun NavGraphBuilder.searchByCountryRoute() = composable<SearchByCountryScreen> {
    SearchByCountryScreen()
}