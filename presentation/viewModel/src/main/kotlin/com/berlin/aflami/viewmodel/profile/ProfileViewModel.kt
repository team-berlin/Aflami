package com.berlin.aflami.viewmodel.profile

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
import usecase.profile.GetLanguageUseCase
import usecase.profile.GetThemeUseCase
import usecase.profile.ObserveUserProfileUseCase
import usecase.profile.SetContentRestrictionUseCase
import usecase.profile.SetLanguageUseCase
import usecase.profile.SetThemeUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val getLanguageUseCase: GetLanguageUseCase,
    val getThemeUseCase: GetThemeUseCase,
    val setLanguageUseCase: SetLanguageUseCase,
    val setThemeUseCase: SetThemeUseCase,
    val getLoginStatus: GetLoginUseCase,
    val logoutUseCase: LogoutUseCase,
    val setContentRestrictionUseCase: SetContentRestrictionUseCase,
    val getContentRestrictionUseCase: GetContentRestrictionUseCase,
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
    private val clearUserProfileUseCase: ClearUserProfileUseCase,
    private val getUserScoreUseCase: GetPointsUseCase,
) : BaseViewModel<ProfileUiState, ProfileScreenEffect>(ProfileUiState()),
    ProfileInteractionListener {

    init {
        collectTheme()
        collectLanguage()
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
                    isEnglishEnabled = it.languageOption.selectedLanguage == AppLanguage.EN.name,
                ),

                themeOption = ThemeOption(
                    tempSelectedTheme = it.themeOption.selectedTheme,
                    isDarkThemeEnabled = it.themeOption.selectedTheme == AppTheme.DARK.name,
                ),
                contentRestrictionOption = ContentRestrictionOption(
                    tempSelectedRestriction = it.contentRestrictionOption.selectedRestriction,
                    isStrictSelected = it.contentRestrictionOption.selectedRestriction == ContentRestriction.STRICT.name,
                    isModeratedSelected = it.contentRestrictionOption.selectedRestriction == ContentRestriction.MODERATE.name,
                    isOffSelected = it.contentRestrictionOption.selectedRestriction == ContentRestriction.OFF.name,
                ),

            )
        }
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            getLoginStatus().collect { loggedIn ->
                updateState { it.copy(isLoggedIn = loggedIn) }
            }
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
            val selectedTheme = AppTheme.valueOf(state.value.themeOption.tempSelectedTheme)
            setThemeUseCase(selectedTheme)
            updateState {
                it.copy(
                    activeDialog = ProfileDialogType.NONE,
                    themeOption = ThemeOption(
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
                tempSelectedLanguage = AppLanguage.AR.name,
                )
            )
        }
    }

    override fun onEnglishSelected() {
        updateState {
            it.copy(
                languageOption = LanguageOption(
                    isEnglishEnabled = true,
                tempSelectedLanguage = AppLanguage.EN.name,
                )
            )
        }
    }

    override fun onApplyLanguageOption() {
        viewModelScope.launch {
            val selectedLanguage =
                AppLanguage.valueOf(state.value.languageOption.tempSelectedLanguage)
            setLanguageUseCase(selectedLanguage)
            updateState {
                it.copy(
                    languageOption = LanguageOption(
                        selectedLanguage = it.languageOption.tempSelectedLanguage,
                    ),
                    activeDialog = ProfileDialogType.NONE
                )
            }
            sendNewEffect(ProfileScreenEffect.RefreshActivity)
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
                val appTheme = theme ?: AppTheme.DARK.name

                updateState {
                    it.copy(
                        themeOption = ThemeOption(
                            selectedTheme = appTheme,
                            isDarkThemeEnabled = theme == AppTheme.DARK.name
                        ),

                        )
                }
            }
        }
    }

    private fun collectLanguage() {
        viewModelScope.launch {
            getLanguageUseCase().collect { currentLanguage ->
                val appLanguage =
                    currentLanguage ?: AppLanguage.valueOf(
                        state.value.languageOption.selectedLanguage
                    ).name
                updateState {
                    it.copy(
                        languageOption = LanguageOption(
                        selectedLanguage = appLanguage,
                        isEnglishEnabled = appLanguage == AppLanguage.EN.name,
                        )
                    )
                }
            }
        }
    }

    private fun collectContentRestriction() {
        viewModelScope.launch {
            getContentRestrictionUseCase().collect { contentRestriction ->
                val appContentRestriction = contentRestriction ?: ContentRestriction.STRICT.name
                val percentage = getContentRestrictionPercentage(appContentRestriction)

                updateState {
                    it.copy(
                        contentRestrictionOption = ContentRestrictionOption(
                        selectedRestriction = appContentRestriction,
                        isStrictSelected = contentRestriction == ContentRestriction.STRICT.name,
                        isModeratedSelected = contentRestriction == ContentRestriction.MODERATE.name,
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
}