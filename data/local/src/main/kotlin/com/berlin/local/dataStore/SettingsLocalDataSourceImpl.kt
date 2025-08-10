package com.berlin.local.dataStore

import com.berlin.repository.datasource.local.dataStore.SettingsLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsLocalDataSourceImpl @Inject constructor(
    private val preferences: SettingsPreferencesDataStore,
) : SettingsLocalDataSource {

    override suspend fun setTheme(theme: String) = preferences.setTheme(theme)

    override suspend fun getTheme(): Flow<String?> = preferences.getTheme()

    override suspend fun setLanguage(language: String) = preferences.setLanguage(language)


    override suspend fun getLanguage(): Flow<String?> = preferences.getLanguage()
    override suspend fun setContentRestriction(contentRestrictionLevel: String) =
        preferences.setContentRestriction(contentRestrictionLevel)

    override suspend fun getContentRestriction(): Flow<String?> =
        preferences.getContentRestriction()
}



