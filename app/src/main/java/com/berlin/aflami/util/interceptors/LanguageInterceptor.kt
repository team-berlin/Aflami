package com.berlin.aflami.util.interceptors

import androidx.appcompat.app.AppCompatDelegate
import okhttp3.Interceptor
import okhttp3.Response

class LanguageInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalUrl = request.url

        val shouldSkipLanguage = originalUrl.encodedPath.endsWith("/images") ||
                originalUrl.encodedPath.endsWith("/videos")

        val newUrlBuilder = originalUrl.newBuilder()
        if (!shouldSkipLanguage) {
            newUrlBuilder.setQueryParameter(
                "language",
                AppCompatDelegate.getApplicationLocales().toLanguageTags()
            )
        }

        val newUrl = newUrlBuilder.build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
