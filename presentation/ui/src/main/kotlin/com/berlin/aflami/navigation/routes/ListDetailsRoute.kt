package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.ListDetailsDestination
import com.berlin.aflami.screens.listdetails.ListDetailsScreen

fun NavGraphBuilder.listsDetailsRoute() = composable<ListDetailsDestination> {
    ListDetailsScreen()
}