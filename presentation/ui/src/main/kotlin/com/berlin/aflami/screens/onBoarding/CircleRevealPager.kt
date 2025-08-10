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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.color.ExtraColors.onBoardingLinearGradientDownToTop
import com.berlin.aflami.ui.color.ExtraColors.onBoardingLinearGradientTopToDown
import com.berlin.aflami.ui.theme.Theme
import kotlin.math.absoluteValue

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CircleRevealPager(
    pagerState: PagerState,
    data: List<OnBoardingModel>,
    modifier: Modifier = Modifier,
   ) {
    var offsetY by remember { mutableFloatStateOf(0f) }
    HorizontalPager(
        modifier = modifier
            .pointerInteropFilter {
                offsetY = it.y
                false
            }

            .background(Color.Black),
        state = pagerState,
    ) { page ->


        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset = pagerState.offsetForPage(page)
                    translationX = size.width * pageOffset

                    val endOffset = pagerState.endOffsetForPage(page)

                    shadowElevation = 20f
                    shape = CirclePath(
                        progress = 1f - endOffset.absoluteValue,
                        origin = Offset(
                            size.width,
                            offsetY,
                        )
                    )
                    clip = true

                    val absoluteOffset = pagerState.offsetForPage(page).absoluteValue
                    val scale = 1f + (absoluteOffset.absoluteValue * .4f)

                    scaleX = scale
                    scaleY = scale

                    val startOffset = pagerState.startOffsetForPage(page)
                    alpha = (2f - startOffset) / 2f

                },
            contentAlignment = Alignment.Center,
        ) {

            Image(
                painter = painterResource( data[page].image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
             )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = onBoardingLinearGradientTopToDown,
                    ).background(
                        brush = onBoardingLinearGradientDownToTop,
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 12.dp, end = 12.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Indicator(
                    modifier = Modifier.padding(bottom = 24.dp),
                    pageNumber = pagerState.currentPage,
                    pageCount = pagerState.pageCount,
                )
                Text(
                    text = stringResource( data[page].title),
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




