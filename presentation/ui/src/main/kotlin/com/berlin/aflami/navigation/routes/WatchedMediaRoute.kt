package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.ContinueWatchingScreen
import com.berlin.aflami.screens.home.ContinueWatchingScreen

fun NavGraphBuilder.watchedMedia() = composable<ContinueWatchingScreen> {
    ContinueWatchingScreen()
}