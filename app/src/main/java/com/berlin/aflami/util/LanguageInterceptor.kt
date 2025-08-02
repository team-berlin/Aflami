package com.berlin.aflami.util

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
class LanguageInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url

        val shouldSkipLanguage = originalUrl.encodedPath.endsWith("/images")

        val newUrlBuilder = originalUrl.newBuilder()
        if (!shouldSkipLanguage) {
            newUrlBuilder.addQueryParameter("language", tmdbLanguageParam)
        }

        val newRequest = original.newBuilder().url(newUrlBuilder.build()).build()
        return chain.proceed(newRequest)
    }
    val currentLocale = Locale.getDefault()
    val languageCode = currentLocale.language
    val countryCode = currentLocale.country
    val tmdbLanguageParam = "${languageCode}-${countryCode}"
}