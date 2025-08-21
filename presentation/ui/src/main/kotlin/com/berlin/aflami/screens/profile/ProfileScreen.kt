package com.berlin.aflami.screens.profile

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.ThemeAndLocalePreviews
import com.berlin.aflami.navigation.LoginDestination
import com.berlin.aflami.navigation.MyRatingDestination
import com.berlin.aflami.navigation.WatchHistoryDestination
import com.berlin.aflami.navigation.WebViewDestination
import com.berlin.aflami.screens.RequiredLoggedInPlaceholder
import com.berlin.aflami.screens.profile.components.ContentRestrictionDialog
import com.berlin.aflami.screens.profile.components.LogoutDialog
import com.berlin.aflami.screens.profile.components.OptionsDialog
import com.berlin.aflami.screens.profile.components.ProfileSection
import com.berlin.aflami.screens.profile.components.SettingSection
import com.berlin.aflami.screens.profile.components.SettingsDialog
import com.berlin.aflami.screens.profile.components.WatchHistoryRatingSection
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.profile.ProfileDialogType
import com.berlin.aflami.viewmodel.profile.ProfileInteractionListener
import com.berlin.aflami.viewmodel.profile.ProfileScreenEffect
import com.berlin.aflami.viewmodel.profile.ProfileUiState
import com.berlin.aflami.viewmodel.profile.ProfileViewModel
import com.berlin.ui.R

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profileScreenState by viewModel.state.collectAsStateWithLifecycle()
    val navController = Theme.navController

    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        visible = profileScreenState.isLoggedIn == null
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(R.string.loading)
        )
    }

    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        visible = profileScreenState.isLoggedIn == true
    ) { ProfileContent(profileScreenState, viewModel) }

    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        visible = profileScreenState.isLoggedIn == false
    ) { RequiredLoggedInPlaceholder { navController.navigate(LoginDestination) } }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { newEffect ->
            watchHistoryReceiveEffect(navController = navController, effect = newEffect)
        }
    }
}

private fun watchHistoryReceiveEffect(
    navController: NavController,
    effect: ProfileScreenEffect
) {
    when (effect) {
        ProfileScreenEffect.NavigateToMyRatingScreen -> {
            navController.navigate(
                MyRatingDestination
            )
        }

        ProfileScreenEffect.NavigateToWatchHistoryScreen -> {
            navController.navigate(
                WatchHistoryDestination
            )
        }

        ProfileScreenEffect.NavigateToChangePasswordScreen -> {
            navController.navigate(WebViewDestination(RESET_PASSWORD_URL))
        }

        ProfileScreenEffect.RefreshActivity -> {
            (navController.context as? ComponentActivity)?.recreate()
        }

        ProfileScreenEffect.NavigateToLoginScreen -> {
            navController.navigate(LoginDestination)
        }
    }
}

