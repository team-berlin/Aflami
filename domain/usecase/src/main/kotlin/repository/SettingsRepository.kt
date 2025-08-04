package repository

import com.berlin.entity.AppLanguage
import com.berlin.entity.AppTheme

interface SettingsRepository {
    suspend fun getTheme(): AppTheme
    suspend fun setTheme(theme: AppTheme)
    suspend fun getLanguage(): AppLanguage
    suspend fun setLanguage(language: AppLanguage)
}