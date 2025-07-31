package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.berlin.aflami.navigation.WebViewScreen
import com.berlin.aflami.screens.authentication.WebView

fun NavGraphBuilder.webView() {
    composable<WebViewScreen> { backStackEntry ->
        val webViewParameters = backStackEntry.toRoute<WebViewScreen>()
          WebView(
            url = webViewParameters.url,
        )
    }
}