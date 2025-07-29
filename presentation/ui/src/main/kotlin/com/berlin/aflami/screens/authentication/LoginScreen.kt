package com.berlin.aflami.screens.authentication

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import android.net.Uri
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.berlin.aflami.component.IconButton
import com.berlin.aflami.component.buttons.PrimaryButton
import com.berlin.aflami.component.TextField
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import androidx.compose.ui.tooling.preview.Preview
import com.berlin.ui.R
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import com.berlin.aflami.component.SnackBar
import com.berlin.aflami.component.SnackBarStatus
import com.berlin.aflami.component.buttons.ButtonState
import com.berlin.aflami.component.buttons.SecondaryButton
import com.berlin.aflami.viewmodel.login.FormUiState
import com.berlin.aflami.viewmodel.login.LoginEffect
import com.berlin.aflami.viewmodel.login.LoginInteractionListener
import com.berlin.aflami.viewmodel.login.LoginUiState
import com.berlin.aflami.viewmodel.login.LoginViewmodel
import com.example.navigation.Destination
import com.example.navigation.NavigationConstants.Routes.WEB_VIEW_ROUTE
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(
    viewmodel: LoginViewmodel = koinViewModel(),
    navController: NavController
) {
    val uiState by viewmodel.state.collectAsState()
    LoginContent(uiState, viewmodel)

    LaunchedEffect(Unit) {
        viewmodel.effect.collect {
            when (it) {
                LoginEffect.NavigateToHome -> {
                    navController.navigate(
                        Destination.HomeScreen.route
                    )
                }
                LoginEffect.NavigateToCreateAccount -> {
                    val encodedUrl = Uri.encode(REGISTER_URL)
                    navController.navigate("$WEB_VIEW_ROUTE/$encodedUrl")
                }

                LoginEffect.NavigateToForgotPassword -> {
                    val encodedUrl = Uri.encode(RESET_PASSWORD_URL)
                    navController.navigate("$WEB_VIEW_ROUTE/$encodedUrl")
                }
            }
        }
    }
}


@Composable
fun LoginContent(uiState: LoginUiState, listener: LoginInteractionListener) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = Theme.color.gradientColors.streakGradient
                )
            )
            .padding(start = 12.dp, end = 12.dp, top = 24.dp, bottom = 16.dp)
    ) {
        CirclesBackground()
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            LoginLogo()
            WelcomeText()
            FormLogin(
                modifier = Modifier.padding(bottom = 24.dp),
                uiState = uiState.formUiState,
                onUsernameChanged = listener::onUsernameChanged,
                onPasswordChanged = listener::onPasswordChanged,
                onTrailingIconClicked = listener::onTrailingIconClicked,
                onForgotPasswordClicked = listener::onForgotPasswordClicked
            )
            LoginButtons(
                isLoading = uiState.isLoading,
                isError = uiState.isError,
                isLoginButtonEnabled = uiState.isLoginButtonEnabled,
                onLoginClicked = listener::onLoginClicked,
                onContinueAsGuestClicked = listener::onContinueAsGuestClicked
            )
            Spacer(modifier = Modifier.weight(1f))
            CreateAccount(
                onCreateAccountClicked = listener::onCreateAccountClicked
            )
        }
        AnimatedSnackBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.TopCenter),
            isSnackBarVisible = uiState.isError
        )
    }
}

@Composable
private fun LoginLogo() {
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
private fun WelcomeText() {
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
private fun FormLogin(
    modifier: Modifier = Modifier,
    uiState: FormUiState,
    onUsernameChanged: (CharSequence) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onTrailingIconClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
) {
    Column(modifier = modifier) {
        TextField(
            text = uiState.username,
            leadingIcon = R.drawable.user_square,
            hintText = stringResource(R.string.username),
            onValueChange = { onUsernameChanged(it) },
            modifier = Modifier.fillMaxWidth()
        )
        var passwordError by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            text = uiState.password,
            hintText = stringResource(R.string.password),
            leadingIcon = R.drawable.door_lock,
            isError = passwordError,
            errorMessage = if (passwordError) stringResource(R.string.incorrect_password) else "",
            isObscured = uiState.isPasswordObscured,
            onValueChange = { onPasswordChanged(it) },
            trailingIcon = R.drawable.eye,
            onTrailingIconClicked = onTrailingIconClicked,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.forgot_password),
            style = Theme.textStyle.body.small,
            color = Theme.color.primary,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onForgotPasswordClicked() }
                .padding(top = 4.dp)
        )
    }
}

