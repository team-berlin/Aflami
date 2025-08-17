package com.berlin.aflami.util

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class LanguageInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url

        val shouldSkipLanguage = originalUrl.encodedPath.endsWith("/images") ||
                originalUrl.encodedPath.endsWith("/videos")

        val newUrlBuilder = originalUrl.newBuilder()
        if (!shouldSkipLanguage) {
            newUrlBuilder.addQueryParameter("language", tmdbLanguageParam)
        }

        val newRequest = original.newBuilder()
            .url(newUrlBuilder.build())
            .build()

        return chain.proceed(newRequest)
    }

    private val currentLocale = Locale.getDefault()
    private val languageCode = currentLocale.language
    private val countryCode = currentLocale.country
    private val tmdbLanguageParam = "${languageCode}-${countryCode}"
}
