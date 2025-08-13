package com.berlin.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.berlin.local.utils.DataStoreKeys
import com.berlin.local.utils.EncryptionUtils
import com.berlin.repository.datasource.local.AuthenticationLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : AuthenticationLocalDataSource {

    override fun observeLoginStatus(): Flow<Boolean> {
        return dataStore.data
            .catch { emit(emptyPreferences()) } // optional: handle IOExceptions safely
            .map { preferences ->
                preferences[DataStoreKeys.USER_SESSION_ID]?.isNotBlank() == true
            }
            .distinctUntilChanged()
    }

    override suspend fun saveUserAccountId(accountId: Int): Boolean {
        return try {
            val encrypted = EncryptionUtils.encrypt(accountId.toString())
            dataStore.edit { it[DataStoreKeys.USER_ACCOUNT_ID] = encrypted }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getUserAccountId(): Int {
        return try {
            val encrypted: String = dataStore.data.first()[DataStoreKeys.USER_ACCOUNT_ID]
                ?: throw IllegalStateException("account id not found in local storage ")
            EncryptionUtils.decrypt(encrypted).toInt()
        } catch (e: Exception) {
            throw e
        }
    }


    override suspend fun saveUserToken(userToken: String): Boolean {
        return try {
            val encrypted = EncryptionUtils.encrypt(userToken)
            dataStore.edit { it[DataStoreKeys.USER_TOKEN] = encrypted }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getUserToken(): String? {
        return try {
            val encrypted = dataStore.data.first()[DataStoreKeys.USER_TOKEN] ?: return null
            EncryptionUtils.decrypt(encrypted)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deleteUserToken(): Boolean {
        return try {
            dataStore.edit { it.remove(DataStoreKeys.USER_TOKEN) }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun saveUserSessionId(userSessionId: String): Boolean {
        return try {
            val encrypted = EncryptionUtils.encrypt(userSessionId)
            dataStore.edit { it[DataStoreKeys.USER_SESSION_ID] = encrypted }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getUserSessionId(): String? {
        return try {
            val encrypted = dataStore.data.first()[DataStoreKeys.USER_SESSION_ID] ?: return null
            EncryptionUtils.decrypt(encrypted)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deleteUserSessionId(): Boolean {
        return try {
            dataStore.edit { it.remove(DataStoreKeys.USER_SESSION_ID) }
            true
        } catch (e: Exception) {
            false
        }
    }
}