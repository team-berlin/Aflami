package com.berlin.aflami.navigation.routes

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.berlin.aflami.navigation.WebViewScreen
import com.berlin.aflami.screens.authentication.WebView

fun NavGraphBuilder.webView(
    navController: NavController
) {
    composable<WebViewScreen> { backStackEntry ->
        val webViewParameters = backStackEntry.toRoute<WebViewScreen>()
        val decodedUrl = Uri.decode(webViewParameters.url)
        WebView(
            url = webViewParameters.url,
            onError = { navController.popBackStack() }
        )
    }
}

object WebViewArgs {
    const val URL = "url"
}