package com.berlin.aflami.viewmodel.profile

import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.entity.AppLanguage
import com.berlin.entity.AppTheme
import com.berlin.entity.ContentRestriction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import usecase.auth.GetLoginUseCase
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
                tempSelectedLanguage = it.selectedLanguage,
                isArabicSelected = it.selectedLanguage == AppLanguage.AR.name,
                isEnglishSelected = it.selectedLanguage == AppLanguage.EN.name,
                tempSelectedTheme = it.selectedTheme,
                isDarkThemeSelected = it.selectedTheme == AppTheme.DARK.name,
                isLightThemeSelected = it.selectedTheme == AppTheme.LIGHT.name,
                tempSelectedRestriction = it.selectedRestriction,
                isStrictSelected = it.selectedRestriction == ContentRestriction.STRICT.name,
                isModeratedSelected = it.selectedRestriction == ContentRestriction.MODERATE.name,
                isOffSelected = it.selectedRestriction == ContentRestriction.OFF.name,
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
                isDarkThemeSelected = true,
                isLightThemeSelected = false,
                tempSelectedTheme = AppTheme.DARK.name,

                )
        }

    }

    override fun onLightThemeSelected() {
        updateState {
            it.copy(
                isDarkThemeSelected = false,
                isLightThemeSelected = true,
                tempSelectedTheme = AppTheme.LIGHT.name,
            )
        }
    }

    override fun onApplyThemeOption() {
        viewModelScope.launch {
            val selectedTheme = AppTheme.valueOf(state.value.tempSelectedTheme)
            setThemeUseCase(selectedTheme)
            updateState {
                it.copy(
                    activeDialog = ProfileDialogType.NONE,
                    selectedTheme = it.tempSelectedTheme,
                )
            }
        }
    }

    override fun onArabicSelected() {
        updateState {
            it.copy(
                isEnglishSelected = false,
                isArabicSelected = true,
                tempSelectedLanguage = AppLanguage.AR.name,
            )
        }
    }

    override fun onEnglishSelected() {
        updateState {
            it.copy(
                isEnglishSelected = true,
                isArabicSelected = false,
                tempSelectedLanguage = AppLanguage.EN.name,
            )
        }
    }

    override fun onApplyLanguageOption() {
        viewModelScope.launch {
            val selectedLanguage = AppLanguage.valueOf(state.value.tempSelectedLanguage)
            setLanguageUseCase(selectedLanguage)
            updateState {
                it.copy(
                    selectedLanguage = it.tempSelectedLanguage,
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
                isStrictSelected = true,
                isModeratedSelected = false,
                isOffSelected = false,
                tempSelectedRestriction = ContentRestriction.STRICT.name
            )
        }
    }

    override fun onModerateSelected() {
        updateState {
            it.copy(
                isStrictSelected = false,
                isModeratedSelected = true,
                isOffSelected = false,
                tempSelectedRestriction = ContentRestriction.MODERATE.name
            )
        }
    }

    override fun onOffRestrictionSelected() {
        updateState {
            it.copy(
                isStrictSelected = false,
                isModeratedSelected = false,
                isOffSelected = true,
                tempSelectedRestriction = ContentRestriction.OFF.name
            )
        }
    }

    override fun onSaveContentRestriction() {
        viewModelScope.launch {
            val selectRestriction = ContentRestriction.valueOf(state.value.tempSelectedRestriction)
            val percentage = getContentRestrictionPercentage(selectRestriction.name)
            setContentRestrictionUseCase(selectRestriction)
            updateState {
                it.copy(
                    selectedRestriction = it.tempSelectedRestriction,
                    contentRestrictionPercentage = percentage,
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
                        selectedTheme = appTheme,
                        isDarkThemeSelected = theme == AppTheme.DARK.name,
                        isLightThemeSelected = theme == AppTheme.LIGHT.name,
                        isDarkThemeEnabled = theme == AppTheme.DARK.name
                    )
                }
            }
        }
    }

    private fun collectLanguage() {
        viewModelScope.launch {
            getLanguageUseCase().collect { currentLanguage ->
                val appLanguage =
                    currentLanguage ?: AppLanguage.valueOf(state.value.selectedLanguage).name
                updateState {
                    it.copy(
                        selectedLanguage = appLanguage,
                        isArabicSelected = appLanguage == AppLanguage.AR.name,
                        isEnglishSelected = appLanguage == AppLanguage.EN.name,
                        isEnglishEnabled = appLanguage == AppLanguage.EN.name,
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
                        selectedRestriction = appContentRestriction,
                        isStrictSelected = contentRestriction == ContentRestriction.STRICT.name,
                        isModeratedSelected = contentRestriction == ContentRestriction.MODERATE.name,
                        isOffSelected = contentRestriction == ContentRestriction.OFF.name,
                        contentRestrictionPercentage = percentage
                    )
                }

            }
        }
    }

    private fun collectUserProfile() {
        viewModelScope.launch {
            observeUserProfileUseCase()
                .collect { user ->
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
                            userPoints =points
                        )
                    }
                }
        }
    }
}