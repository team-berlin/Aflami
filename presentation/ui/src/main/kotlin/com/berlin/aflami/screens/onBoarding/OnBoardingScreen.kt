package com.berlin.aflami.screens.onBoarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.berlin.aflami.navigation.LoginDestination
import com.berlin.aflami.navigation.OnBoardingDestination
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.onboarding.OnBoardingScreenEffect
import com.berlin.aflami.viewmodel.onboarding.OnBoardingViewModel
import com.berlin.ui.R
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel()
) {

    val onBoardingPageState = rememberPagerState(initialPage = 0) {
        onBoardingList.size
    }

    val navController = Theme.navController

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                OnBoardingScreenEffect.NavigateToLogin -> {
                    viewModel.saveFirstEntry()
                    navController.navigate(
                        LoginDestination
                    ) {
                        popUpTo(OnBoardingDestination) { inclusive = true }

                    }
                }
            }
        }
    }

    OnBoardingContent(
        onBoardingList = onBoardingList,
        navigateToLogin = { viewModel.onClickSkip() },
        pagerState = onBoardingPageState,
    )

}

@Composable
fun OnBoardingContent(
    onBoardingList: List<OnBoardingModel>,
    navigateToLogin: () -> Unit,
    pagerState: PagerState,
) {
    val isLastPage by remember {
        derivedStateOf { pagerState.currentPage == pagerState.pageCount - 1 }
    }
    val coroutineScope = rememberCoroutineScope()

    CircleRevealPager(
        pagerState = pagerState,
        data = onBoardingList,
        modifier = Modifier.fillMaxSize(),
    )
    Box(
        modifier = Modifier.fillMaxSize()

    ) {

        AnimatedVisibility(visible = !isLastPage) {
            TextButton(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 16.dp, start = 16.dp)
                    .zIndex(2f), onClick = navigateToLogin
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    style = Theme.textStyle.label.medium,
                    color = Theme.color.primary,
                )
            }
        }
        OnBoardingButtonNavigation(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter),
            currentPage = pagerState.currentPage,
            onNextClick = {
                if (pagerState.currentPage != pagerState.pageCount - 1) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                } else {
                    navigateToLogin()
                }

            },
            onPreviousClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            })

    }

}

