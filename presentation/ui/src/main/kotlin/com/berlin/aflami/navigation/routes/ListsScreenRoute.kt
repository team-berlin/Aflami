package com.berlin.aflami.navigation.routes

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.NavigationBarDestinations
import com.berlin.aflami.screens.lists.ListsScreen

fun NavGraphBuilder.listsRoute() = composable<NavigationBarDestinations.ListScreen> {
    Log.d("Nadeen", "Navigating to ListScreen")
    ListsScreen()
}