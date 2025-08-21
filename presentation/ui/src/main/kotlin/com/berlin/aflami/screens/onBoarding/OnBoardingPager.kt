package com.berlin.aflami.screens.onBoarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.color.ExtraColors.onBoardingLinearGradientDownToTop
import com.berlin.aflami.ui.color.ExtraColors.onBoardingLinearGradientTopToDown
import com.berlin.aflami.ui.theme.Theme

@Composable
fun OnBoardingPager(
    pagerState: PagerState,
    data: List<OnBoardingModel>,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
    ) { page ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(data[page].image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = onBoardingLinearGradientTopToDown)
                    .background(brush = onBoardingLinearGradientDownToTop)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 12.dp, end = 12.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Indicator(
                    modifier = Modifier.padding(end = 0.dp),
                    pageNumber = pagerState.currentPage,
                    pageCount = pagerState.pageCount,
                )
                Text(
                    text = stringResource(data[page].title),
                    style = Theme.textStyle.headline.small,
                    color = Theme.color.textColors.onPrimary,
                )
                Text(
                    text = stringResource(data[page].description),
                    style = Theme.textStyle.body.medium,
                    color = Theme.color.textColors.onPrimaryBody,
                )
            }
        }
    }
}