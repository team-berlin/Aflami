package com.berlin.remote


import com.berlin.exception.NetworkException
import com.berlin.exception.NotFoundException
import com.berlin.exception.UnknownException
import okio.IOException
import retrofit2.Response
import java.net.UnknownHostException


suspend fun <T> wrapApiResponse(
    request: suspend () -> Response<T>
): T {
    try {
        return request().body() ?: throw NotFoundException("Response body is null")
    } catch (e: UnknownHostException) {
        throw NetworkException("No internet connection: ${e.message}")
    } catch (e: IOException) {
        throw NetworkException("Network error: ${e.message}")
    } catch (e: Exception) {
        throw UnknownException("Unexpected error: ${e.message}")
    }
}