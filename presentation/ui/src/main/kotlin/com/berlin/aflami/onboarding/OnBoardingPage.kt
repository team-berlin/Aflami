package com.berlin.aflami.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.color.ExtraColors.onBoardingLinearGradientDownToTop
import com.berlin.aflami.ui.color.ExtraColors.onBoardingLinearGradientTopToDown
import com.berlin.aflami.ui.theme.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun OnBoardingPage(
    onBoardingModel: OnBoardingModel,
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    navigateToLogin: () -> Unit,
    coroutineScope: CoroutineScope
) {

    Box(
        modifier = modifier
    ) {
        Image(
            painter = onBoardingModel.image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = onBoardingLinearGradientTopToDown,
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = onBoardingLinearGradientDownToTop,
                )
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 520.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            BottomPageIndicator(
                modifier = Modifier.padding(end = 120.dp),
                pageNumber = pagerState.currentPage,
                pageCount = pagerState.pageCount,
            )
            Text(
                text = onBoardingModel.title,
                style = Theme.textStyle.headline.small,
                color = Theme.color.textColors.onPrimary,
            )
            Text(
                text = onBoardingModel.description,
                style = Theme.textStyle.body.medium,
                color = Theme.color.textColors.onPrimaryBody,
            )

            OnBoardingButtonNavigation(
                currentPage = pagerState.currentPage,
                onNextClick = {
                    if(pagerState.currentPage != pagerState.pageCount - 1){
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                    else{
                        navigateToLogin()
                    }

                },
                onPreviousClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            )

        }
    }


}
