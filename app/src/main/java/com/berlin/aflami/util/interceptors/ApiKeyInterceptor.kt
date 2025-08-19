package com.berlin.aflami.util.interceptors

import com.berlin.aflami.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.API_KEY)
            .build()

        val request = original.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}