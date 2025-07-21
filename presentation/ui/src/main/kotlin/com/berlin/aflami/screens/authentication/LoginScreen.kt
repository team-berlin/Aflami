package com.berlin.aflami.screens.authentication

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.IconButton
import com.berlin.aflami.component.PrimaryButton
import com.berlin.aflami.component.TextField
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.berlin.aflami.component.ThemeAndLocalePreviews

@Composable
fun LoginScreen() {
    LoginContent()
}

@Composable
fun LoginContent() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = Theme.color.gradientColors.streakGradient
                )
            )
            .padding(horizontal = 12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
        ) {
            LoginHeader()
            LoginForm(
                username = username,
                password = password,
                passwordError = passwordError,
                onUsernameChange = { username = it },
                onPasswordChange = { password = it },
                onPasswordErrorChange = { passwordError = it }
            )
            Spacer(modifier = Modifier.height(48.dp))
            LoginButtons()
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.dont_have_account),
                style = Theme.textStyle.body.small,
            )
            Text(
                text = stringResource(R.string.create_account),
                style = Theme.textStyle.body.small,
                color = Theme.color.primary,
                modifier = Modifier
                    .clickable { }
                    .padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun LoginHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        IconButton(
            painter = painterResource(com.berlin.designsystem.R.drawable.home_logo),
            contentDescription = "search",
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = Theme.color.primaryVariant)
                .border(
                    width = 1.dp,
                    color = Theme.color.stroke,
                    shape = RoundedCornerShape(12.dp)
                ),
            paddingValues = PaddingValues(8.dp),
            withBorder = true,
            containerColor = Theme.color.primaryVariant,
            onClick = {}
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = stringResource(R.string.welcome_back),
                color = Theme.color.textColors.title,
                style = Theme.textStyle.title.medium
            )
            Text(
                text = stringResource(R.string.please_enter_info),
                color = Theme.color.textColors.body,
                style = Theme.textStyle.body.medium
            )
        }
    }
}

@Composable
fun LoginForm(
    username: String,
    password: String,
    passwordError: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordErrorChange: (Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            text = username,
            leadingIcon = R.drawable.user_square,
            hintText = stringResource(R.string.username),
            onValueChange = onUsernameChange,
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            text = password,
            hintText = stringResource(R.string.password),
            leadingIcon = R.drawable.door_lock,
            isError = passwordError,
            errorMessage = if (passwordError) stringResource(R.string.incorrect_password) else "",
            isObscured = true,
            onValueChange = onPasswordChange,
            trailingIcon = R.drawable.heroicons_outline,
            onTrailingClick = { onPasswordErrorChange(!passwordError) },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.forgot_password),
            style = Theme.textStyle.body.small,
            color = Theme.color.primary,
            modifier = Modifier
                .align(Alignment.End)
                .clickable {}
                .padding(top = 4.dp)
        )
    }
}

@Composable
fun LoginButtons() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        PrimaryButton(
            onClick = { },
            containerColor = Theme.color.primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            gradientColor = Theme.color.primaryButton
        ) {
            Text(
                text = stringResource(R.string.login),
                style = Theme.textStyle.label.large,
                color = Theme.color.textColors.onPrimary
            )
        }
        PrimaryButton(
            onClick = {},
            containerColor = Theme.color.primaryVariant,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Text(
                text = stringResource(R.string.continue_as_guest),
                style = Theme.textStyle.label.large,
                color = Theme.color.primary
            )
        }
    }
}
@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF0D090B, heightDp = 700, widthDp = 360)
@Composable
fun LoginScreenPreview() {
    AflamiTheme(isDarkTheme = false) {
        LoginScreen()
    }
}