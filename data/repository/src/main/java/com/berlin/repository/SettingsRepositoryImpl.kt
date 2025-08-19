package com.berlin.repository

import com.berlin.entity.AppTheme
import com.berlin.repository.datasource.local.dataStore.SettingsLocalDataSource
import kotlinx.coroutines.flow.Flow
import repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsLocalDataSource
) : SettingsRepository {
    override fun getTheme(): Flow<String> {
        return settingsDataStore.getTheme()
    }

    override suspend fun setTheme(theme: AppTheme) {
        settingsDataStore.setTheme(theme.name)
    }

    override suspend fun setContentRestriction(contentRestrictionLevel: String) {
        settingsDataStore.setContentRestriction(contentRestrictionLevel)
    }

    override fun getContentRestriction(): Flow<String?> {
        return settingsDataStore.getContentRestriction()
    }

}
