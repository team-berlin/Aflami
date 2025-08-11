package com.berlin.remote

import com.berlin.exception.AlreadyExistsException
import com.berlin.exception.ApiException
import com.berlin.exception.NetworkException
import com.berlin.exception.NotFoundException
import com.berlin.exception.UnknownException
import com.berlin.repository.datasource.remote.dto.AddMovieToListDto
import okio.IOException
import retrofit2.Response
import java.net.UnknownHostException

suspend fun <T> wrapApiResponse(request: suspend () -> Response<T>): T {
    try {
        val response: Response<T> = request()
        val body = response.body()
        if (body is AddMovieToListDto && body.statusCode == 8) {
            throw AlreadyExistsException(body.statusMessage ?: "Movie already exists")
        }

        if (response.isSuccessful) {
            return body ?: throw NotFoundException("Response body is null")
        } else {
            throw ApiException("API error: ${response.code()} - ${response.message()}")
        }

    } catch (e: UnknownHostException) {
        throw NetworkException("No internet connection: ${e.message}")
    } catch (e: IOException) {
        throw NetworkException("Network error: ${e.message}")
    } catch (e: Exception) {
        throw UnknownException("Unexpected error: ${e.message}")
    }
}