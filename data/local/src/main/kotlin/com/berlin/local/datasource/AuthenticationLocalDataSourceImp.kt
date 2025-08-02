package com.berlin.local.datasource

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.berlin.local.utils.SharedPrefConstants.USER_SESSION_ID_SHARED_PREFERENCES_KEY
import com.berlin.local.utils.SharedPrefConstants.USER_TOKEN_SHARED_PREFERENCES_KEY
import com.berlin.repository.datasource.local.AuthenticationLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@SuppressLint("UseKtx")
class AuthenticationLocalDataSourceImp @Inject constructor(
    private var prefs: SharedPreferences
) : AuthenticationLocalDataSource {

    override suspend fun saveUserToken(userToken: String) = withContext(Dispatchers.IO) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN_SHARED_PREFERENCES_KEY, userToken)
        return@withContext editor.commit()
    }

    override suspend fun getUserToken(): String? = withContext(Dispatchers.IO) {
        prefs.getString(USER_TOKEN_SHARED_PREFERENCES_KEY, null)
    }

    override suspend fun deleteUserToken(): Boolean =
        withContext(Dispatchers.IO) {
            val editor = prefs.edit()
            editor.remove(USER_TOKEN_SHARED_PREFERENCES_KEY)
            return@withContext editor.commit()
        }

    override suspend fun saveUserSessionId(userSessionId: String) = withContext(Dispatchers.IO) {
        val editor = prefs.edit()
        editor.putString(USER_SESSION_ID_SHARED_PREFERENCES_KEY, userSessionId)
        return@withContext editor.commit()
    }

    override suspend fun getUserSessionId(): String? = withContext(Dispatchers.IO) {
        prefs.getString(USER_SESSION_ID_SHARED_PREFERENCES_KEY, null)
    }


    override suspend fun deleteUserSessionId(): Boolean =
        withContext(Dispatchers.IO) {
            val editor = prefs.edit()
            editor.remove(USER_TOKEN_SHARED_PREFERENCES_KEY)
            return@withContext editor.commit()
        }
}