package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.ListsScreenWithArgs
import com.berlin.aflami.screens.lists.ListScreen

fun NavGraphBuilder.listsScreenRouteWithArgs() = composable<ListsScreenWithArgs> {
    ListScreen()
}