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
    val isLanguageEN: Boolean = true,
    val isDarkThemeEnabled: Boolean = false,
    val appVersion: String = "v1.0.0",
    val activeDialog: ProfileDialogType = ProfileDialogType.NONE

)

enum class ProfileDialogType { NONE, LANGUAGE, THEME, SETTINGS }

