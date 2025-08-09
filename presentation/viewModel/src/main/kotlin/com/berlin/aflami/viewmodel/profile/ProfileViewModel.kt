package com.berlin.aflami.viewmodel.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.entity.AppLanguage
import com.berlin.entity.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import usecase.auth.GetLoginStatus
import usecase.profile.GetLanguageUseCase
import usecase.profile.GetThemeUseCase
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

) : BaseViewModel<ProfileUiState, ProfileScreenEffect>(ProfileUiState()),
    ProfileInteractionListener {

    init {
        collectTheme()
        collectLanguage()
        checkLoginStatus()
    }

    private fun collectTheme() {
        viewModelScope.launch {
            getThemeUseCase().collect { theme ->
                val safeTheme = theme ?: AppTheme.DARK.name

                updateState {
                    it.copy(
                        selectedTheme = safeTheme,
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
                val safeTheme = language ?: AppLanguage.AR.name

                updateState {
                    it.copy(
                        selectedLanguage = safeTheme,
                        isArabicSelected = language == AppLanguage.AR.name,
                        isEnglishSelected = language == AppLanguage.EN.name,
                        isLanguageEN = language == AppLanguage.EN.name
                    )
                }
            }
        }
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            val loggedIn = getLoginStatus()
            updateState { it.copy(isLoggedIn = loggedIn) }
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
        TODO("Not yet implemented")
    }

    override fun onModerateSelected() {
        TODO("Not yet implemented")
    }

    override fun onOffRestrictionSelected() {
        TODO("Not yet implemented")
    }

    override fun onSaveContentRestriction() {
        TODO("Not yet implemented")
    }

    override fun onLogoutClicked() = sendNewEffect(ProfileScreenEffect.NavigateToLoginScreen)


    private fun updateError(errorUiState: ErrorUiState) {
        Log.e("HomeScreenViewModel", "updatePopularUiStateWithError: ${errorUiState.message}")

    }
}