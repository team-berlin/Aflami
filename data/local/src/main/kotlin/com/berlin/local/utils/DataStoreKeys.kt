package com.berlin.local.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    const val USER_TOKEN_SHARED_PREFERENCES_KEY = "userToken"
    const val USER_SESSION_ID_SHARED_PREFERENCES_KEY = "userSessionId"

    val USER_ACCOUNT_ID = stringPreferencesKey("account_id")
    val USER_TOKEN = stringPreferencesKey("user_token")
    val USER_SESSION_ID = stringPreferencesKey("user_session_id")

}