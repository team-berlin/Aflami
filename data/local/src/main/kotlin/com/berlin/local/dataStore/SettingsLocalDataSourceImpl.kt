package com.berlin.local.dataStore

import com.berlin.repository.datasource.local.dataStore.SettingsLocalDataSource
import javax.inject.Inject

class SettingsLocalDataSourceImpl @Inject constructor(
    private val preferences: SettingsPreferencesDataStore
) : SettingsLocalDataSource {

    override suspend fun setTheme(theme: String) = preferences.setTheme(theme)

    override suspend fun getTheme(): String? = preferences.getTheme()

    override suspend fun setLanguage(language: String) = preferences.setLanguage(language)

    override suspend fun getLanguage(): String? = preferences.getLanguage()
}
