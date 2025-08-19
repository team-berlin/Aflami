package com.berlin.repository.datasource.local.dataStore

import kotlinx.coroutines.flow.Flow

interface SettingsLocalDataSource {
    suspend fun setTheme(theme: String)
    fun getTheme(): Flow<String?>
    suspend fun setContentRestriction(contentRestrictionLevel: String)
    fun getContentRestriction(): Flow<String?>
}