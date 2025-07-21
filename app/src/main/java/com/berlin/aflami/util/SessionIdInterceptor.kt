package com.berlin.aflami.util

import okhttp3.Interceptor
import okhttp3.Response

class SessionIdInterceptor(
private val sessionManager: SessionManager,
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val sessionId = sessionManager.getSessionId()
        val urlWithSession = if (sessionId != null) {
            originalUrl.newBuilder()
                .addQueryParameter("session_id", sessionId)
                .build()
        } else {
            originalUrl
        }

        val newRequest = originalRequest.newBuilder()
            .url(urlWithSession)
            .build()

        return chain.proceed(newRequest)
    }
}