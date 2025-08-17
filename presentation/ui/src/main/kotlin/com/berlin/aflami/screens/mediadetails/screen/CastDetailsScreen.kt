package com.berlin.aflami.screens.mediadetails.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.mediadetails.components.MediaCastGrid
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.details.cast.CastDetailsScreenEffect
import com.berlin.aflami.viewmodel.details.cast.CastDetailsScreenListener
import com.berlin.aflami.viewmodel.details.cast.CastScreenState
import com.berlin.aflami.viewmodel.details.cast.CastViewModelScreen
import com.berlin.ui.R

@Composable
fun CastDetailsScreen(
    viewmodel: CastViewModelScreen = hiltViewModel(),
) {
    val navController = Theme.navController
    val castState by viewmodel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewmodel.effect.collect { effect ->
            onReceiveEffect(navController = navController, castDetailsScreenEffect = effect)
        }
    }
    AnimatedVisibility(
        enter =  EnterTransition.None ,
        exit = ExitTransition.None ,
        visible = castState.isScreenLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(R.string.loading)
        )
    }
    AnimatedVisibility(
        visible = castState.errorMessage != null
    ) {
        NoInternetConnectionPlaceholder()
    }

    AnimatedVisibility(
        enter =  EnterTransition.None ,
        exit = ExitTransition.None ,
        visible = !castState.isScreenLoading
    ) {
        CastContent(
            listener = viewmodel,
            castState = castState
        )
    }
}

private fun onReceiveEffect(
    navController: NavController,
    castDetailsScreenEffect: CastDetailsScreenEffect,
) {
    when (castDetailsScreenEffect) {
        is CastDetailsScreenEffect.NavigationBack -> navController.popBackStack()
    }
}

@Composable
fun CastContent(
    listener: CastDetailsScreenListener,
    castState: CastScreenState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        TopBar(
            modifier = Modifier
                .statusBarsPadding()
                .padding(vertical = 8.dp),
            title = {
                Text(
                    text = stringResource(R.string.cast),
                    style = Theme.textStyle.title.large,
                    color = Theme.color.textColors.title
                )
            },
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Theme.color.surfaceHigh)
                        .clickable {
                            listener.onBackClicked()
                        }
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = stringResource(R.string.arrow_back),
                        tint = Theme.color.textColors.title
                    )
                }
            }
        )
        MediaCastGrid(
            mediaCast = castState.castList
        )
    }


}