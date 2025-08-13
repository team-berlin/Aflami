package com.berlin.aflami.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun SettingSection(
    isDarkThemeEnabled: Boolean,
    isLanguageEN: Boolean,
    modifier: Modifier = Modifier,
    onLanguageClick: () -> Unit = {},
    onThemeClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {

            SettingsItem(
                icon = painterResource(R.drawable.language),
                title = stringResource(R.string.language),
                subtitle = if (isLanguageEN) stringResource(R.string.eng)
                else stringResource(R.string.ar),
                onClick = onLanguageClick
            )
            SettingsItem(
                icon = painterResource(R.drawable.app_theme),
                title = stringResource(R.string.app_theme),
                subtitle = if (isDarkThemeEnabled) stringResource(R.string.dark)
                else stringResource(R.string.light),
                onClick = onThemeClick
            )
            SettingsItem(
                icon = painterResource(R.drawable.settings),
                title = stringResource(R.string.settings),
                subtitle = "",
                onClick = onSettingsClick
            )
        }
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = stringResource(R.string.v1_1),
            style = Theme.textStyle.label.small,
            color = Theme.color.textColors.hint,
            modifier = Modifier .padding(bottom = 12.dp)
        )
        Spacer(modifier = Modifier.height(60.dp))


    }


}