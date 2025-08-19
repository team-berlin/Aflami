package repository

import com.berlin.entity.AppLanguage
import com.berlin.entity.AppTheme
import kotlinx.coroutines.flow.Flow


interface SettingsRepository {
    fun getTheme(): Flow<String>
    suspend fun setTheme(theme: AppTheme)
    fun getLanguage(): Flow<String>
    suspend fun setLanguage(language: AppLanguage)
    fun getContentRestriction(): Flow<String?>
    suspend fun setContentRestriction(contentRestrictionLevel: String)

}