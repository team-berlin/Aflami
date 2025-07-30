package com.berlin.aflami.util

import com.berlin.aflami.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()

        val request = original.newBuilder().url(url).build()
        return chain.proceed(request)
    }

}