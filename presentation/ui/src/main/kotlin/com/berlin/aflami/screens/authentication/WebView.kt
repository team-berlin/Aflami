package com.berlin.aflami.screens.authentication

import android.annotation.SuppressLint
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(
    url: String,
    onError: () -> Unit
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = ErrorWebViewClient(onError = onError)
                settings.javaScriptEnabled = true
                settings.setSupportZoom(true)
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
}

private class ErrorWebViewClient(val onError: () -> Unit) : WebViewClient() {
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
}