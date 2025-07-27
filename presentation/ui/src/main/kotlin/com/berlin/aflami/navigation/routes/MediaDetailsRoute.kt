package com.berlin.aflami.navigation.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.berlin.aflami.navigation.Destination
import com.berlin.aflami.screens.mediadetails.screen.MediaDetailsScreen
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsScreenEffect


fun NavGraphBuilder.mediaDetailsRoute(
    navController: NavController
) {
    composable(
        route = Destination.MediaDetailsScreen.route,
        arguments = listOf(
            navArgument(MediaDetailsArgs.ID) { NavType.LongType },
            navArgument(MediaDetailsArgs.MEDIA_TYPE) { NavType.StringType }
        )
    ) {
        MediaDetailsScreen(
            onEffect = { effect ->
                when (effect) {
                    is MediaDetailsScreenEffect.NavigateToShowAllCastScreen -> {
                        navController.navigate(
                            Destination.CastScreen.route(
                                id=effect.mediaId,
                                mediaType = effect.mediaType.name
                            )
                        )
                    }
                    is MediaDetailsScreenEffect.NavigateBack -> {
                        navController.popBackStack()
                    }
                    is MediaDetailsScreenEffect.PlayMedia -> {}

                    else -> {}
                }
            }
        )

    }

}

object MediaDetailsArgs {
    const val ID = "id"
    const val MEDIA_TYPE = "media_type"
}
