package com.berlin.entity

open class AppException(message: String = "") : RuntimeException(message)
class NoInternetException(message: String = "No internet connection") : AppException(message)
class TimeoutException(message: String = "Request timed out") : AppException(message)
class ServerException(message: String = "Server error") : AppException(message)
class UnknownException(message: String = "Unknown error") : AppException(message)