@Composable
fun LoginButtons(
    isLoading: Boolean,
    isLoginButtonEnabled: Boolean,
    onLoginClicked: () -> Unit,
    onContinueAsGuestClicked: () -> Unit,
    isError: Boolean,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        PrimaryButton(
            onClick = onLoginClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            state = when {
                isError || !isLoginButtonEnabled -> ButtonState.DISABLED
                isLoading -> ButtonState.LOADING
                else -> ButtonState.IDLE
            }
        ) {
            Text(
                stringResource(R.string.login),
                style = Theme.textStyle.label.large,
                color = Theme.color.textColors.onPrimary
            )
        }

        SecondaryButton(
            onClick = onContinueAsGuestClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            border = null
        ) {
            Text(
                stringResource(R.string.continue_as_guest),
                style = Theme.textStyle.label.large,
                color = Theme.color.primary
            )
        }
    }
}

@Composable
private fun CreateAccount(modifier: Modifier = Modifier, onCreateAccountClicked: () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
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
                .clickable { onCreateAccountClicked() }
                .padding(start = 4.dp)
        )
    }
}

@Composable
private fun AnimatedSnackBar(
    modifier: Modifier = Modifier,
    isSnackBarVisible: Boolean
) {
    AnimatedVisibility(
        visible = isSnackBarVisible, enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight }, animationSpec = spring(
                stiffness = Spring.StiffnessLow, dampingRatio = Spring.DampingRatioMediumBouncy
            )
        ) + fadeIn(),

        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight }, animationSpec = spring(
                stiffness = Spring.StiffnessMedium, dampingRatio = Spring.DampingRatioNoBouncy
            )
        ) + fadeOut()
    ) {
        SnackBar(
            modifier = modifier,
            status = SnackBarStatus.ERROR,
            text = stringResource(id = R.string.login_error_message),
            iconPainter = painterResource(id = com.berlin.designsystem.R.drawable.error)
        )
    }
}

data class Circles(val size: Dp, val xScreen: Int, val yScreen: Int)

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun CirclesBackground() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    val circles = listOf(
        Circles(32.dp, 34, -18),
        Circles(64.dp, 193, -10),
        Circles(64.dp, 304, 128),
        Circles(64.dp, 234, 358),
        Circles(64.dp, 324, 498),
        Circles(24.dp, 246, 657),
        Circles(32.dp, 167, 566),
        Circles(100.dp, -22, 734),
        Circles(100.dp, -22, 304),
        Circles(24.dp, 16, 617),
        Circles(40.dp, 339, 19)
    )

    circles.forEach { circle ->
        val infiniteTransition = rememberInfiniteTransition()
        val animatedScale by infiniteTransition.animateFloat(
            initialValue = 0.9f,
            targetValue = 1.1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Box(
            modifier = Modifier
                .size(circle.size * animatedScale)
                .offset(
                    x = (circle.xScreen / 360f * screenWidth).dp,
                    y = (circle.yScreen / 800f * screenHeight).dp
                )
                .clip(CircleShape)
                .background(
                    Theme.color.statusColors.backgroundCircles.copy(alpha = 0.04f)
                )
        )
    }
}

@Preview(showBackground = true, heightDp = 800, widthDp = 360)
@Composable
private fun LoginScreenPreview() {
    AflamiTheme(isDarkTheme = false) {
        LoginScreen(navController = rememberNavController())
    }
}

private const val REGISTER_URL = "https://www.themoviedb.org/signup"
private const val RESET_PASSWORD_URL = "https://www.themoviedb.org/reset-password"
