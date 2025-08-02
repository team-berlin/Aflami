package com.berlin.aflami.screens.mediadetails.screen

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R
import androidx.compose.ui.res.stringResource

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoScreen(videoUrl: String) {
    var isLoading by remember { mutableStateOf(true) }
    Log.e("Video screen",videoUrl)
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.statusBarsPadding().fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                            isLoading = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            isLoading = false
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            isLoading = false
                        }
                    }
                    settings.javaScriptEnabled = true
                    settings.setSupportZoom(true)
                    loadUrl(videoUrl)
                }
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
