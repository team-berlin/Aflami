package com.berlin.local.dataStore

import com.berlin.repository.datasource.local.dataStore.SettingsLocalDataSource
import kotlinx.coroutines.flow.Flow

class SettingsLocalDataSourceImpl(
    private val preferences: SettingsPreferencesDataStore
) : SettingsLocalDataSource {

    override suspend fun setTheme(theme: String) = preferences.setTheme(theme)

    override fun getTheme(): Flow<String?> = preferences.getTheme()

    override suspend fun setLanguage(language: String) = preferences.setLanguage(language)

    override fun getLanguage(): Flow<String?> = preferences.getLanguage()
}
