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
    val selectedTheme: String = AppTheme.DARK.name,
    val tempSelectedTheme: String = selectedTheme,
    val isDarkThemeEnabled: Boolean = AppTheme.DARK.name == selectedTheme,
    val isDarkThemeSelected: Boolean = AppTheme.DARK.name == selectedTheme,
    val isLightThemeSelected: Boolean = AppTheme.LIGHT.name == selectedTheme,
    val selectedLanguage: String = Locale.getDefault().language.uppercase(),
    val tempSelectedLanguage: String = selectedLanguage,
    val isEnglishEnabled: Boolean = selectedLanguage == AppLanguage.EN.name,
    val isEnglishSelected: Boolean = selectedLanguage == AppLanguage.EN.name,
    val isArabicSelected: Boolean = selectedLanguage == AppLanguage.AR.name,
    val activeDialog: ProfileDialogType = ProfileDialogType.NONE,
    val isLoggedIn: Boolean = false,
    val isStrictSelected: Boolean = true,
    val isModeratedSelected: Boolean = false,
    val isOffSelected: Boolean = false,
    val selectedRestriction: String = ContentRestriction.STRICT.name,
    val tempSelectedRestriction: String = selectedRestriction,
    val contentRestrictionPercentage: Int = 100,
    val appVersion: String = "v1.0.0",


    )

enum class ProfileDialogType { NONE, LANGUAGE, THEME, SETTINGS, CONTENT_RESTRICTION }

