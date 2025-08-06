package com.berlin.aflami.screens.onBoarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun OnBoardingButtonNavigation(
    modifier: Modifier = Modifier,
    currentPage: Int,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        AnimatedVisibility(currentPage > 0) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Theme.color.primaryVariant)
                    .padding(horizontal = 24.dp, vertical = 18.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onPreviousClick()
                    }

            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = null,
                    tint = Theme.color.primary,
                )
            }
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Theme.color.primaryVariant)
                .padding(horizontal = 24.dp, vertical = 18.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onNextClick()
                }

        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_left),
                contentDescription = null,
                tint = Theme.color.primary,
                modifier = Modifier
                    .rotate(180f)

            )
        }

    }
}

@Composable
@Preview(showSystemUi = true)
private fun OnBoardingButtonNavigationPreview() {
    OnBoardingButtonNavigation(
        currentPage = 1,
        onNextClick = {},
        onPreviousClick = {}
    )
}