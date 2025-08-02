package com.berlin.remote

import com.berlin.entity.AuthorizationException
import com.berlin.entity.BadRequestException
import com.berlin.entity.DataParseException
import com.berlin.entity.EmptyResponseException
import com.berlin.entity.ForbiddenException
import com.berlin.entity.NetworkException
import com.berlin.entity.NoInternetException
import com.berlin.entity.NotFoundException
import com.berlin.entity.RateLimitException
import com.berlin.entity.ServerException
import okio.IOException
import retrofit2.Response
import java.net.UnknownHostException


suspend fun <T> wrapApiResponse(
    request: suspend () -> Response<T>
): T {
    val response: Response<T>
    try {
        response = request()
    } catch (_: UnknownHostException) {
        throw NoInternetException("No internet connection")
    } catch (_: IOException) {
        throw NetworkException("Network error")
    } catch (_: Exception) {
        throw DataParseException("Unexpected error")
    }

    if (response.isSuccessful) {
        return response.body() ?: throw EmptyResponseException("Response body is null")
    } else {
        throw when (response.code()) {
            400 -> BadRequestException(response.message())
            401 -> AuthorizationException("Unauthorized")
            403 -> ForbiddenException("Access forbidden")
            404 -> NotFoundException("Not found")
            429 -> RateLimitException("Rate limit exceeded")
            500 -> ServerException("Internal server error")
            else -> ServerException("Unexpected error: ${response.code()}")
        }
    }
}
