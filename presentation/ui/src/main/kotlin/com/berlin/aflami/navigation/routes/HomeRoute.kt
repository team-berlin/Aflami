package com.berlin.aflami.navigation.routes


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.ContinueWatchingScreen
import com.berlin.aflami.navigation.HomeScreen
import com.berlin.aflami.navigation.MediaDetailsScreen
import com.berlin.aflami.navigation.SearchScreen
import com.berlin.aflami.navigation.TopRatingMediaScreen
import com.berlin.aflami.screens.home.HomeScreen
import com.berlin.aflami.viewmodel.home.HomeScreenEffect
import com.berlin.aflami.viewmodel.shareduistate.MediaType

fun NavGraphBuilder.home(
    navController: NavController,
) {
    composable<HomeScreen> {
        HomeScreen(
            onEffect = { effect ->
                when (effect) {
                    is HomeScreenEffect.NavigateToContinueWatching -> {
                        navController.navigate(
                            ContinueWatchingScreen
                        )
                    }

                    is HomeScreenEffect.NavigateToSearch -> {
                        navController.navigate(
                            SearchScreen
                        )
                    }

                    is HomeScreenEffect.NavigateToTopRating -> {
                        TopRatingMediaScreen
                    }
                    is HomeScreenEffect.NavigateToMovieDetails -> {
                        navController.navigate(
                            MediaDetailsScreen(effect.id, MediaType.valueOf(effect.mediaType))
                        )
                    }

                    else -> {}
                }
            }
        )

    }

}