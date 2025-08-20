package com.berlin.remote

import com.berlin.exception.AlreadyExistsException
import com.berlin.exception.ApiException
import com.berlin.exception.NetworkException
import com.berlin.exception.NotFoundException
import com.berlin.exception.UnauthorizedException
import com.berlin.exception.UnknownException
import okio.IOException
import retrofit2.Response
import java.net.UnknownHostException

suspend fun <T> wrapApiResponse(request: suspend () -> Response<T>): T {
    try {
        val response: Response<T> = request()

        if (response.isSuccessful) {
            return response.body() ?: throw ApiException("Response body is null")
        } else {
            if (response.code() == 403) throw AlreadyExistsException("Movie already exists")
            if (response.code() == 401) throw UnauthorizedException("UnauthorizedException error: ${response.code()}")
            throw ApiException("API error: ${response.code()}")
        }

    } catch (ioException: IOException) {
        throw NetworkException("Network error: ${ioException.message}")
    }catch (unauthorizedException: UnauthorizedException) {
        throw UnauthorizedException("unauthorizedException error: ${unauthorizedException.message}")
    }
    catch (e: UnknownHostException) {
        throw NetworkException("No internet connection: ${e.message}")
    } catch (e: IOException) {
        throw NetworkException("Network error: ${e.message}")
    } catch (e: AlreadyExistsException) {
        throw e
    } catch (e: Exception) {
        throw UnknownException("Unexpected error: ${e.message}")
    }
}
