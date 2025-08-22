package com.berlin.aflami.viewmodel.profile

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.entity.AppLanguage
import com.berlin.entity.AppTheme
import com.berlin.entity.ContentRestriction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import usecase.auth.GetLoginUseCase
import usecase.auth.LogoutUseCase
import usecase.game.GetPointsUseCase
import usecase.profile.ClearUserProfileUseCase
import usecase.profile.GetContentRestrictionUseCase
import usecase.profile.GetThemeUseCase
import usecase.profile.ObserveUserProfileUseCase
import usecase.profile.SetContentRestrictionUseCase
import usecase.profile.SetThemeUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val getLoginStatus: GetLoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val setContentRestrictionUseCase: SetContentRestrictionUseCase,
    private val getContentRestrictionUseCase: GetContentRestrictionUseCase,
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
    private val clearUserProfileUseCase: ClearUserProfileUseCase,
    private val getUserScoreUseCase: GetPointsUseCase,
) : BaseViewModel<ProfileUiState, ProfileScreenEffect>(ProfileUiState()),
    ProfileInteractionListener {

    init {
        collectTheme()
        collectUserProfile()
        collectContentRestriction()
        checkLoginStatus()
    }

    override fun onDialogDismissed() {
        updateState {
            it.copy(
                activeDialog = ProfileDialogType.NONE,
                languageOption = LanguageOption(
                    tempSelectedLanguage = it.languageOption.selectedLanguage,
                    isEnglishEnabled = it.languageOption.selectedLanguage == AppLanguage.EN,
                ),
                themeOption = ThemeOption(
                    tempSelectedTheme = it.themeOption.selectedTheme,
                    isDarkThemeEnabled = it.themeOption.selectedTheme == AppTheme.DARK.name,
                ),
                contentRestrictionOption = ContentRestrictionOption(
                    tempSelectedRestriction = it.contentRestrictionOption.selectedRestriction,
                    isStrictSelected =
                        it.contentRestrictionOption
                            .selectedRestriction == ContentRestriction.STRICT.name,
                    isModeratedSelected =
                        it.contentRestrictionOption
                            .selectedRestriction == ContentRestriction.MODERATE.name,
                    isOffSelected =
                        it.contentRestrictionOption
                            .selectedRestriction == ContentRestriction.OFF.name,
                ),

                )
        }
    }

    override fun onWatchHistoryClick() {
        sendNewEffect(ProfileScreenEffect.NavigateToWatchHistoryScreen)
    }

    override fun onMyRatingClick() {
        sendNewEffect(ProfileScreenEffect.NavigateToMyRatingScreen)
    }

    override fun onLanguageClick() {
        updateState { it.copy(activeDialog = ProfileDialogType.LANGUAGE) }
    }

    override fun onAppThemeClick() {
        updateState { it.copy(activeDialog = ProfileDialogType.THEME) }

    }

    override fun onSettingsClick() {
        updateState { it.copy(activeDialog = ProfileDialogType.SETTINGS) }
    }

    override fun onDarkThemeSelected() {
        updateState {
            it.copy(
                themeOption = ThemeOption(
                    isDarkThemeEnabled = true,
                    tempSelectedTheme = AppTheme.DARK.name,
                )
            )
        }
    }

    override fun onLightThemeSelected() {
        updateState {
            it.copy(
                themeOption = ThemeOption(
                    isDarkThemeEnabled = false,
                    tempSelectedTheme = AppTheme.LIGHT.name,
                )
            )
        }
    }

    override fun onApplyThemeOption() {
        viewModelScope.launch {
            val selectedTheme = AppTheme.valueOf(
                state
                    .value
                    .themeOption
                    .tempSelectedTheme
            )
            setThemeUseCase(selectedTheme)
            updateState {
                it.copy(
                    activeDialog = ProfileDialogType.NONE,
                    themeOption = it.themeOption.copy(
                        selectedTheme = it.themeOption.tempSelectedTheme,
                    )
                )
            }
        }
    }

    override fun onArabicSelected() {
        updateState {
            it.copy(
                languageOption = LanguageOption(
                    isEnglishEnabled = false,
                    tempSelectedLanguage = AppLanguage.AR,
                )
            )
        }
    }

    override fun onEnglishSelected() {
        updateState {
            it.copy(
                languageOption = LanguageOption(
                    isEnglishEnabled = true,
                    tempSelectedLanguage = AppLanguage.EN
                )
            )
        }
    }

    override fun onApplyLanguageOption() {
        val appLocale: LocaleListCompat =
            LocaleListCompat.forLanguageTags(
                state
                    .value
                    .languageOption
                    .tempSelectedLanguage
                    .name
                    .lowercase()
            )
        AppCompatDelegate.setApplicationLocales(appLocale)
        updateState {
            it.copy(
                languageOption = LanguageOption(
                    selectedLanguage = it.languageOption.tempSelectedLanguage,
                ),
                activeDialog = ProfileDialogType.NONE
            )
        }
    }

    override fun onChangePasswordClicked() =
        sendNewEffect(ProfileScreenEffect.NavigateToChangePasswordScreen)

    override fun onSettingsLogoutClicked() =
        updateState { it.copy(activeDialog = ProfileDialogType.LOGOUT) }

    override fun onDialogLogoutClicked() {
        viewModelScope.launch {
            logoutUseCase()
            clearUserProfileUseCase()
            updateState { it.copy(isLoggedIn = false) }
        }

        sendNewEffect(ProfileScreenEffect.NavigateToLoginScreen)
    }

    override fun onContentRestrictionClicked() {
        updateState { it.copy(activeDialog = ProfileDialogType.CONTENT_RESTRICTION) }
    }

    override fun onStrictSelected() {
        updateState {
            it.copy(
                contentRestrictionOption = ContentRestrictionOption(
                    isStrictSelected = true,
                    isModeratedSelected = false,
                    isOffSelected = false,
                    tempSelectedRestriction = ContentRestriction.STRICT.name
                ),
            )
        }
    }

    override fun onModerateSelected() {
        updateState {
            it.copy(
                contentRestrictionOption = ContentRestrictionOption(
                    isStrictSelected = false,
                    isModeratedSelected = true,
                    isOffSelected = false,
                    tempSelectedRestriction = ContentRestriction.MODERATE.name
                )
            )
        }
    }

    override fun onOffRestrictionSelected() {
        updateState {
            it.copy(
                contentRestrictionOption = ContentRestrictionOption(
                    isStrictSelected = false,
                    isModeratedSelected = false,
                    isOffSelected = true,
                    tempSelectedRestriction = ContentRestriction.OFF.name
                )
            )
        }
    }

    override fun onSaveContentRestriction() {
        viewModelScope.launch {
            val selectRestriction = ContentRestriction.valueOf(
                state.value.contentRestrictionOption.tempSelectedRestriction
            )
            val percentage = getContentRestrictionPercentage(selectRestriction.name)
            setContentRestrictionUseCase(selectRestriction)
            updateState {
                it.copy(
                    contentRestrictionOption = ContentRestrictionOption(
                        selectedRestriction = it.contentRestrictionOption.tempSelectedRestriction,
                        contentRestrictionPercentage = percentage,
                    ),
                    activeDialog = ProfileDialogType.NONE,
                )
            }
        }
    }

    private fun getContentRestrictionPercentage(restriction: String): Int {
        return when (restriction.uppercase()) {
            "STRICT" -> 100
            "MODERATE" -> 50
            "OFF" -> 0
            else -> 100
        }
    }

    private fun collectTheme() {
        viewModelScope.launch {
            getThemeUseCase().collect { theme ->
                val appTheme = theme

                updateState {
                    it.copy(
                        themeOption = ThemeOption(
                            selectedTheme = appTheme,
                            isDarkThemeEnabled = appTheme == AppTheme.DARK.name
                        ),
                    )
                }
            }
        }
    }


    private fun collectContentRestriction() {
        viewModelScope.launch {
            getContentRestrictionUseCase().collect { contentRestriction ->
                val appContentRestriction = contentRestriction
                    ?: ContentRestriction.STRICT.name
                val percentage = getContentRestrictionPercentage(appContentRestriction)

                updateState {
                    it.copy(
                        contentRestrictionOption = ContentRestrictionOption(
                            selectedRestriction = appContentRestriction,
                            isStrictSelected =
                                contentRestriction == ContentRestriction.STRICT.name,
                            isModeratedSelected =
                                contentRestriction == ContentRestriction.MODERATE.name,
                            isOffSelected = contentRestriction == ContentRestriction.OFF.name,
                            contentRestrictionPercentage = percentage
                        )
                    )
                }

            }
        }
    }

    private fun collectUserProfile() {
        viewModelScope.launch {
            observeUserProfileUseCase().collect { user ->
                val points = if (user != null) {
                    getUserScoreUseCase(user.id)
                } else {
                    0
                }
                updateState { s ->
                    s.copy(
                        userAvatarUrl = user?.avatarUrl?.takeIf { it.isNotBlank() },
                        userName = user?.username.orEmpty(),
                        isLoggedIn = user != null,
                        userPoints = points
                    )
                }
            }
        }
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            getLoginStatus().collect { loggedIn ->
                updateState { it.copy(isLoggedIn = loggedIn) }
            }
        }
    }
}