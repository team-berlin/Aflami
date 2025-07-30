package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.SearchScreen
import com.berlin.aflami.screens.search.search.SearchScreen

fun NavGraphBuilder.searchScreenRoute() = composable<SearchScreen> {
    SearchScreen()
}