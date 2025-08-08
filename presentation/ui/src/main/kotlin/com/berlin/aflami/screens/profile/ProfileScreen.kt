package com.berlin.aflami.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.berlin.aflami.component.ThemeAndLocalePreviews
import com.berlin.aflami.navigation.LoginDestination
import com.berlin.aflami.navigation.WebViewDestination
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
    ProfileContent(profileScreenState, viewModel)

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {

                ProfileScreenEffect.NavigateToMyRatingScreen -> TODO()
                ProfileScreenEffect.NavigateToWatchHistoryScreen -> TODO()
                ProfileScreenEffect.NavigateToChangePasswordScreen -> {
                    navController.navigate(WebViewDestination(RESET_PASSWORD_URL))
                }

                ProfileScreenEffect.NavigateToLoginScreen -> {
                    navController.navigate(route = LoginDestination)
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
                onSecondOptionClick = { profileScreenInteractionListener.onLightThemeSelected() }
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
                onSecondOptionClick = { profileScreenInteractionListener.onArabicSelected() }
            )

        }

        ProfileDialogType.SETTINGS -> {
            SettingsDialog(
                onDismiss = { profileScreenInteractionListener.onDialogDismissed() },
                onFirstOptionClick = { profileScreenInteractionListener.onChangePasswordClicked() },
                onSecondOptionClick = { profileScreenInteractionListener.onLogoutClicked() },
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

    )
    {
        ProfileSection(userAvatar = "", userName = "", painterResource(R.drawable.profile_cover))
        Spacer(modifier = Modifier.height(24.dp))
        WatchHistoryRatingSection()
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(thickness = 1.dp, color = Theme.color.stroke)
        Spacer(modifier = Modifier.height(24.dp))
        SettingSection(
            isLanguageEN = profileScreenState.isLanguageEN,
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