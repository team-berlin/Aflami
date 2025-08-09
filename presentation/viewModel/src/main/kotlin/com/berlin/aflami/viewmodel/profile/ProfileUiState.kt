package com.berlin.aflami.viewmodel.profile

import com.berlin.entity.AppLanguage
import com.berlin.entity.AppTheme


data class ProfileUiState(
    val coverImageUrl: String? = null,
    val userAvatarUrl: String? = null,
    val userName: String = "",
    val userPoints: Int = 0,
    val selectedLanguage: String = AppLanguage.AR.name,
    val selectedTheme: String = AppTheme.DARK.name,
    val isLanguageEN: Boolean = false,
    val isDarkThemeEnabled: Boolean = true,
    val appVersion: String = "v1.0.0",
    val activeDialog: ProfileDialogType = ProfileDialogType.NONE,
    val isDarkThemeSelected: Boolean = true,
    val isLightThemeSelected: Boolean = false,
    val isEnglishSelected: Boolean = false,
    val isArabicSelected: Boolean = true,
    val isLoggedIn: Boolean = false,


)

enum class ProfileDialogType { NONE, LANGUAGE, THEME, SETTINGS, CONTENT_RESTRICTION }

