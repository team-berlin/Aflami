package com.berlin.repository.mapper

import com.berlin.entity.AppException
import com.berlin.entity.NoInternetException
import com.berlin.entity.ServerException
import com.berlin.entity.TimeoutException
import com.berlin.entity.UnknownException
import java.net.UnknownHostException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException


fun Throwable.toAppException(): AppException = when (this) {
    is UnknownHostException -> NoInternetException()
    is HttpRequestTimeoutException -> TimeoutException()
    is RedirectResponseException -> AppException("Redirection Error")
    is ClientRequestException -> AppException("Request Error")
    is ServerResponseException -> ServerException("Server Error")
    else -> UnknownException(this.message ?: "Unknown error")
}
