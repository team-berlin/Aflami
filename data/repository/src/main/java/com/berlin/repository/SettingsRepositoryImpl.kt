package com.yourapp.settings.data.repository

import com.berlin.entity.AppLanguage
import com.berlin.entity.AppTheme
import com.berlin.repository.datasource.local.dataStore.SettingsLocalDataSource
import repository.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsDataStore: SettingsLocalDataSource
) : SettingsRepository {
    override suspend fun getTheme(): AppTheme {
        val value = settingsDataStore.getTheme()
        return try {
            AppTheme.valueOf(value ?: AppTheme.DARK.name)
        } catch (e: IllegalArgumentException) {
            AppTheme.DARK
        }
    }

    override suspend fun setTheme(theme: AppTheme) {
        settingsDataStore.setTheme(theme.name)
    }

    override suspend fun getLanguage(): AppLanguage {
        val value = settingsDataStore.getLanguage()
        return try {
            AppLanguage.valueOf(value ?: AppLanguage.AR.name)
        } catch (e: IllegalArgumentException) {
            AppLanguage.AR
        }
    }

    override suspend fun setLanguage(language: AppLanguage) {
        settingsDataStore.setLanguage(language.name)
    }


}
