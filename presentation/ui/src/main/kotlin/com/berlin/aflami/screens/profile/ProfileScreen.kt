package com.berlin.aflami.screens.profile

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.berlin.aflami.component.ThemeAndLocalePreviews
import com.berlin.aflami.navigation.LoginDestination
import com.berlin.aflami.navigation.NavigationBarDestinations
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
    var counter by rememberSaveable { mutableIntStateOf(0) }
    if (profileScreenState.isLoggedIn) {
        ProfileContent(profileScreenState, viewModel)
    } else {
        RequiredLoggedInPlaceholder(onAvatarClick = { counter++ }) {
            navController.navigate(LoginDestination)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.effect.collect { newEffect ->
            watchHistoryReceiveEffect(navController = navController, effect = newEffect)
        }
    }
    if (counter == 7) {
        ProfileContent(profileScreenState, viewModel)
    }
}

private fun watchHistoryReceiveEffect(
    navController: NavController,
    effect: ProfileScreenEffect
) {
    when (effect) {
        ProfileScreenEffect.NavigateToMyRatingScreen -> {}
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
            navController.navigate(route = LoginDestination) {
                popUpTo(NavigationBarDestinations.HomeScreen) {
                    inclusive = true
                }
            }

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
                isFirstOptionSelected = profileScreenState.isDarkThemeSelected,
                isSecondOptionSelected = profileScreenState.isLightThemeSelected,
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
                isFirstOptionSelected = profileScreenState.isEnglishSelected,
                isSecondOptionSelected = profileScreenState.isArabicSelected,
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
                isFirstOptionSelected = profileScreenState.isStrictSelected,
                isSecondOptionSelected = profileScreenState.isModeratedSelected,
                isThirdOptionSelected = profileScreenState.isOffSelected,
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
    )
    {
        ProfileSection(
            userAvatar = "",
            userName = "",
            if (profileScreenState.isDarkThemeEnabled)
                painterResource(R.drawable.profile_cover_night)
            else painterResource(R.drawable.profile_cover),

        )
        Spacer(modifier = Modifier.height(24.dp))
        WatchHistoryRatingSection {
            profileScreenInteractionListener.onWatchHistoryClick()
        }
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(thickness = 1.dp, color = Theme.color.stroke)
        Spacer(modifier = Modifier.height(24.dp))
        SettingSection(
            isLanguageEN = profileScreenState.isEnglishEnabled,
            isDarkThemeEnabled = profileScreenState.isDarkThemeEnabled,
            onThemeClick = { profileScreenInteractionListener.onAppThemeClick() },
            onLanguageClick = { profileScreenInteractionListener.onLanguageClick() },
            onSettingsClick = { profileScreenInteractionListener.onSettingsClick() },
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