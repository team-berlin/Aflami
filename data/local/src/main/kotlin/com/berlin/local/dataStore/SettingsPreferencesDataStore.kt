package com.berlin.local.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

private const val PREFERENCES_NAME = "settings_preferences"

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

class SettingsPreferencesDataStore @Inject constructor(private val context: Context) {

    companion object {
        val APP_THEME = stringPreferencesKey("app_theme")
        val APP_LANGUAGE = stringPreferencesKey("app_language")
        val App_CONTENT_RESTRICTION = stringPreferencesKey("app_content_restriction")

    }

    suspend fun setTheme(theme: String) {
        context.dataStore.edit { prefs ->
            prefs[APP_THEME] = theme
        }
    }

    fun getTheme(): Flow<String> {
        return context.dataStore.data.map { prefs ->
            prefs[APP_THEME] ?: "DARK"
        }
    }

    suspend fun setLanguage(lang: String) {
        context.dataStore.edit { prefs ->
            prefs[APP_LANGUAGE] = lang
        }
    }
    fun getLanguage(): Flow<String> {
        return context.dataStore.data.map { prefs ->
            prefs[APP_LANGUAGE] ?: Locale.getDefault().language.uppercase()
        }

    }

    suspend fun setContentRestriction(contentRestrictionLevel: String) {
        context.dataStore.edit { prefs ->
            prefs[App_CONTENT_RESTRICTION] = contentRestrictionLevel
        }
    }

    fun getContentRestriction(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[App_CONTENT_RESTRICTION] ?: "STRICT"
        }
    }
}

