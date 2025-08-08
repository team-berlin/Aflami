package com.berlin.aflami.viewmodel.profile

interface ProfileInteractionListener {
    fun onWatchHistoryClick()
    fun onMyRatingClick()
    fun onLanguageClick()
    fun onAppThemeClick()
    fun onSettingsClick()
    fun onDarkThemeSelected()
    fun onLightThemeSelected()
    fun onArabicSelected()
    fun onEnglishSelected()
    fun onApplyThemeOption()
    fun onApplyLanguageOption()
    fun onDialogDismissed()
    fun onChangePasswordClicked()
}
