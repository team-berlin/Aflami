package com.berlin.aflami.navigation.routes

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.berlin.aflami.navigation.WebViewDestination
import com.berlin.aflami.screens.authentication.WebView

fun NavGraphBuilder.webView() {
    composable<WebViewDestination> { backStackEntry ->
        val webViewParameters = backStackEntry.toRoute<WebViewDestination>()
        WebView(
            url = webViewParameters.url,
        )
    }
}