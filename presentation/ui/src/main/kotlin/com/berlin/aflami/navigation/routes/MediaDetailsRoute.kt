package com.berlin.aflami.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.berlin.aflami.navigation.CastScreen
import com.berlin.aflami.navigation.MediaDetailsScreen
import com.berlin.aflami.screens.mediadetails.screen.MediaDetailsScreen
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsScreenEffect


fun NavGraphBuilder.mediaDetailsRoute(
    navController: NavController
) {
    composable<MediaDetailsScreen> { backStackEntry ->
        val mediaDetailsParameters = backStackEntry.toRoute<MediaDetailsScreen>()
        MediaDetailsScreen(
            mediaId = mediaDetailsParameters.mediaId,
            mediaType = mediaDetailsParameters.mediaType,
            onEffect = { effect ->
                when (effect) {
                    is MediaDetailsScreenEffect.NavigateToShowAllCastScreen -> {
                        navController.navigate(
                            CastScreen(effect.mediaId, effect.mediaType)
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
