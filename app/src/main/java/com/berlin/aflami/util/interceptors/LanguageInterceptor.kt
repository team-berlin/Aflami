package com.berlin.aflami.util

import androidx.appcompat.app.AppCompatDelegate
import okhttp3.Interceptor
import okhttp3.Response

class LanguageInterceptor(

) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url.newBuilder()
            .setQueryParameter(
                "language",
                AppCompatDelegate.getApplicationLocales().toLanguageTags()
            )
            .build()

        return chain.proceed(request.newBuilder().url(url).build())
    }
}
