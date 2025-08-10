package com.berlin.aflami.viewmodel.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.entity.AppLanguage
import com.berlin.entity.AppTheme
import com.berlin.entity.ContentRestriction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import usecase.auth.GetLoginStatus
import usecase.profile.GetContentRestrictionUseCase
import usecase.profile.GetLanguageUseCase
import usecase.profile.GetThemeUseCase
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
    val getLoginStatus: GetLoginStatus,
    val setContentRestrictionUseCase: SetContentRestrictionUseCase,
    val getContentRestrictionUseCase: GetContentRestrictionUseCase,

    ) : BaseViewModel<ProfileUiState, ProfileScreenEffect>(ProfileUiState()),
    ProfileInteractionListener {

    init {
        collectTheme()
        collectLanguage()
        collectContentRestriction()
        checkLoginStatus()
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
            getLanguageUseCase().collect { language ->
                val appLanguage = language ?: AppLanguage.AR.name

                updateState {
                    it.copy(
                        selectedLanguage = appLanguage,
                        isArabicSelected = language == AppLanguage.AR.name,
                        isEnglishSelected = language == AppLanguage.EN.name,
                        isLanguageEN = language == AppLanguage.EN.name
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
                selectedTheme = AppTheme.DARK.name,
                isDarkThemeEnabled = true,

                )
        }

    }

    override fun onLightThemeSelected() {
        updateState {
            it.copy(
                isDarkThemeSelected = false,
                isLightThemeSelected = true,
                selectedTheme = AppTheme.LIGHT.name,
                isDarkThemeEnabled = false,
            )
        }

    }

    override fun onArabicSelected() {
        updateState {
            it.copy(
                isEnglishSelected = false,
                isArabicSelected = true,
                selectedLanguage = AppLanguage.AR.name,
                isLanguageEN = false,
            )
        }
    }

    override fun onEnglishSelected() {
        updateState {
            it.copy(
                isEnglishSelected = true,
                isArabicSelected = false,
                selectedLanguage = AppLanguage.EN.name,
                isLanguageEN = true,
            )
        }
    }

    override fun onApplyThemeOption() {
        viewModelScope.launch {
            val selectedTheme = AppTheme.valueOf(state.value.selectedTheme)
            setThemeUseCase(selectedTheme)
            updateState { it.copy(activeDialog = ProfileDialogType.NONE) }
        }
    }

    override fun onApplyLanguageOption() {
        viewModelScope.launch {
            val selectedLanguage = AppLanguage.valueOf(state.value.selectedLanguage)
            setLanguageUseCase(selectedLanguage)
            updateState { it.copy(activeDialog = ProfileDialogType.NONE) }
            sendNewEffect(ProfileScreenEffect.RefreshActivity)
        }
    }

    override fun onDialogDismissed() {
        updateState { it.copy(activeDialog = ProfileDialogType.NONE) }
    }

    override fun onChangePasswordClicked() =
        sendNewEffect(ProfileScreenEffect.NavigateToChangePasswordScreen)

    override fun onContentRestrictionClicked() {
        updateState { it.copy(activeDialog = ProfileDialogType.CONTENT_RESTRICTION) }
    }

    override fun onStrictSelected() {
        updateState {
            it.copy(
                isStrictSelected = true,
                isModeratedSelected = false,
                isOffSelected = false
            )
        }
    }

    override fun onModerateSelected() {
        updateState {
            it.copy(
                isStrictSelected = false,
                isModeratedSelected = true,
                isOffSelected = false
            )
        }
    }

    override fun onOffRestrictionSelected() {
        updateState {
            it.copy(
                isStrictSelected = false,
                isModeratedSelected = false,
                isOffSelected = true
            )
        }
    }

    override fun onSaveContentRestriction() {
        viewModelScope.launch {
            val selectRestriction = ContentRestriction.valueOf(state.value.selectedRestriction)
            val percentage = getContentRestrictionPercentage(selectRestriction.name)
            Log.d("FireBaseModelManager", "onSaveContentRestriction: ${selectRestriction.name}")
            setContentRestrictionUseCase(selectRestriction)
            Log.d("FireBaseModelManager", "onSaveContentRestriction: ${selectRestriction.name}")
            updateState {
                it.copy(
                    selectedRestriction = selectRestriction.name,
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

    override fun onLogoutClicked() = sendNewEffect(ProfileScreenEffect.NavigateToLoginScreen)

}