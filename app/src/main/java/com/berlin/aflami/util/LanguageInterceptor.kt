package com.berlin.aflami.util

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import usecase.profile.GetLanguageUseCase
import java.util.Locale

class LanguageInterceptor(
    val getLanguageUseCase: GetLanguageUseCase
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url

        val shouldSkipLanguage = originalUrl.encodedPath.endsWith("/images") ||
                originalUrl.encodedPath.endsWith("/videos")

        val newUrlBuilder = originalUrl.newBuilder()
        if (!shouldSkipLanguage) {
            val tmdbLanguageParam = runBlocking {
                val lang = getLanguageUseCase().first().orEmpty().lowercase(Locale.ROOT)
                if (lang == "ar") "ar-EG" else "${lang}-US"
            }
            newUrlBuilder.addQueryParameter("language", tmdbLanguageParam)
        }
        val newRequest = original.newBuilder()
            .url(newUrlBuilder.build())
            .build()
        return chain.proceed(newRequest)
    }
}
