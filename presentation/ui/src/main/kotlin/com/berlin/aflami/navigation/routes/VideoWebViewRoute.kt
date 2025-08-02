package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.berlin.aflami.navigation.VideoWebViewDestination
import com.berlin.aflami.navigation.WebViewDestination
import com.berlin.aflami.screens.mediadetails.screen.VideoScreen

fun NavGraphBuilder.videoWebView() {
    composable<VideoWebViewDestination> { backStackEntry ->
        val videoWebViewParameters = backStackEntry.toRoute<WebViewDestination>()
        VideoScreen(
            videoUrl = videoWebViewParameters.url,

            )
    }
}