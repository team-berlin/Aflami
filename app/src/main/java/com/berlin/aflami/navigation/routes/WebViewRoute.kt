package com.berlin.aflami.navigation.routes

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.berlin.aflami.screens.authentication.WebView
import com.example.navigation.Destination

fun NavGraphBuilder.webView(
    navController: NavController
) {
    composable(
        route = Destination.WebViewScreen.route,
        arguments = listOf(navArgument(WebViewArgs.URL) { NavType.StringType })
    ) { backStackEntry ->
        val url = backStackEntry.arguments?.getString(WebViewArgs.URL) ?: ""
        val decodedUrl = Uri.decode(url)
        WebView(
            url = decodedUrl,
            onError = { navController.popBackStack() }
        )
    }
}

object WebViewArgs {
    const val URL = "url"
}