@Composable
private fun ProfileContent(
    profileScreenState: ProfileUiState,
    profileScreenInteractionListener: ProfileInteractionListener,
) {

    when (profileScreenState.activeDialog) {
        ProfileDialogType.THEME -> {
            OptionsDialog(
                onDismiss = { profileScreenInteractionListener.onDialogDismissed() },
                onApplyClick = { profileScreenInteractionListener.onApplyThemeOption() },
                title = R.string.app_theme,
                firstOptionTitleRes = R.string.dark,
                secondOptionTitleRes = R.string.light,
                firstOptionIconRes = com.berlin.designsystem.R.drawable.dark,
                secondOptionIconRes = com.berlin.designsystem.R.drawable.light,
                isFirstOptionSelected = profileScreenState.themeOption.isDarkThemeEnabled,
                isSecondOptionSelected = profileScreenState.themeOption.isDarkThemeEnabled.not(),
                onFirstOptionClick = { profileScreenInteractionListener.onDarkThemeSelected() },
                onSecondOptionClick = { profileScreenInteractionListener.onLightThemeSelected() },
                isThemeDialog = true
            )
        }

        ProfileDialogType.LANGUAGE -> {
            OptionsDialog(
                onDismiss = { profileScreenInteractionListener.onDialogDismissed() },
                onApplyClick = { profileScreenInteractionListener.onApplyLanguageOption() },
                title = R.string.language,
                firstOptionTitleRes = R.string.language_dialog_english,
                secondOptionTitleRes = R.string.language_dialog_arabic,
                firstOptionIconRes = com.berlin.designsystem.R.drawable.english,
                secondOptionIconRes = com.berlin.designsystem.R.drawable.arabic,
                isFirstOptionSelected = profileScreenState.languageOption.isEnglishEnabled,
                isSecondOptionSelected = profileScreenState.languageOption.isEnglishEnabled.not(),
                onFirstOptionClick = { profileScreenInteractionListener.onEnglishSelected() },
                onSecondOptionClick = { profileScreenInteractionListener.onArabicSelected() },
                isThemeDialog = false
            )
        }

        ProfileDialogType.SETTINGS -> {
            SettingsDialog(
                onDismiss = { profileScreenInteractionListener.onDialogDismissed() },
                onFirstOptionClick = { profileScreenInteractionListener.onChangePasswordClicked() },
                onSecondOptionClick = { profileScreenInteractionListener.onContentRestrictionClicked() },
                onThirdOptionClick = { profileScreenInteractionListener.onSettingsLogoutClicked() },
            )
        }

        ProfileDialogType.CONTENT_RESTRICTION -> {
            ContentRestrictionDialog(
                onDismiss = { profileScreenInteractionListener.onDialogDismissed() },
                title = R.string.setting_dialog_content_restriction,
                firstOptionTitleRes = R.string.strict,
                secondOptionTitleRes = R.string.moderate,
                thirdOptionTitleRes = R.string.off,
                isFirstOptionSelected = profileScreenState.contentRestrictionOption.isStrictSelected,
                isSecondOptionSelected = profileScreenState.contentRestrictionOption.isModeratedSelected,
                isThirdOptionSelected = profileScreenState.contentRestrictionOption.isOffSelected,
                onFirstOptionClick = { profileScreenInteractionListener.onStrictSelected() },
                onSecondOptionClick = { profileScreenInteractionListener.onModerateSelected() },
                onThirdOptionClick = { profileScreenInteractionListener.onOffRestrictionSelected() },
                onSaveClick = { profileScreenInteractionListener.onSaveContentRestriction() },
                firstOptionSubTitleIdRes = R.string.strict_description,
                secondOptionSubTitleIdRes = R.string.moderate_description,
                thirdOptionSubTitleIdRes = R.string.off_description
            )
        }

        ProfileDialogType.LOGOUT -> {
            LogoutDialog(
                onDismiss = { profileScreenInteractionListener.onDialogDismissed() },
                title = R.string.setting_dialog_logout,
                onLogoutClick = { profileScreenInteractionListener.onDialogLogoutClicked() },
            )
        }
        else -> Unit
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()

            .background(Theme.color.surface)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 56.dp)
    )
    {
        ProfileSection(
            userAvatar = profileScreenState.userAvatarUrl ?: "",
            userName = profileScreenState.userName,
            userScore = profileScreenState.userPoints,
            if (profileScreenState.themeOption.isDarkThemeEnabled)
                painterResource(R.drawable.profile_cover_night)
            else painterResource(R.drawable.profile_cover),
            )
        Spacer(modifier = Modifier.height(24.dp))
        WatchHistoryRatingSection(
            onWatchHistoryClick = { profileScreenInteractionListener.onWatchHistoryClick() },
            onMyRatingClick = { profileScreenInteractionListener.onMyRatingClick() }
        )
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(thickness = 1.dp, color = Theme.color.stroke)
        Spacer(modifier = Modifier.height(24.dp))
        SettingSection(
            isLanguageEN = profileScreenState.languageOption.isEnglishEnabled,
            isDarkThemeEnabled = profileScreenState.themeOption.isDarkThemeEnabled,
            onThemeClick = { profileScreenInteractionListener.onAppThemeClick() },
            onLanguageClick = { profileScreenInteractionListener.onLanguageClick() },
            onSettingsClick = { profileScreenInteractionListener.onSettingsClick() },
        )
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.v1_1),
            style = Theme.textStyle.label.small,
            color = Theme.color.textColors.hint,
            modifier = Modifier
                .padding(bottom = 12.dp)
        )
    }
}


@ThemeAndLocalePreviews
@Composable
private fun PreviewProfileSection() {
    AflamiTheme {
        ProfileScreen()
    }
}

private const val RESET_PASSWORD_URL = "https://www.themoviedb.org/reset-password"