package com.berlin.entity


open class AflamiException(message: String?): Exception(message)

class NullResultException(message: String?) : AflamiException(message)
class BadRequestException(message: String?) : AflamiException(message)
open class ValidationException(message: String?): AflamiException(message)
class NotFoundException(message: String?): AflamiException(message)
open class NetworkException(message: String?): AflamiException(message)
class  NoInternetException(message: String?): NetworkException(message)
class ServerException(message: String?) : NetworkException(message)
class EmptyResponseException(message: String) : Exception(message)
class DataParseException(message: String) : Exception(message)
class ForbiddenException(message: String) : Exception(message)
class RateLimitException(message: String) : Exception(message)

open class AuthenticationsExceptions(message: String):AflamiException(message)
class InvalidUsernameOrPasswordException(message: String): AuthenticationsExceptions(message)
class InvalidLoginTokenException(message: String): AuthenticationsExceptions(message)
class InvalidLoginApiKeyException(message: String): AuthenticationsExceptions(message)
class SessionDeniedException(message: String): AuthenticationsExceptions(message)


class AuthorizationException(message: String): AflamiException(message)
