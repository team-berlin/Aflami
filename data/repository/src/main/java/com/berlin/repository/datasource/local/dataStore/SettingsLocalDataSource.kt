package com.berlin.repository.datasource.local.dataStore

import kotlinx.coroutines.flow.Flow

interface SettingsLocalDataSource {
    suspend fun setTheme(theme: String)
    suspend fun getTheme(): Flow<String?>
    suspend fun setLanguage(language: String)
    suspend fun getLanguage(): Flow<String?>
}