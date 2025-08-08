package com.berlin.local.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val PREFERENCES_NAME = "settings_preferences"

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

class SettingsPreferencesDataStore @Inject constructor(private val context: Context) {

    companion object {
        val APP_THEME = stringPreferencesKey("app_theme")
        val APP_LANGUAGE = stringPreferencesKey("app_language")
    }

    suspend fun setTheme(theme: String) {
        context.dataStore.edit { prefs ->
            prefs[APP_THEME] = theme
        }
    }

    suspend fun getTheme(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[APP_THEME] ?: "DARK"
        }
    }

    suspend fun setLanguage(lang: String) {
        context.dataStore.edit { prefs ->
            prefs[APP_LANGUAGE] = lang
        }
    }
    suspend fun getLanguage(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[APP_LANGUAGE] ?: "AR"
        }

    }

}

