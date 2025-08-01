package com.berlin.aflami.screens.authentication

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(url: String) {
    val navController = Theme.navController
    var isLoading by remember { mutableStateOf(true) }
    Box {
        AndroidView(

            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    webViewClient = CustomWebViewClient(
                        onPageStarted = {
                            isLoading = true
                        },
                        onPageFinished = {
                            isLoading = false
                        },
                        onError = { onErrorReceived(navController) }
                    )
                    settings.javaScriptEnabled = true
                    settings.setSupportZoom(true)
                }
            },
            update = { webView ->
                webView.loadUrl(url)
            }
        )
        if (isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.surface),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    text = stringResource(R.string.loading),
                )
            }
        }
    }
}

private fun onErrorReceived(navController: NavController) {
    navController.popBackStack()
}

private class CustomWebViewClient(
    private val onPageStarted: () -> Unit,
    private val onPageFinished: () -> Unit,
    private val onError: () -> Unit
) : WebViewClient() {
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        if (request?.url.toString() == view?.url) {
            error?.errorCode?.let { onError() }
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        onPageFinished()
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
        onPageStarted
    }
}