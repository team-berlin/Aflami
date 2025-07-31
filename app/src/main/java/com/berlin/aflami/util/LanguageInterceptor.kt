package com.berlin.aflami.util

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
class LanguageInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter("language", tmdbLanguageParam)
            .build()

        val request = original.newBuilder().url(url).build()
        return chain.proceed(request)
    }
    val currentLocale = Locale.getDefault()
    val languageCode = currentLocale.language
    val countryCode = currentLocale.country
    val tmdbLanguageParam = "${languageCode}-${countryCode}"
}