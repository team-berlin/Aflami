package repository

import com.berlin.entity.AppLanguage
import com.berlin.entity.AppTheme
import kotlinx.coroutines.flow.Flow


interface SettingsRepository {
    suspend fun getTheme(): Flow<String>
    suspend fun setTheme(theme: AppTheme)
    suspend fun getLanguage(): Flow<String>
    suspend fun setLanguage(language: AppLanguage)
    suspend fun getContentRestriction(): Flow<String?>
    suspend fun setContentRestriction(contentRestrictionLevel: String)

}