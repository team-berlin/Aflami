package com.berlin.aflami.viewmodel.profile

import com.berlin.aflami.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
    val setThemeUseCase: SetThemeUseCase

) : BaseViewModel<ProfileUiState, ProfileScreenEffect>(ProfileUiState()),
    ProfileInteractionListener {
    override fun onWatchHistoryClick() {
        TODO("Not yet implemented")
    }

    override fun onMyRatingClick() {
        TODO("Not yet implemented")
    }

    override fun onLanguageClick() {
        TODO("Not yet implemented")
    }

    override fun onAppThemeClick() {
        TODO("Not yet implemented")
    }

    override fun onSettingsClick() {
        TODO("Not yet implemented")
    }

}