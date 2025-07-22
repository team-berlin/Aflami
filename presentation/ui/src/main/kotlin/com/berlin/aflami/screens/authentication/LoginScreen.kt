package com.berlin.aflami.screens.authentication

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.berlin.aflami.component.PrimaryButton
import com.berlin.aflami.component.TextField
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import androidx.compose.ui.tooling.preview.Preview
import com.berlin.aflami.component.IconButton
import com.berlin.ui.R

@Composable
fun LoginScreen() {

    LoginContent()
}


@Composable
fun LoginContent() {
    val context = LocalContext.current

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
            verticalArrangement = Arrangement.spacedBy(24.dp),

            ) {
            LoginLogo()
            WelcomeText()
            FormLogin()
            Spacer(modifier = Modifier.height(24.dp))
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
                    .clickable {
                        launchCustomBrowserTab(
                            loadUrl = REGISTER_URL,
                            context = context
                        )
                    }
                    .padding(start = 4.dp)
            )
        }

    }
}

@Composable
fun LoginLogo() {
    IconButton(
        painter = painterResource(com.berlin.designsystem.R.drawable.home_logo),
        contentDescription = stringResource(R.string.logo),
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = Theme.color.primaryVariant,
            )
            .border(
                width = 1.dp,
                color = Theme.color.stroke,
                shape = RoundedCornerShape(12.dp)
            ),
        paddingValues = PaddingValues(8.dp),
        withBorder = true,
        containerColor = Theme.color.primaryVariant,
    )
}

@Composable
fun WelcomeText() {
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

@Composable
fun FormLogin() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column {
        TextField(
            text = username,
            leadingIcon = R.drawable.user_square,
            hintText = stringResource(R.string.username),
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth()
        )
        var passwordError by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            text = password,
            hintText = stringResource(R.string.password),
            leadingIcon = R.drawable.door_lock,
            isError = passwordError,
            errorMessage = if (passwordError) stringResource(R.string.incorrect_password) else "",
            isObscured = true,
            onValueChange = { password = it },
            trailingIcon = R.drawable.eye,
            onTrailingClick = { },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

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
                stringResource(R.string.login),
                style = Theme.textStyle.label.large,
                color = Theme.color.textColors.onPrimary
            )
        }

        PrimaryButton(
            onClick = { },
            containerColor = Theme.color.primaryVariant,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Text(
                stringResource(R.string.continue_as_guest),
                style = Theme.textStyle.label.large,
                color = Theme.color.primary
            )
        }
    }
}


@Preview(showBackground = true, heightDp = 800, widthDp = 360)
@Composable
fun LoginScreenPreview() {
    AflamiTheme(isDarkTheme = false) {
        LoginScreen()
    }
}

private const val REGISTER_URL = "https://www.themoviedb.org/signup"

private fun launchCustomBrowserTab(loadUrl: String, context: Context) {
    val intent = CustomTabsIntent.Builder()
        .setShowTitle(true)
        .setUrlBarHidingEnabled(true)
        .build()
    intent.launchUrl(context, loadUrl.toUri())
}