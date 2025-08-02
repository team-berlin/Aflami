package com.berlin.aflami.screens.mediadetails.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.DefaultBar
import com.berlin.aflami.navigation.CastDestination
import com.berlin.aflami.navigation.LoginDestination
import com.berlin.aflami.navigation.VideoWebViewDestination
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.navigation.MediaDetailsDestination
import com.berlin.aflami.screens.mediadetails.components.BackdropPager
import com.berlin.aflami.screens.mediadetails.components.LoginRequiredDialog
import com.berlin.aflami.screens.mediadetails.components.RateDialog
import com.berlin.aflami.screens.mediadetails.components.screensections.CastSection
import com.berlin.aflami.screens.mediadetails.components.screensections.DescriptionSection
import com.berlin.aflami.screens.mediadetails.components.screensections.MediaOverviewSection
import com.berlin.aflami.screens.mediadetails.components.screensections.TabSection
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.mediadetails.MovieDetailsTabs
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsScreenEffect
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsViewModel
import com.berlin.aflami.viewmodel.mediadetails.details.MediaInteractionListener
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaDetailsUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.RowSectionUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.UiText
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.designsystem.R
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MediaDetailsScreen(
    mediaId: Long,
    mediaType: MediaType,
    viewModel: MediaDetailsViewModel = koinViewModel(parameters = { parametersOf(mediaId, mediaType) })) {
    val navController = Theme.navController
    val uiState by viewModel.state.collectAsState()
    val tabSelected by viewModel.tabSelectedUiState.collectAsState()
    val showLoginRequiredDialog by viewModel.showLoginRequiredDialog.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { newEffect ->
            onReceiveMediaDetailsEffect(
                navController = navController,
                mediaDetailsScreenEffect = newEffect
            )
        }
    }

    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = uiState.isLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(com.berlin.ui.R.string.loading)
        )
    }
    AnimatedVisibility(
        visible = uiState.error != null
    ) {
        NoInternetConnectionPlaceholder()
    }
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = !uiState.isLoading
    ) {
        MediaDetailsContent(
            state = uiState,
            listener = viewModel,
            isDescriptionExpanded = viewModel.isDescriptionExpanded(),
            mediaChips = tabSelected.tab,
            mediaType = viewModel.mediaType,
        )
    }
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = showLoginRequiredDialog
    ) {
        LoginRequiredDialog(
            onLoginClick = {
                viewModel.onLoginButtonClicked()
            },
            onDismiss = { viewModel.showLoginDialog(false) },
            title = stringResource(com.berlin.ui.R.string.login_required),
            description = stringResource(com.berlin.ui.R.string.login_required_warning)
        )
    }


}


private fun onReceiveMediaDetailsEffect(
    navController: NavController,
    mediaDetailsScreenEffect: MediaDetailsScreenEffect,
) {
    when (mediaDetailsScreenEffect) {
        is MediaDetailsScreenEffect.NavigateToShowAllCastScreen -> {
            navController.navigate(
                CastDestination(mediaDetailsScreenEffect.mediaId,
                    mediaDetailsScreenEffect.mediaType)
            )
        }

        is MediaDetailsScreenEffect.NavigateBack -> {
            navController.popBackStack()
        }

        is MediaDetailsScreenEffect.PlayMedia -> {
            navController.navigate(
                VideoWebViewDestination(mediaDetailsScreenEffect.videoUrl)
            )
        }

        is MediaDetailsScreenEffect.NavigateToMediaDetails -> {
            navController.navigate(
                MediaDetailsDestination(
                    mediaDetailsScreenEffect.mediaId,
                    mediaDetailsScreenEffect.mediaType
                )
            ){
                launchSingleTop = true
            }
        }

        MediaDetailsScreenEffect.NavigateToLogin -> {
            navController.navigate(LoginDestination)
        }
    }
}

@Composable
fun MediaDetailsContent(
    state: MediaDetailsUiState,
    listener: MediaInteractionListener,
    isDescriptionExpanded: Boolean,
    mediaChips: MovieDetailsTabs,
    mediaType: MediaType,
) {
    val listState = rememberLazyListState()
    val appBarFadeHeightPx = with(LocalDensity.current) { 50.dp.roundToPx() }
    val appBarAlpha by remember {
        derivedStateOf {
            val offset =
                if (listState.firstVisibleItemIndex == 0) listState.firstVisibleItemScrollOffset else appBarFadeHeightPx
            (offset / appBarFadeHeightPx.toFloat()).coerceIn(0f, 1f)
        }
    }
    val animatedAppBarAlpha by animateFloatAsState(appBarAlpha)
    val appBarBgColor = Theme.color.surface.copy(alpha = animatedAppBarAlpha)

    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surface)

    ) {
        LazyColumn(state = listState) {
            item {
                BackdropPager(
                    state = state,
                    onPlayClick = { listener.onPlayClicked(state.id,state.mediaType) })
            }

            item {
                MediaOverviewSection(state = state)
            }
            item {
                DescriptionSection(
                    state.overview, isExpanded = isDescriptionExpanded,
                    onToggleExpand = { listener.onReadMoreDescriptionClicked() }
                )
            }
            item {
                CastSection(
                    cast = state.mediaCast,
                    onShowAllClicked = { listener.onShowCastClicked() }
                )
            }
            item {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    color = Theme.color.stroke,
                    thickness = 1.dp
                )
            }
            item {
                TabSection(
                    tabState = mediaChips,
                    rowState = state.rowSection,
                    onChipClick = { tab-> listener.onTabSelected(tab)},
                    isReviewExpanded = { id -> state.expandedReviewIds.contains(id) },
                    onToggleReviewExpand = { id -> listener.onReadMoreReviewClicked(id) },
                    onMediaClick = { mediaId, type -> listener.onMediaClicked(mediaId, type)
                        Log.d("MoreLikeThisInScreen", "ID= $mediaId , Type= $type")},

                    mediaType = mediaType,
                )
            }
        }
        DefaultBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(appBarBgColor)
                .statusBarsPadding(),

            firstOption = painterResource(R.drawable.ic_rounded_star),
            lastOption = painterResource(R.drawable.ic_rounded_add_heart),
            onFirstOptionClicked = { listener.onRateIconClicked(state.id) },
            onLastOptionClicked = {
                listener.onAddMediaToFavouriteListClicked(0, state.id)
            },
            onNavigateBackClicked = { listener.onBackClicked() },
            optionContainerColor = Theme.color.surfaceHigh,
            containerColor = Color.Unspecified,
        )

        if (state.showRatingDialog && state.selectedRatingMediaId != null) {
                RateDialog(
                    onDismiss = {listener.onCancelRatingClicked()},
                    onRate = {rating -> listener.onSubmitRateClicked(rating)}
                )
        }

//        if (state.showAddToListDialog && state.selectedAddToListMediaId != null) {
//            AddToListDialog(
//                mediaId = uiState.selectedAddToListMediaId,
//                favouriteListId = uiState.selectedFavouriteListId,
//                onConfirm = { listId ->
//                    viewModel.onSelectFavouriteList(listId)
//                },
//                onDismiss = {
//                    viewModel.onCancelAddingToFavouriteClicked()
//                }
//            )
//        }
    }

}

@Composable
fun RowSectionUiState.getDisplayMessage(): String {
    return when (this) {
        is RowSectionUiState.Error -> this.message.orEmpty()
        is RowSectionUiState.NoDataFound -> this.message.asString()
        else -> "Unknown error!"
    }
}

@Composable
fun UiText.asString(): String {
    return when (this) {
        is UiText.Dynamic -> value
        is UiText.Resource -> stringResource(id = resId)
    }
}