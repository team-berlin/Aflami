package com.berlin.local.dataStore

import com.berlin.repository.datasource.local.dataStore.SettingsLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsLocalDataSourceImpl @Inject constructor(
    //private val dataStore: DataStore<Preferences>
    private val preferences: SettingsPreferencesDataStore,


) : SettingsLocalDataSource {

    override suspend fun setTheme(theme: String) {
        /*  dataStore.edit { prefs ->
              prefs[APP_THEME] = theme
          }*/
        preferences.setTheme(theme)
    }

    override suspend fun getTheme(): Flow<String?> {
        /* return dataStore.data.map { prefs ->
             prefs[APP_THEME]
         }*/
        return preferences.getTheme()
    }

    override suspend fun setLanguage(language: String) {
        /*dataStore.edit { prefs ->
            prefs[APP_LANGUAGE] = language*/
        preferences.setLanguage(language)
    }

    override suspend fun getLanguage(): Flow<String?> {
        /* return dataStore.data.map { prefs ->
            prefs[APP_LANGUAGE]
        }*/

        return preferences.getLanguage()
    }
}



