package repository

import com.berlin.entity.auth.LoginToken
import com.berlin.entity.auth.RequestToken
import com.berlin.entity.auth.Session

interface AuthenticationRepository {

    suspend fun register(email: String, userName: String, password: String)
    suspend fun requestToken(): LoginToken
    suspend fun createSession(requestToken:String): Session

    suspend fun login(userName: String, password: String ): LoginToken
    suspend fun logout()
}