package com.berlin.aflami.viewmodel.profile

import com.berlin.entity.AppTheme
import com.berlin.entity.ContentRestriction
import java.util.Locale


data class ProfileUiState(
    val coverImageUrl: String? = null,
    val userAvatarUrl: String? = null,
    val userName: String = "",
    val userPoints: Int = 0,
    val selectedTheme: String = AppTheme.DARK.name,
    val isDarkThemeEnabled: Boolean = true,
    val isDarkThemeSelected: Boolean = true,
    val isLightThemeSelected: Boolean = false,
    val selectedLanguage: String = Locale.getDefault().language.uppercase(),
    val isEnglishEnabled: Boolean = false,
    val isEnglishSelected: Boolean = false,
    val isArabicSelected: Boolean = false,
    val activeDialog: ProfileDialogType = ProfileDialogType.NONE,
    val isLoggedIn: Boolean = false,
    val isStrictSelected: Boolean = true,
    val isModeratedSelected: Boolean = false,
    val isOffSelected: Boolean = false,
    val selectedRestriction: String = ContentRestriction.STRICT.name,
    val contentRestrictionPercentage: Int = 100,
    val appVersion: String = "v1.0.0",


    )

enum class ProfileDialogType { NONE, LANGUAGE, THEME, SETTINGS, CONTENT_RESTRICTION }

