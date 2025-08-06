package com.berlin.aflami.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.ThemeAndLocalePreviews
import com.berlin.aflami.screens.profile.components.ProfileSection
import com.berlin.aflami.screens.profile.components.SettingSection
import com.berlin.aflami.screens.profile.components.WatchHistoryRatingSection
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun ProfileScreen() {
    ProfileContent()
}

@Composable
private fun ProfileContent() {
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
            isLanguageEN = true,
            isDarkThemeEnabled = true,
            onThemeClick = { /* Handle theme change */ },
            onLanguageClick = { /* Handle language change */ },
            onSettingsClick = { /* Handle settings click */ },
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

