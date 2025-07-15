package com.berlin.aflami.navigation.routes

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.berlin.aflami.navigation.routes.MediaDetailsArgs.Companion.MEDIA_ID_ARGS
import com.berlin.aflami.navigation.routes.MediaDetailsArgs.Companion.MEDIA_TYPE_ARGS
import com.berlin.aflami.screens.search.mediadetails.MediaDetailsScreen
import com.berlin.aflami.viewmodel.mediadetails.MediaType
import com.example.navigation.Destination

fun NavGraphBuilder.mediaDetailsRoute(
    navController: NavController
) {
    composable(
        route = Destination.MediaDetailsScreen.route,
        arguments = listOf(
            navArgument(MEDIA_ID_ARGS) { NavType.LongType },
            navArgument(MEDIA_TYPE_ARGS) { NavType.EnumType(MediaType::class.java) }
        )
    ) {
        MediaDetailsScreen(navController = navController)
    }
}

class MediaDetailsArgs(saveStateHandle: SavedStateHandle) {
    val mediaId: Long = checkNotNull(saveStateHandle["mediaId"])
    val mediaType: MediaType = MediaType.valueOf(checkNotNull(saveStateHandle["mediaType"]))

    companion object {
        const val MEDIA_ID_ARGS = "mediaId"
        const val MEDIA_TYPE_ARGS = "mediaType"
    }
}