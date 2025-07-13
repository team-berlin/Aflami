package com.berlin.aflami.viewmodel.mapper

import com.berlin.entity.AppException
import com.berlin.entity.NoInternetException
import com.berlin.entity.ServerException
import com.berlin.entity.TimeoutException
import com.berlin.entity.UnknownException

fun Throwable.toMessage(): String {
    return when (this) {
        is NoInternetException -> "No internet connection. Please check your network."
        is TimeoutException -> "Request timed out. Please try again later."
        is ServerException -> "Something went wrong on the server. Please try again."
        is UnknownException -> "An unexpected error occurred. Please try again."
        else -> "An unknown error occurred. Please try again."    }
}
