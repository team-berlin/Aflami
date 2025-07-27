package com.berlin.exception


open class AflamiException(message: String?): Exception(message)

class UnauthorizedException(message: String?): AflamiException(message)
class NotFoundException(message: String?): AflamiException(message)
class NetworkException(message: String?): AflamiException(message)
class ServerException(message: String?) : AflamiException(message)
class UnknownException(message: String?): AflamiException(message)