package com.berlin.aflami.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
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
import kotlinx.coroutines.CoroutineScope

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel()
) {


    val onBoardingList = listOf(
        OnBoardingModel(
            image = painterResource(R.drawable.onboarding_page1),
            title = stringResource(R.string.movies_that_feel_you),
            description = stringResource(R.string.page1_descerption)
        ),
        OnBoardingModel(
            image = painterResource(R.drawable.onboarding_page2),
            title = stringResource(R.string.build_your_watchlist_show_love),
            description = stringResource(R.string.page2_description)
        ),
        OnBoardingModel(
            image = painterResource(R.drawable.onboarding_page3),
            title = stringResource(R.string.your_movie_journal),
            description = stringResource(R.string.page3_description)
        ),
        OnBoardingModel(
            image = painterResource(R.drawable.onboarding_page4),
            title = stringResource(R.string.guess_play),
            description = stringResource(R.string.page4_description)
        )
    )
    val isFirstEntry by viewModel.isFirstEntry.collectAsState()
    val onBoardingPageState = rememberPagerState(initialPage = 0) {
        onBoardingList.size
    }

    val coroutineScope = rememberCoroutineScope()
    val navController = Theme.navController


    if (isFirstEntry==false) {
        navController.navigate(
            LoginDestination
        ) {
            popUpTo(OnBoardingDestination) { inclusive = true }
        }

    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                OnBoardingScreenEffect.NavigateToLogin -> {
                    viewModel.saveFirstEntry()
                    navController.navigate(
                        LoginDestination
                    ){
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
        coroutineScope = coroutineScope,
    )


}

@Composable
fun OnBoardingContent(
    onBoardingList: List<OnBoardingModel>,
    navigateToLogin: () -> Unit,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {
    val lastPage = remember { pagerState.pageCount - 1 }

    Box(
        modifier = Modifier.fillMaxSize()

    ) {
        AnimatedVisibility(pagerState.currentPage != lastPage) {
            TextButton(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp)
                    .zIndex(1f),
                onClick = navigateToLogin
            ) {
                Text(
                    text = "Skip",
                    style = Theme.textStyle.label.medium,
                    color = Theme.color.primary,

                    )
            }
        }

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 88.dp),
            state = pagerState,
            verticalAlignment = Alignment.Bottom
        ) { index ->
            OnBoardingPage(
//                orientation = (orientation == Configuration.ORIENTATION_PORTRAIT),
                modifier = Modifier.fillMaxSize(),
                onBoardingModel = onBoardingList[index],
                pagerState = pagerState,
                navigateToLogin = {
                    navigateToLogin()
                },
                coroutineScope = coroutineScope,
            )
        }
    }


}