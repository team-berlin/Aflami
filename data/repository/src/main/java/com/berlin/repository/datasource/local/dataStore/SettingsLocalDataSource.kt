package com.berlin.repository.datasource.local.dataStore

interface SettingsLocalDataSource {
    suspend fun setTheme(theme: String)
    suspend fun getTheme(): String?
    suspend fun setLanguage(language: String)
    suspend fun getLanguage(): String?
}