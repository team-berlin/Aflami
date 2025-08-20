package com.berlin.aflami.viewmodel.profile

import com.berlin.entity.AppLanguage
import com.berlin.entity.AppTheme
import com.berlin.entity.ContentRestriction
import java.util.Locale


data class ProfileUiState(
    val coverImageUrl: String? = null,
    val userAvatarUrl: String? = null,
    val userName: String = "",
    val userPoints: Int = 0,
    val activeDialog: ProfileDialogType = ProfileDialogType.NONE,
    val isLoggedIn: Boolean? = null,
    val themeOption: ThemeOption = ThemeOption(),
    val languageOption: LanguageOption = LanguageOption(),
    val contentRestrictionOption: ContentRestrictionOption = ContentRestrictionOption(),
    val appVersion: String = "v1.0.0",

    )
data class ThemeOption(
    val selectedTheme: String = AppTheme.DARK.name,
    val tempSelectedTheme: String = selectedTheme,
    val isDarkThemeEnabled: Boolean = AppTheme.DARK.name == selectedTheme,
)
data class LanguageOption(
    val selectedLanguage: AppLanguage =
        if (Locale.getDefault().language == "ar")
            AppLanguage.AR else AppLanguage.EN,
    val tempSelectedLanguage: AppLanguage = selectedLanguage,
    val isEnglishEnabled: Boolean = selectedLanguage == AppLanguage.EN,
)
data class ContentRestrictionOption(
    val isStrictSelected: Boolean = true,
    val isModeratedSelected: Boolean = false,
    val isOffSelected: Boolean = false,
    val selectedRestriction: String = ContentRestriction.STRICT.name,
    val tempSelectedRestriction: String = selectedRestriction,
    val contentRestrictionPercentage: Int = 100,
)

enum class ProfileDialogType {
    NONE,
    LANGUAGE,
    THEME,
    SETTINGS,
    CONTENT_RESTRICTION,
    LOGOUT
}

