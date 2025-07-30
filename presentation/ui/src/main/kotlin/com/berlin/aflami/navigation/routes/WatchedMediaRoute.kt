package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.ContinueWatchingScreen
import com.berlin.aflami.screens.home.ContinueWatchingScreen
import com.berlin.aflami.viewmodel.watchedmedia.ContinueWatchingMediaEffect

fun NavGraphBuilder.watchedMedia(
    navController: NavController,
) {
    composable<ContinueWatchingScreen> {
        ContinueWatchingScreen(
            onEffect = { effect ->
                when (effect) {
                    is ContinueWatchingMediaEffect.NavigateToDetails -> {
                        navController.navigate(
                            ContinueWatchingScreen
                        )
                    }

                    is ContinueWatchingMediaEffect.OnBackClicked -> {
                        navController.popBackStack()
                    }
                }
            })

    }

}