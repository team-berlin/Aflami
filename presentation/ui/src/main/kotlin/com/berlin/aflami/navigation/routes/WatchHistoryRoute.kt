package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.WatchHistoryDestination
import com.berlin.aflami.screens.profile.WatchHistoryScreen

fun NavGraphBuilder.watchHistory()=composable<WatchHistoryDestination> {
    WatchHistoryScreen()
}