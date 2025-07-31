package com.berlin.aflami.screens.mediadetails.screen

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.mediadetails.components.MediaCastGrid
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.mediadetails.cast.CastDetailsEffect
import com.berlin.aflami.viewmodel.mediadetails.cast.CastDetailsListener
import com.berlin.aflami.viewmodel.mediadetails.cast.CastViewModel
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaCastUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.ui.R
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CastDetailsScreen(
    mediaId: Long,
    mediaType: MediaType,
    viewmodel: CastViewModel = koinViewModel(parameters = { parametersOf(mediaId, mediaType) }),

) {
    val navController = Theme.navController
    val castState by viewmodel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewmodel.effect.collect { effect ->
            onReceiveEffect(navController = navController, castDetailsEffect = effect)
        }
    }
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = castState.isLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(com.berlin.ui.R.string.loading)
        )
    }
    AnimatedVisibility(
        visible = castState.error != null
    ) {
        NoInternetConnectionPlaceholder()
    }

    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = !castState.isLoading
    ) {
        CastContent(
            listener = viewmodel,
            castState = castState.mediaCast
        )
    }
}

private fun onReceiveEffect(navController: NavController, castDetailsEffect: CastDetailsEffect) {
    when (castDetailsEffect) {
        is CastDetailsEffect.CastNavigationBack -> navController.popBackStack()
    }
}

@Composable
fun CastContent(
    listener: CastDetailsListener,
    castState: List<MediaCastUiState>,
) {
    Column {
        TopBar(
            modifier = Modifier.statusBarsPadding().padding(vertical = 8.dp),
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
                            listener.onCastBackClicked()
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
            mediaCast = castState
        )
    }


}