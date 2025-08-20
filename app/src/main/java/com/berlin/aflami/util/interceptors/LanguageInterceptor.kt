package com.berlin.aflami.util.interceptors

import androidx.appcompat.app.AppCompatDelegate
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class LanguageInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalUrl = request.url

        val shouldSkipLanguage = originalUrl.encodedPath.endsWith("/images") ||
                originalUrl.encodedPath.endsWith("/videos")

        val newUrlBuilder = originalUrl.newBuilder()
        if (!shouldSkipLanguage) {
            val appLocales = AppCompatDelegate.getApplicationLocales()
            val languageTag =
                if (appLocales.isEmpty) {
                    Locale.getDefault().toLanguageTag()
                } else {
                    appLocales.toLanguageTags()
                }
            newUrlBuilder.setQueryParameter("language", languageTag)
        }
        val newUrl = newUrlBuilder.build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}
